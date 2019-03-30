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


    fun cadastrar(dto: PostCadastrarDTO): PostDetalhesDTO {
        var post = postRepository.save(postConverter.DTOToModel(dto))
        return buscarPorCodigo(post.codigo)!!
    }

    fun excluir (codigoPost: Long) = postRepository.deleteById(codigoPost)

    fun atualizar(dto: PostAtualizarDTO): PostDetalhesDTO {
        return buscarPorCodigo(dto.codigo)!!
    }

    fun buscarPorCodigo(codigoPost: Long): PostDetalhesDTO?{
        val dto : PostDetalhesDTO? = postRepository.buscarPorCodigo(codigoPost)
        if (dto != null){
            dto.tags = tagRepository.buscarTagsPost(dto.codigo)
        }
        return dto
    }

    fun listagem(filtro: PostListagemFiltro, paginavel: Pageable): Page<PostListagemDTO>{
        val dto : Page<PostListagemDTO> = postRepository.listagem(filtro, paginavel)
        dto.forEach{it.tags = tagRepository.buscarTagsPost(it.codigo)}
        return dto
    }

}