package br.com.blog.blogapi.repository.query

import br.com.blog.blogapi.dto.TagAtualizarDTO
import br.com.blog.blogapi.dto.TagDetalhesDTO
import br.com.blog.blogapi.dto.TagListagem
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface TagRepositoryQuery {
    fun atualizar(dto: TagAtualizarDTO): TagDetalhesDTO
    fun buscarPorCodigo(codigoTag: Long): TagDetalhesDTO
    fun listagem(paginavel: Pageable): Page<TagListagem>
    fun buscarTagsPost(codigoPost: Long): List<TagDetalhesDTO>
}