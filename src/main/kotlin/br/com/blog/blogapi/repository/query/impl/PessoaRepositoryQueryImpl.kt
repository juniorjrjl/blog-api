package br.com.blog.blogapi.repository.query.impl

import br.com.blog.blogapi.dto.PessoaAtualizarDTO
import br.com.blog.blogapi.dto.PessoaDetalhesDTO
import br.com.blog.blogapi.model.Pessoa
import br.com.blog.blogapi.model.Pessoa_
import br.com.blog.blogapi.repository.query.PessoaRepositoryQuery
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.NoResultException
import javax.persistence.NonUniqueResultException
import javax.persistence.TypedQuery
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.CriteriaUpdate
import javax.persistence.criteria.Root

open class PessoaRepositoryQueryImpl: PessoaRepositoryQuery {

    @Autowired
    lateinit var entityManager: EntityManager

    @Transactional
    override fun atualizar(dto: PessoaAtualizarDTO): PessoaDetalhesDTO {
        val builder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteria: CriteriaUpdate<Pessoa> = builder.createCriteriaUpdate(Pessoa::class.java)
        val root: Root<Pessoa> = criteria.from(Pessoa::class.java)
        criteria.set(root[Pessoa_.nome], dto.nome)
        criteria.set(root[Pessoa_.email], dto.email)
        criteria.set(root[Pessoa_.senha], dto.senha)
        criteria.where(builder.equal(root[Pessoa_.codigo], dto.codigo))
        try {
            entityManager.createQuery(criteria).executeUpdate()
            return buscarPessoaAtualizada(dto.codigo)
        }catch (e: Exception){
            e.printStackTrace()
            throw e
        }
    }

    private fun buscarPessoaAtualizada(codigoPessoa: Long): PessoaDetalhesDTO {
        val builder: CriteriaBuilder = entityManager.criteriaBuilder
        val criteria: CriteriaQuery<PessoaDetalhesDTO> = builder.createQuery(PessoaDetalhesDTO::class.java)
        val root: Root<Pessoa> = criteria.from(Pessoa::class.java)
        criteria.select(builder.construct(PessoaDetalhesDTO::class.java,
                root[Pessoa_.codigo],
                root[Pessoa_.nome],
                root[Pessoa_.email],
                root[Pessoa_.senha]))
        criteria.where(builder.equal(root[Pessoa_.codigo], codigoPessoa))
        val query : TypedQuery<PessoaDetalhesDTO> = entityManager.createQuery(criteria)
        var dto : PessoaDetalhesDTO?
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

}