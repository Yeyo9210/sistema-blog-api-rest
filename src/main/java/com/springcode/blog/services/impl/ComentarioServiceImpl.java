package com.springcode.blog.services.impl;

import com.springcode.blog.dao.ComentarioDTO;
import com.springcode.blog.entity.Comentario;
import com.springcode.blog.entity.Publicacion;
import com.springcode.blog.exceptions.BlogAppException;
import com.springcode.blog.exceptions.ResourceNotFoundException;
import com.springcode.blog.mapper.ComentarioMapper;
import com.springcode.blog.repository.ComentarioRepository;
import com.springcode.blog.repository.PublicacionRepository;
import com.springcode.blog.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ComentarioServiceImpl implements ComentarioService {

    @Autowired
    private ComentarioRepository comentarioRepository;

    @Autowired
    private PublicacionRepository publicacionRepository;

    @Autowired
    private ComentarioMapper comentarioMapper;

    @Override
    public ComentarioDTO crearComentario(Long publicacionId, ComentarioDTO comentarioDTO) {
        Comentario comentario = comentarioMapper.toEntity(comentarioDTO);
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion","id",publicacionId));

        comentario.setPublicacion(publicacion);
        Comentario nuevoComentario = comentarioRepository.save(comentario);
        return comentarioMapper.toDto(nuevoComentario);
    }

    @Override
    public List<ComentarioDTO> obtenerComentariosPorPublicacionId(Long publicacionId) {
       return comentarioRepository.findByPublicacionId(publicacionId)
                .stream()
                .map(ComentarioMapper::mapPublicacionDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ComentarioDTO obtenerComentarioPorId(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion","id",publicacionId));
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario","id",comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a la publicacion");
        }
        return comentarioMapper.toDto(comentario);
    }

    @Override
    public ComentarioDTO actualizarComentario(Long publicacionId, Long comentarioId, ComentarioDTO comentarioDTO) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion","id",publicacionId));
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario","id",comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario no pertenece a esta publicación");
        }
        comentario.setNombre(comentarioDTO.getNombre());
        comentario.setEmail(comentarioDTO.getEmail());
        comentario.setCuerpo(comentarioDTO.getCuerpo());

        return comentarioMapper.toDto(comentarioRepository.save(comentario));
    }

    @Override
    public void eliminarComentario(Long publicacionId, Long comentarioId) {
        Publicacion publicacion = publicacionRepository.findById(publicacionId)
                .orElseThrow(() -> new ResourceNotFoundException("Publicacion","id",publicacionId));
        Comentario comentario = comentarioRepository.findById(comentarioId)
                .orElseThrow(() -> new ResourceNotFoundException("Comentario","id",comentarioId));

        if(!comentario.getPublicacion().getId().equals(publicacion.getId())){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"El comentario con id :"+comentarioId+" no se encuentra");
        }
        comentarioRepository.delete(comentario);
    }
}
