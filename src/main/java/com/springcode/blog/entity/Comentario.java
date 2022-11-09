package com.springcode.blog.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "comentarios")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String cuerpo;

    /*
    Muchos comentarios podran ir en una publicacin
    Cuando yo elimine un comentario no se elimina una publicacion
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "publicacion_id",nullable = false)
    private Publicacion publicacion;
}
