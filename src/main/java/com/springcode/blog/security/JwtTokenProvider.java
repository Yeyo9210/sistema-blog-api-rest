package com.springcode.blog.security;

import com.springcode.blog.exceptions.BlogAppException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.SignatureException;
import java.util.Date;

@Component
public class JwtTokenProvider {
    /*
    Vamos a generar el token , se generan los claims y validar el token
    Value Obtner valor de una propiedad
     */
    @Value("{app.jwt-secret}")
    private String jwtSecret;

    @Value("{app.jwt-expiration.milliseconds}")
    private int jwtExpirationInMs;

    public String generarToken(Authentication authentication){
        /*
        Obtenemos el usuario , la fecha actual y la fecha de expiration
        Y establecemos la informacion en el token
         */
        String username = authentication.getName();
        Date fechaActual = new Date();
        Date fehcaExpiration = new Date(fechaActual.getTime() + jwtExpirationInMs);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(fehcaExpiration)
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact();
    }

    /*
    Obtenemos el username del token
     */
    public String obtenerUsernameDelJWT(String token){
        Claims claims = Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    /*
    Valida que el token este correcto con su llave secreta
     */
    public boolean validarToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret.getBytes())
                    .build()
                    .parseClaimsJws(token);
            return true;
        }catch (MalformedJwtException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"Token JWT no valida");
        }catch (ExpiredJwtException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"Token JWT caducado");
        }catch (UnsupportedJwtException ex){
            throw new BlogAppException(HttpStatus.BAD_REQUEST,"Token JWT no compatible");
        }catch (IllegalArgumentException ex) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "La cadena claims JWT esta vacia");
        }
    }


}
