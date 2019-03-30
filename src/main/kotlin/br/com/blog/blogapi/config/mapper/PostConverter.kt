package br.com.blog.blogapi.config.mapper

import br.com.blog.blogapi.dto.PostCadastrarDTO
import br.com.blog.blogapi.model.Post
import br.com.blog.blogapi.model.Tag
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings
import org.mapstruct.Named
import kotlin.collections.ArrayList

@Mapper(componentModel = "spring")
abstract class PostConverter {

    @Mappings(
            Mapping(target = "codigo", ignore = true),
            Mapping(source = "titulo", target = "titulo"),
            Mapping(source = "descricao", target = "descricao"),
            Mapping(source = "codigoPessoa", target = "pessoa.codigo"),
            Mapping(target = "dataEdicao", ignore = true),
            Mapping(target = "dataCriacao", ignore = true),
            Mapping(target = "tags", source = "tags", qualifiedByName = ["longListToTagList"] )
    )
    abstract fun DTOToModel(dto: PostCadastrarDTO): Post

    @Named("longListToTagList")
    fun longListToTagList(dto: List<Long>):List<Tag>{
        val tags: MutableList<Tag> = ArrayList()
        dto.forEach {
            val t = Tag()
            t.codigo = it
            tags.add(t)
        }
        return tags.toList()
    }

}