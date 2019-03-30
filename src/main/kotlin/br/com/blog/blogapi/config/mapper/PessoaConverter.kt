package br.com.blog.blogapi.config.mapper

import br.com.blog.blogapi.dto.PessoaCadastrarDTO
import br.com.blog.blogapi.model.Pessoa
import org.mapstruct.Mapper
import org.mapstruct.Mapping
import org.mapstruct.Mappings

@Mapper(componentModel = "spring")
interface PessoaConverter {

    @Mappings(
            Mapping(target = "codigo", ignore = true),
            Mapping(source = "nome", target = "nome"),
            Mapping(source = "email", target = "email"),
            Mapping(source = "senha", target = "senha"),
            Mapping(target = "posts", ignore = true)
    )
    fun DTOToModel(dto: PessoaCadastrarDTO):Pessoa

}