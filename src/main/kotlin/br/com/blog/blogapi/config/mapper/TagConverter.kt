package br.com.blog.blogapi.config.mapper

import br.com.blog.blogapi.dto.TagCadastrarDTO
import br.com.blog.blogapi.model.Tag
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface TagConverter {

    @Mappings(
            Mapping(source = "descricao", target = "descricao"),
            Mapping(target = "codigo", ignore = true),
            Mapping(target = "posts", ignore = true),
            Mapping(target = "copy", ignore = true)
    )
    fun DTOToModel(dto: TagCadastrarDTO): Tag

}