package com.springcode.blog.security;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JWTAuthResponseDTO {

    private String tokenDeAcceso;
    private String tipoDeToken = "Bearer";

    public JWTAuthResponseDTO(String tokenDeAcceso) {
        super();
        this.tokenDeAcceso = tokenDeAcceso;
    }
}
