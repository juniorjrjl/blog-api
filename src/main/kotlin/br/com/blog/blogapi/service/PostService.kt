package br.com.blog.blogapi.service

import br.com.blog.blogapi.dto.PostAtualizarDTO
import br.com.blog.blogapi.dto.PostCadastrarDTO
import br.com.blog.blogapi.dto.PostDetalhesDTO
import br.com.blog.blogapi.dto.PostListagemDTO
import br.com.blog.blogapi.dto.TagAtualizarDTO
import br.com.blog.blogapi.dto.TagCadastrarDTO
import br.com.blog.blogapi.dto.TagDetalhesDTO
import br.com.blog.blogapi.repository.PostRepository
import br.com.blog.blogapi.repository.query.filtro.PostListagemFiltro
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class PostService {

    @Autowired
    lateinit var postRepository: PostRepository

    fun cadastrar(dto: PostCadastrarDTO): PostDetalhesDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun excluir (codigoPost: Long){
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun atualizar(dto: PostAtualizarDTO): PostDetalhesDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun buscarPorCodigo(codigoPost: Long): PostDetalhesDTO{
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun listagem(filtro: PostListagemFiltro, pagable: Pageable): Page<PostListagemDTO>{
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}