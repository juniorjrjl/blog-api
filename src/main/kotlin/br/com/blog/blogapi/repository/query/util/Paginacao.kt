package br.com.blog.blogapi.repository.query.util

import br.com.blog.blogapi.model.Post
import br.com.blog.blogapi.model.Post_
import org.springframework.data.domain.Pageable
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

class Paginacao {

    companion object {

        fun montarPaginacao(query: TypedQuery<*>, paginavel: Pageable){
            val paginaAtual = paginavel.pageNumber
            val registosPorPagina = paginavel.pageSize
            val primeiroregistro = paginaAtual * registosPorPagina
            query.firstResult = primeiroregistro
            query.maxResults = registosPorPagina
        }

    }

}