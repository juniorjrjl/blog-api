package br.com.blog.blogapi.repository.query.impl

import br.com.blog.blogapi.model.Pessoa
import br.com.blog.blogapi.model.Post
import br.com.blog.blogapi.model.Post_.pessoa
import br.com.blog.blogapi.repository.PessoaRepository
import br.com.blog.blogapi.repository.PostRepository
import br.com.blog.blogapi.repository.query.filtro.PostListagemFiltro
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class `Testando a implementação da classe query da post` {

    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var pessoaRepository: PessoaRepository

    var pessoa1Salva1: Pessoa? = null
    var pessoa1Salva2: Pessoa? = null
    var pessoa1Salva3: Pessoa? = null

    var postSalvo1: Post? = null
    var postSalvo2: Post? = null
    var postSalvo3: Post? = null
    var postSalvo4: Post? = null
    var postSalvo5: Post? = null
    var postSalvo6: Post? = null
    var postSalvo7: Post? = null
    var postSalvo8: Post? = null
    var postSalvo9: Post? = null

    @BeforeEach
    fun `setUp`(){
        pessoa1Salva1 = pessoaRepository.save(Pessoa("nome 1", "email 1", "senha 1"))
        pessoa1Salva2 = pessoaRepository.save(Pessoa("nome 2", "email 2", "senha 2"))
        pessoa1Salva3 = pessoaRepository.save(Pessoa("nome 3", "email 3", "senha 3"))
        postSalvo1 = postRepository.save(Post("título 1", "descrição 1", pessoa1Salva1!!))
        postSalvo2 = postRepository.save(Post("título 2", "descrição 2", pessoa1Salva2!!))
        postSalvo3 = postRepository.save(Post("título 3", "descrição 3", pessoa1Salva3!!))
        postSalvo4 = postRepository.save(Post("título 4", "descrição 4", pessoa1Salva1!!))
        postSalvo5 = postRepository.save(Post("título 5", "descrição 5", pessoa1Salva2!!))
        postSalvo6 = postRepository.save(Post("título 6", "descrição 6", pessoa1Salva3!!))
        postSalvo7 = postRepository.save(Post("título 7", "descrição 7", pessoa1Salva1!!))
        postSalvo8 = postRepository.save(Post("título 8", "descrição 8", pessoa1Salva2!!))
        postSalvo9 = postRepository.save(Post("título 9", "descrição 9", pessoa1Salva3!!))
    }

    @AfterEach
    fun `tearDown`(){
        postRepository.deleteAll()
        pessoaRepository.deleteAll()
    }

    @Test
    fun `busca de post por código`(){
        val postRecuperado = postRepository.buscarPorCodigo(postSalvo1!!.codigo)
        assertEquals(postSalvo1!!.codigo, postRecuperado!!.codigo)
        assertEquals(postSalvo1!!.descricao, postRecuperado.descricao)
        assertTrue(postRecuperado.tags.isEmpty())
        assertEquals(postSalvo1!!.titulo, postRecuperado.titulo)
        assertEquals(postSalvo1!!.pessoa.nome, postRecuperado.pessoa)

    }

    @Test
    fun `quando informar codigo inexistente, retornar objeto nulo`(){
        assertNull(postRepository.buscarPorCodigo(postSalvo9!!.codigo + 1 ))
    }

    @Test
    fun `Buscando listagem de posts`() {
        val pagina = 0
        val tamanho = 3
        val pageable = PageRequest.of(pagina, tamanho)
        val filtro = PostListagemFiltro()
        val posts = postRepository.listagem(filtro, pageable)
        assertEquals(pagina, posts.number)
        assertEquals(tamanho, posts.size)
    }

}