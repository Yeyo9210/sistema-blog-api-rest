package com.springcode.blog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


//Indicamos que el titulo sera unico no tendra que tener alguno repetido
@Entity
@Table(name = "publicaciones",uniqueConstraints = {@UniqueConstraint(columnNames = {"titulo"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Publicacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo",nullable = false)
    private String titulo;

    @Column(name = "descripcion",nullable = false)
    private String descripcion;

    @Column(name = "contenido",nullable = false)
    private String contenido;

    /*
    En una publicacion tendra muchos comentarios
    orphanRemoval cada que eliminenmos un valor todos los asociados a el se eliminan
    JsonBackReference No sirve para evitar caso de recursividad e ignora cuando se serialice
     */
    @JsonBackReference
    @OneToMany(mappedBy = "publicacion",cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Comentario> comentarios = new HashSet<>();




}
