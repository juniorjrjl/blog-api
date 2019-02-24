package br.com.blog.blogapi.repository.query.impl

import br.com.blog.blogapi.dto.PostListagemDTO
import br.com.blog.blogapi.dto.TagAtualizarDTO
import br.com.blog.blogapi.dto.TagDetalhesDTO
import br.com.blog.blogapi.dto.TagListagem
import br.com.blog.blogapi.model.Post
import br.com.blog.blogapi.model.Post_
import br.com.blog.blogapi.model.Tag
import br.com.blog.blogapi.model.Tag_
import br.com.blog.blogapi.repository.query.TagRepositoryQuery
import br.com.blog.blogapi.repository.query.util.Paginacao
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.NonUniqueResultException
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.CriteriaUpdate
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

open class TagRepositoryQueryImpl: TagRepositoryQuery {

    @Autowired
    lateinit var entityManager: EntityManager

    @Transactional
    override fun atualizar(dto: TagAtualizarDTO): TagDetalhesDTO {
        val builder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteria: CriteriaUpdate<Tag> = builder.createCriteriaUpdate(Tag::class.java)
        val root: Root<Tag> = criteria.from(Tag::class.java)
        criteria.set(root[Tag_.descricao], dto.descricao)
        criteria.where(builder.equal(root[Tag_.codigo], dto.codigo))
        try {
            entityManager.createQuery(criteria).executeUpdate()
            return buscarPorCodigo(dto.codigo)
        }catch (e: Exception){
            e.printStackTrace()
            throw e
        }
    }

    override fun buscarPorCodigo(codigoTag: Long): TagDetalhesDTO {
        val builder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteria: CriteriaQuery<TagDetalhesDTO> = builder.createQuery(TagDetalhesDTO::class.java)
        val root: Root<Tag> = criteria.from(Tag::class.java)
        criteria.select(builder.construct(TagDetalhesDTO::class.java,
                root[Tag_.codigo],
                root[Tag_.descricao]))
        criteria.where(builder.equal(root[Tag_.codigo], codigoTag))
        val query : TypedQuery<TagDetalhesDTO> = entityManager.createQuery(criteria)
        var dto : TagDetalhesDTO?
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
        return dto!!
    }

    override fun listagem(paginavel: Pageable): Page<TagListagem> {
        val builder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteria: CriteriaQuery<TagListagem> = builder.createQuery(TagListagem::class.java)
        val root: Root<Tag> = criteria.from(Tag::class.java)
        criteria.select(builder.construct(TagListagem::class.java,
                root[Tag_.codigo],
                root[Tag_.descricao]))
        val query : TypedQuery<TagListagem> = entityManager.createQuery(criteria)
        Paginacao.montarPaginacao(query, paginavel)
        var dto : List<TagListagem>
        try {
            dto = query.resultList
        }catch (e: Exception){
            e.printStackTrace()
            throw e
        }
        return PageImpl<TagListagem>(dto, paginavel, verificarTotalRegistrosQuery((emptyList<Predicate>()).toTypedArray()))
    }

    override fun buscarTagsPost(codigoPost: Long): List<TagDetalhesDTO> {
        val builder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteria: CriteriaQuery<TagDetalhesDTO> = builder.createQuery(TagDetalhesDTO::class.java)
        val root: Root<Tag> = criteria.from(Tag::class.java)
        criteria.select(builder.construct(TagDetalhesDTO::class.java,
                root[Tag_.codigo],
                root[Tag_.descricao]))
        criteria.where(builder.equal(root.join(Tag_.posts)[Post_.codigo], codigoPost))
        val query : TypedQuery<TagDetalhesDTO> = entityManager.createQuery(criteria)
        var dto : List<TagDetalhesDTO>
        try {
            dto = query.resultList
        }catch (e: Exception){
            e.printStackTrace()
            throw e
        }
        return dto
    }

    private fun verificarTotalRegistrosQuery(predicates: Array<Predicate>): Long{
        val builder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteria: CriteriaQuery<Long> = builder.createQuery(Long::class.java)
        val root: Root<Tag> = criteria.from(Tag::class.java)
        criteria.select(builder.count(root[Tag_.codigo]))
        if (predicates.isNotEmpty()) {
            criteria.where(*predicates)
        }
        return entityManager.createQuery(criteria).singleResult
    }

}