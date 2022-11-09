package com.springcode.blog.services;

import com.springcode.blog.dao.ComentarioDTO;

import java.util.List;

public interface ComentarioService {

    ComentarioDTO crearComentario(Long publicacionId,ComentarioDTO comentarioDTO);
    List<ComentarioDTO> obtenerComentariosPorPublicacionId(Long publicacionId);
    ComentarioDTO obtenerComentarioPorId(Long publicacionId,Long comentarioId);
    ComentarioDTO actualizarComentario(Long publicacionId,Long comentarioId,ComentarioDTO comentarioDTO);
    void eliminarComentario(Long publicacionId,Long comentarioId);

}
