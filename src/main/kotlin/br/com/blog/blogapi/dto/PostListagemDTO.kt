package br.com.blog.blogapi.dto

data class PostListagemDTO(
    var codigo: Long,
    var titulo: String,
    var pessoa: String
){
    var tags: List<TagDetalhesDTO> = emptyList()
}