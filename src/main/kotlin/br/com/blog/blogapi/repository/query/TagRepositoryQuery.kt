package br.com.blog.blogapi.repository.query

import br.com.blog.blogapi.dto.TagAtualizarDTO
import br.com.blog.blogapi.dto.TagDetalhesDTO
import br.com.blog.blogapi.dto.TagListagem

interface TagRepositoryQuery {
    fun Atualizar(dto: TagAtualizarDTO): TagDetalhesDTO
    fun buscarPorCodigo(codigoTag: Long): TagDetalhesDTO
    fun listagem(filtro: TagListagem): List<TagListagem>
}