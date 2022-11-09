package com.springcode.blog.services;

import com.springcode.blog.dao.PublicacionDTO;
import com.springcode.blog.dao.PublicacionRespuesta;

import java.util.Optional;

public interface PublicacionService {

    PublicacionDTO crearPublicacion(PublicacionDTO publicacionDTO);
    PublicacionRespuesta obtenerListaPublicaciones(int numeroDePagina, int medidaDePagina, String ordenarPor, String sortDir);

    Optional<PublicacionDTO> obtenerPublicacionPorId(Long id);

    PublicacionDTO actualizarPublicacion(PublicacionDTO publicacionDTO, Long id);

    void eliminarPublicacion(Long id);
}
