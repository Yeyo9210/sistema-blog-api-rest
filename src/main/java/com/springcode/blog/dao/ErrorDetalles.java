package com.springcode.blog.dao;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ErrorDetalles {

    private Date marcaDeTiempo;
    private String mensaje;
    private String detalles;
}
