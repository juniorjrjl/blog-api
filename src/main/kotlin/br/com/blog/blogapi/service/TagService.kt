package br.com.blog.blogapi.service

import br.com.blog.blogapi.config.mapper.TagConverter
import br.com.blog.blogapi.dto.TagAtualizarDTO
import br.com.blog.blogapi.dto.TagCadastrarDTO
import br.com.blog.blogapi.dto.TagDetalhesDTO
import br.com.blog.blogapi.dto.TagListagem
import br.com.blog.blogapi.repository.TagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class TagService
@Autowired constructor(
        private val tagRepository: TagRepository,
        private val tagConverter: TagConverter
){

    fun cadastrar(dto: TagCadastrarDTO): TagDetalhesDTO {
        val dtoRetorno : TagDetalhesDTO
        try {
            val tag = tagRepository.save(tagConverter.DTOToModel(dto))
            dtoRetorno = buscarPorCodigo(tag.codigo)
        }catch (e: Exception){
            throw e
        }
        return dtoRetorno
    }

    fun excluir (codigoTag: Long){
        try {
            tagRepository.deleteById(codigoTag)
        }catch (e: Exception){
            throw e
        }
    }

    fun atualizar(dto: TagAtualizarDTO): TagDetalhesDTO {
        val dtoRetorno : TagDetalhesDTO
        try {
            tagRepository.atualizar(dto)
            dtoRetorno = buscarPorCodigo(dto.codigo)
        }catch (e: Exception){
            throw e
        }
        return dtoRetorno
    }

    fun buscarPorCodigo(codigoTag: Long): TagDetalhesDTO {
        val dtoRetorno : TagDetalhesDTO
        try {
            dtoRetorno = tagRepository.buscarPorCodigo(codigoTag)
        }catch (e: Exception){
            throw e
        }
        return dtoRetorno
    }

    fun listagem(paginavel: Pageable): Page<TagListagem> {
        val dto: Page<TagListagem>
        try{
            dto = tagRepository.listagem(paginavel)
        }catch (e: Exception){
            throw e
        }
        return dto
    }

}