package com.springcode.blog.services.impl;

import com.springcode.blog.dao.PublicacionDTO;
import com.springcode.blog.dao.PublicacionRespuesta;
import com.springcode.blog.entity.Publicacion;
import com.springcode.blog.exceptions.ResourceNotFoundException;
import com.springcode.blog.mapper.PublicacionMapper;
import com.springcode.blog.repository.PublicacionRepository;
import com.springcode.blog.services.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PublicacionServiceImpl implements PublicacionService {

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private PublicacionMapper publicacionMapper;

    @Override
    public PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO) {
        Publicacion publicacion = publicacionMapper.toEntity(publicacionDTO);
        Optional<PublicacionDTO> publicacionGuardada = Optional.ofNullable
                (publicacionRepository.findByTitulo(publicacionDTO.getTitulo()));
        if (publicacionGuardada.isPresent()) {
            throw new ResourceNotFoundException("Publicacion","Titulo",publicacionDTO.getId());
        }
        return publicacionMapper.toDto(publicacionRepository.save(publicacion));
       }

    @Override
    public PublicacionRespuesta obtenerListaPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir) {
        /*
        Indicamos si el sort es igual a la direccion Ascendente y se ordena por el valor que le pasamos
        Y si no pasamos lo ordenamos de forma descendente
         */
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(ordenarPor)
                .ascending():Sort.by(ordenarPor).descending();
        Pageable pageable = PageRequest.of(numeroDePagina,medidaDePagina,sort);
        Page<Publicacion> publicaciones = publicacionRepository.findAll(pageable);
        List<Publicacion> listarPublicaciones = publicaciones.getContent();
        List<PublicacionDTO> contenido = listarPublicaciones
                .stream()
                .map(PublicacionMapper::mapPublicacionDTO)
                .collect(Collectors.toList());
        PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
        publicacionRespuesta.setContenido(contenido);
        publicacionRespuesta.setNumeroDePagina(publicaciones.getNumber());
        publicacionRespuesta.setMedidaDePagina(publicaciones.getSize());
        publicacionRespuesta.setTotalElementos(publicaciones.getTotalElements());
        publicacionRespuesta.setTotalDePaginas(publicaciones.getTotalPages());
        publicacionRespuesta.setUltima(publicaciones.isLast());
        return publicacionRespuesta;

    }

    @Override
    public Optional<PublicacionDTO> obtenerPublicacionPorId(Long id) {
        return Optional.ofNullable(publicacionMapper.toDto(publicacionRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Publicacion","id",id)
        )));
    }

    @Override
    public PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, Long id) {
       return publicacionRepository.findById(id)
                .map(publicacionGuardada -> {
                    publicacionGuardada.setId(publicacionDTO.getId());
                    publicacionGuardada.setTitulo(publicacionDTO.getTitulo());
                    publicacionGuardada.setDescripcion(publicacionDTO.getDescripcion());
                    publicacionGuardada.setContenido(publicacionDTO.getContenido());

                    PublicacionDTO publicacionActualizada = publicacionMapper.toDto(publicacionRepository.save(publicacionGuardada));

                    return publicacionActualizada;
                })
                .orElseThrow(() -> new ResourceNotFoundException("Publicación","id",id));
    }

    @Override
    public void eliminarPublicacion(Long id) {
        publicacionMapper.toDto(publicacionRepository.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("Publicacion","id",id)
        ));
        publicacionRepository.deleteById(id);
    }
}
