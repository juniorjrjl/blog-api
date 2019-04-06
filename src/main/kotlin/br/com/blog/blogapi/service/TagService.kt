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

    fun cadastrar(dto: TagCadastrarDTO): TagDetalhesDTO =
            try {
                val tag = tagRepository.save(tagConverter.DTOToModel(dto))
                buscarPorCodigo(tag.codigo)
            }catch (e: Exception){
                throw e
            }

    fun excluir (codigoTag: Long) =
            try {
                tagRepository.deleteById(codigoTag)
            }catch (e: Exception){
                throw e
            }

    fun atualizar(dto: TagAtualizarDTO): TagDetalhesDTO =
            try {
                tagRepository.atualizar(dto)
                buscarPorCodigo(dto.codigo)
            }catch (e: Exception){
                throw e
            }

    fun buscarPorCodigo(codigoTag: Long): TagDetalhesDTO  =
            try {
                tagRepository.buscarPorCodigo(codigoTag)
            }catch (e: Exception){
                throw e
            }

    fun listagem(pageable: Pageable): Page<TagListagem> =
            try{
                tagRepository.listagem(pageable)
            }catch (e: Exception){
                throw e
            }

}