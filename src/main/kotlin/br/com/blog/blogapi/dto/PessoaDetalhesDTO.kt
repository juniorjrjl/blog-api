package br.com.blog.blogapi.dto

data class PessoaDetalhesDTO(
        var codigo: Long,
        var nome : String,
        var email : String,
        var senha : String
)