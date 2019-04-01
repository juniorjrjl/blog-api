package br.com.blog.blogapi.service

import br.com.blog.blogapi.config.mapper.PostConverter
import br.com.blog.blogapi.dto.PostCadastrarDTO
import br.com.blog.blogapi.dto.PostDetalhesDTO
import br.com.blog.blogapi.dto.TagDetalhesDTO
import br.com.blog.blogapi.model.Pessoa
import br.com.blog.blogapi.model.Post
import br.com.blog.blogapi.repository.PostRepository
import br.com.blog.blogapi.repository.TagRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
class `Verificando classe de servico do Post` {

    @Autowired
    lateinit var postService: PostService

    @Autowired
    lateinit var postConverter: PostConverter

    private val postRepository: PostRepository = mock()
    private val tagRepository: TagRepository = mock()

    @BeforeEach
    fun `setUp`(){
        postService = PostService(postRepository, tagRepository, postConverter)
    }

    @Test
    fun `verificando mapeamento do post`(){
        val post = argumentCaptor<Post>()
        whenever(postRepository.save(post.capture())).thenReturn(Post())
        whenever(postRepository.buscarPorCodigo(any())).thenReturn(PostDetalhesDTO(1L, "titulo", "descricao", "pessoa"))
        whenever(tagRepository.buscarTagsPost(any())).thenReturn(listOf(TagDetalhesDTO(1L, "tag 1"), TagDetalhesDTO(2L, "tag 2"), TagDetalhesDTO(3L, "tag 3")))
        val titulo = "titulo"
        val descricao = "descricao"
        val idPessoa = 1L
        val idsTags = listOf(1L, 2L, 3L, 4L)
        postService.cadastrar(PostCadastrarDTO(titulo, descricao, idPessoa, idsTags))
        val postObtido = post.firstValue
        assertEquals(descricao, postObtido.descricao)
        assertEquals(titulo, postObtido.titulo)
        assertEquals(idPessoa, postObtido.pessoa.codigo)
        assertEquals(0L, postObtido.codigo)
        assertEquals(descricao, postObtido.descricao)
        assertEquals(idsTags.size, postObtido.tags.filter { t -> t.codigo in idsTags }.size)
    }

}