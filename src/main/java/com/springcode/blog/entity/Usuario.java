package com.springcode.blog.entity;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "usuarios",uniqueConstraints = {@UniqueConstraint(columnNames = {"username"}),@UniqueConstraint(columnNames = {"email"})})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String nombre;
    private String password;
    private String username;

    /*
    Unimos una tabla llamada usuario_roles e insertamos una referencia a la tabla
    usuarios y despues referenciamos la tabla hija de roles
     */
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_roles",joinColumns = @JoinColumn(name = "usuario_id",referencedColumnName = "id"),inverseJoinColumns = @JoinColumn(name = "rol_id",referencedColumnName = "id"))
    private Set<Rol> roles = new HashSet<>();
}
