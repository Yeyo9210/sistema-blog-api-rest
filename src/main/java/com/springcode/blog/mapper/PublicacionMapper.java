package com.springcode.blog.mapper;

import com.springcode.blog.dao.PublicacionDTO;
import com.springcode.blog.entity.Publicacion;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PublicacionMapper {

    PublicacionMapper INSTANCE = Mappers.getMapper(PublicacionMapper.class);

    Publicacion toEntity(PublicacionDTO publicacionDTO);

    PublicacionDTO toDto(Publicacion publicacion);

    public static PublicacionDTO mapPublicacionDTO(Publicacion publicacion){
        PublicacionDTO publicacionDTO = new PublicacionDTO();
        publicacionDTO.setId(publicacion.getId());
        publicacionDTO.setTitulo(publicacion.getTitulo());
        publicacionDTO.setDescripcion(publicacion.getDescripcion());
        publicacionDTO.setContenido(publicacion.getContenido());
        publicacionDTO.setComentarios(publicacion.getComentarios());
        return publicacionDTO;
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(PublicacionDTO publicacionDTO, @MappingTarget Publicacion publicacion);
}
