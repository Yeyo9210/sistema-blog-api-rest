package com.springcode.blog.dao;

import com.springcode.blog.entity.Comentario;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicacionDTO {

    private Long id;

    @NotEmpty
    @Size(min = 2,message = "El titulo de la publicación debe de tener al menos 2 caracteres")
    private String titulo;

    @NotEmpty
    @Size(min = 10,message = "La descripción de la publicación debe de tener al menos 10 caracteres")
    private String descripcion;

    @NotEmpty
    private String contenido;
    private Set<Comentario> comentarios;
}
