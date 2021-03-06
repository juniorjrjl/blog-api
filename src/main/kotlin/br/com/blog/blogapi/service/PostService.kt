package br.com.blog.blogapi.service

import br.com.blog.blogapi.config.mapper.PostConverter
import br.com.blog.blogapi.dto.PostAtualizarDTO
import br.com.blog.blogapi.dto.PostCadastrarDTO
import br.com.blog.blogapi.dto.PostDetalhesDTO
import br.com.blog.blogapi.dto.PostListagemDTO
import br.com.blog.blogapi.dto.TagAtualizarDTO
import br.com.blog.blogapi.dto.TagCadastrarDTO
import br.com.blog.blogapi.dto.TagDetalhesDTO
import br.com.blog.blogapi.repository.PostRepository
import br.com.blog.blogapi.repository.TagRepository
import br.com.blog.blogapi.repository.query.filtro.PostListagemFiltro
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PostService
@Autowired constructor(
        private val postRepository: PostRepository,
        private val tagRepository: TagRepository,
        private val postConverter: PostConverter
) {


    fun cadastrar(dto: PostCadastrarDTO): PostDetalhesDTO  =
            try{
                val post = postRepository.save(postConverter.DTOToModel(dto))
                buscarPorCodigo(post.codigo)!!
            }catch (e: Exception){
                throw e
            }

    fun excluir (codigoPost: Long) =
            try{
                postRepository.deleteById(codigoPost)
            }catch (e: Exception){
                throw e
            }

    fun atualizar(dto: PostAtualizarDTO): PostDetalhesDTO  =
            try{
                buscarPorCodigo(dto.codigo)!!
            }catch (e: Exception){
                throw e
            }

    fun buscarPorCodigo(codigoPost: Long): PostDetalhesDTO? =
            try{
                val dto  = postRepository.buscarPorCodigo(codigoPost)
                if (dto != null){
                    dto.tags = tagRepository.buscarTagsPost(dto.codigo)
                }
                dto
            }catch (e: Exception){
                throw e
            }

    fun listagem(filtro: PostListagemFiltro, paginavel: Pageable): Page<PostListagemDTO> =
            try{
                val dto = postRepository.listagem(filtro, paginavel)
                dto.forEach{it.tags = tagRepository.buscarTagsPost(it.codigo)}
                dto
            }catch (e: Exception){
                throw e
            }

}