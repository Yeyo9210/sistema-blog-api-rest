package com.springcode.blog.dao;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistroDTO {

    private String nombre;
    private String username;
    private String email;
    private String password;
}
