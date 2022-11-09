package com.springcode.blog.controller;

import com.springcode.blog.dao.PublicacionDTO;
import com.springcode.blog.dao.PublicacionRespuesta;
import com.springcode.blog.services.PublicacionService;
import com.springcode.blog.utils.AppConstantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/api/publicacion")
public class PublicacionController {

    @Autowired
    private PublicacionService publicacionService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/")
    public ResponseEntity<?> guardarPublicacion(@Valid @RequestBody PublicacionDTO publicacionDTO){
        return ResponseEntity.ok(publicacionService.crearPublicacion(publicacionDTO));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarPublicacion(@PathVariable("id") Long id,@Valid @RequestBody PublicacionDTO publicacionDTO){
        return ResponseEntity.ok(publicacionService.actualizarPublicacion(publicacionDTO,id));
    }

    @GetMapping("/")
    public PublicacionRespuesta listarPublicaciones(@RequestParam(value = "pageNum",defaultValue = AppConstantes.NUMERO_DE_PAGINA_POR_DEFECTO,
            required = false) int numeroDePagina, @RequestParam(value = "pageSize",defaultValue = AppConstantes.MEDIDA_DE_PAGINA_POR_DEFECTO,
            required = false) int medidaDePagina, @RequestParam(value = "sortBy",defaultValue = AppConstantes.ORDENAR_POR_DEFECTO,
            required = false) String ordenarPor,@RequestParam(value = "sortDir",defaultValue = AppConstantes.ORDENAR_DIRECCION_POR_DEFECTO,
            required = false) String sortDir){
        return publicacionService.obtenerListaPublicaciones(numeroDePagina, medidaDePagina,ordenarPor,sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPublicacionPorId(@PathVariable("id") Long id){
        return ResponseEntity.ok(publicacionService.obtenerPublicacionPorId(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarPublicacion(@PathVariable("id") Long id){
        publicacionService.eliminarPublicacion(id);
        return new ResponseEntity<>("Publicacion eliminada con exito", HttpStatus.OK);
    }
}
