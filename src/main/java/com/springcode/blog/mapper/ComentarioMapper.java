package com.springcode.blog.mapper;

import com.springcode.blog.dao.ComentarioDTO;
import com.springcode.blog.entity.Comentario;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {
    ComentarioMapper INSTANCE = Mappers.getMapper(ComentarioMapper.class);

    Comentario toEntity(ComentarioDTO comentarioDTO);

    ComentarioDTO toDto(Comentario comentario);

    public static ComentarioDTO mapPublicacionDTO(Comentario comentario){
        ComentarioDTO comentarioDTO = new ComentarioDTO();
        comentarioDTO.setId(comentario.getId());
        comentarioDTO.setNombre(comentario.getCuerpo());
        comentarioDTO.setEmail(comentario.getEmail());
        comentarioDTO.setCuerpo(comentario.getCuerpo());
        return comentarioDTO;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(ComentarioDTO publicacionDTO, @MappingTarget Comentario comentario);
}
