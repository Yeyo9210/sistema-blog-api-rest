package com.springcode.blog.repository;

import com.springcode.blog.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComentarioRepository extends JpaRepository<Comentario,Long> {

    List<Comentario> findByPublicacionId(Long publicacionId);
}
