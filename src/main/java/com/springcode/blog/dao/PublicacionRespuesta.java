package com.springcode.blog.dao;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicacionRespuesta {

    private List<PublicacionDTO> contenido;
    private int numeroDePagina;
    private int medidaDePagina;
    private Long totalElementos;
    private int totalDePaginas;
    private boolean ultima;
}
