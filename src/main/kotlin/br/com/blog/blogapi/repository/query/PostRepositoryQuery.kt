package br.com.blog.blogapi.repository.query

import br.com.blog.blogapi.dto.PostDetalhesDTO
import br.com.blog.blogapi.dto.PostListagemDTO
import br.com.blog.blogapi.repository.query.filtro.PostListagemFiltro
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostRepositoryQuery {

    fun buscarPorCodigo(codigoPost: Long): PostDetalhesDTO?
    fun listagem(filtro: PostListagemFiltro, paginavel: Pageable): Page<PostListagemDTO>

}