package br.com.blog.blogapi.dto

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

class TagCadastrarDTO(
        @field:NotNull
        @field:NotBlank
        @field:Size(min = 2, max = 140)
        var descricao: String
)