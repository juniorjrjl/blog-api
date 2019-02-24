package br.com.blog.blogapi.dto

data class PostDetalhesDTO(
        var codigo: Long,
        var titulo: String,
        var descricao: String,
        var pessoa: String
){
    var tags: List<TagDetalhesDTO> = emptyList()
}