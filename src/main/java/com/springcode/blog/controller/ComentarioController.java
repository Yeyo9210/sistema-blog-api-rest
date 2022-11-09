package com.springcode.blog.controller;

import com.springcode.blog.dao.ComentarioDTO;
import com.springcode.blog.services.ComentarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping("/publicaciones/{publicacionId}/comentarios")
    public ResponseEntity<ComentarioDTO> guardarComentario(@PathVariable("publicacionId") Long publicacionId,@Valid @RequestBody ComentarioDTO comentarioDTO){
        return new ResponseEntity<>(comentarioService.crearComentario(publicacionId,comentarioDTO), HttpStatus.CREATED);
    }

    @PutMapping("/publicaciones/{publicacionId}/comentarios/{id}")
    public ResponseEntity<ComentarioDTO> actualizarDTO(@PathVariable("publicacionId") Long publicacionId,@PathVariable("id") Long comentarioId,@Valid @RequestBody ComentarioDTO comentarioDTO){
        return new ResponseEntity<>(comentarioService.actualizarComentario(publicacionId,comentarioId,comentarioDTO),HttpStatus.OK);
    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios")
    public List<ComentarioDTO> listarComentariosPorPublicacionId(@PathVariable("publicacionId") Long publicacionId){
        return comentarioService.obtenerComentariosPorPublicacionId(publicacionId);
    }

    @GetMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<ComentarioDTO> buscarComentarioDePublicacionPorId(@PathVariable("publicacionId") Long publicacionId,@PathVariable("comentarioId") Long comentarioId){
        return new ResponseEntity<>(comentarioService.obtenerComentarioPorId(publicacionId,comentarioId), HttpStatus.OK);
    }

    @DeleteMapping("/publicaciones/{publicacionId}/comentarios/{comentarioId}")
    public ResponseEntity<String> borrarComentario(@PathVariable("publicacionId") Long publicacionId,@PathVariable("comentarioId") Long comentarioId){
        comentarioService.eliminarComentario(publicacionId,comentarioId);
        return new ResponseEntity<>("Comentario eliminado con exito",HttpStatus.OK);
    }
}
