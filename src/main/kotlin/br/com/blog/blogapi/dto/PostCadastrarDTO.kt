package br.com.blog.blogapi.dto

class PostCadastrarDTO (
        var titulo: String,
        var descricao: String,
        var codigoPessoa: Long,
        var tags:List<Long>
)