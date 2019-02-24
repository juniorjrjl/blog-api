package br.com.blog.blogapi.repository.query.impl

import br.com.blog.blogapi.dto.PostDetalhesDTO
import br.com.blog.blogapi.dto.PostListagemDTO
import br.com.blog.blogapi.model.Pessoa_
import br.com.blog.blogapi.model.Post
import br.com.blog.blogapi.model.Post_
import br.com.blog.blogapi.model.Tag_
import br.com.blog.blogapi.repository.query.PostRepositoryQuery
import br.com.blog.blogapi.repository.query.filtro.PostListagemFiltro
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.NonUniqueResultException
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

open class PostRepositoryQueryImpl: PostRepositoryQuery {

    @Autowired
    lateinit var entityManager: EntityManager

    override fun buscarPorCodigo(codigoPost: Long): PostDetalhesDTO? {
        val builder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteria: CriteriaQuery<PostDetalhesDTO> = builder.createQuery(PostDetalhesDTO::class.java)
        val root: Root<Post> = criteria.from(Post::class.java)
        criteria.select(builder.construct(PostDetalhesDTO::class.java,
                root[Post_.codigo],
                root[Post_.titulo],
                root[Post_.descricao],
                root[Post_.pessoa][Pessoa_.nome]))
        criteria.where(builder.equal(root[Post_.codigo], codigoPost))
        val query : TypedQuery<PostDetalhesDTO> = entityManager.createQuery(criteria)
        var dto : PostDetalhesDTO?
        try {
            dto = query.singleResult
        }catch (e: Exception){
            when(e){
                is NonUniqueResultException, is NoResultException -> {
                    dto = null
                }else -> {
                e.printStackTrace()
                throw e
            }
            }
        }
        return dto
    }

    override fun listagem(filtro: PostListagemFiltro, paginavel: Pageable): Page<PostListagemDTO> {
        val builder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteria: CriteriaQuery<PostListagemDTO> = builder.createQuery(PostListagemDTO::class.java)
        val root: Root<Post> = criteria.from(Post::class.java)
        criteria.select(builder.construct(PostListagemDTO::class.java,
                root[Post_.codigo],
                root[Post_.titulo],
                root[Post_.pessoa][Pessoa_.nome]))
        var predicates :MutableList<Predicate> = mutableListOf()
        if (!filtro.titulo.isNullOrBlank()){
            predicates.add(builder.like(root[Post_.titulo], filtro.titulo))
        }
        if (!filtro.tags.isEmpty()) {
            predicates.add(root.join(Post_.tags)[Tag_.codigo].`in`(filtro.tags))
        }
        criteria.where(*(predicates as List<Predicate>).toTypedArray())
        criteria.orderBy(if (filtro.dataCriacaoDecrescente) builder.desc(root[Post_.dataCriacao]) else builder.asc(root[Post_.dataCriacao]))
        val query : TypedQuery<PostListagemDTO> = entityManager.createQuery(criteria)
        montarPaginacao(query, paginavel)
        var dto : List<PostListagemDTO>
        try{
            dto = query.resultList
        }catch (e: Exception){
            e.printStackTrace()
            throw e
        }
        return PageImpl<PostListagemDTO>(dto, paginavel, verificarTotalRegistrosQuery((predicates as List<Predicate>).toTypedArray()))
    }

    private fun verificarTotalRegistrosQuery(predicates: Array<Predicate>): Long{
        val builder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteria: CriteriaQuery<Long> = builder.createQuery(Long::class.java)
        val root: Root<Post> = criteria.from(Post::class.java)
        criteria.select(builder.count(root[Post_.codigo]))
        criteria.where(*predicates)
        return entityManager.createQuery(criteria).singleResult
    }

    private fun montarPaginacao(query: TypedQuery<*>, paginavel: Pageable){
        val paginaAtual = paginavel.pageNumber
        val registosPorPagina = paginavel.pageSize
        val primeiroregistro = paginaAtual * registosPorPagina
        query.firstResult = primeiroregistro
        query.maxResults = registosPorPagina
    }

}