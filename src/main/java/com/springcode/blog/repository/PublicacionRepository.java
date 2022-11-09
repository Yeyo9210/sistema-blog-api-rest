package com.springcode.blog.repository;

import com.springcode.blog.dao.PublicacionDTO;
import com.springcode.blog.entity.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicacionRepository extends JpaRepository<Publicacion,Long> {

    PublicacionDTO findByTitulo(String titulo);
}
