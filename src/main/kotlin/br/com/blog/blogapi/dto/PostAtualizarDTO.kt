package br.com.blog.blogapi.dto

data class PostAtualizarDTO(
        var codigo: Long,
        var titulo: String,
        var descricao: String,
        var tags:List<Long>
)