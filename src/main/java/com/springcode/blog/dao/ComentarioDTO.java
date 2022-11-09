package com.springcode.blog.dao;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComentarioDTO {

    private Long id;

    @NotEmpty(message = "El nombre no debe ser vacio o nulo")
    private String nombre;

    @NotEmpty(message = "El email nn debe ser vacio o nulo")
    @Email
    private String email;

    @NotEmpty
    @Size(min = 10,message = "El cuerpo del comentario debe tener al menos 10 caracteres")
    private String cuerpo;
}
