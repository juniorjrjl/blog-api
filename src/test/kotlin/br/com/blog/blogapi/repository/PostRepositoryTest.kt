package br.com.blog.blogapi.repository

import br.com.blog.blogapi.model.Pessoa
import br.com.blog.blogapi.model.Post
import br.com.blog.blogapi.model.Tag
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class `Tests Crud Básico Post` {

    val texto141Caracteres = "12345678901234567890123456789012345678901234567890123456789012345678901234567890123456789" +
            "0123456789012345678901234567890123456789012345678901"

    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var pessoaRepository: PessoaRepository

    var pessoa1: Pessoa? = null

    var pessoa2: Pessoa? = null


    @BeforeEach
    fun `setUp`(){
        pessoa1 = pessoaRepository.save(Pessoa("nome 1", "e-mail 1", "senha 1"))
        pessoa2 = pessoaRepository.save(Pessoa("nome 2", "e-mail 2", "senha 2"))
    }

    @AfterEach
    fun `tearDown`(){
        pessoaRepository.deleteById(pessoa1!!.codigo)
        pessoaRepository.deleteById(pessoa2!!.codigo)
    }

    @Test
    fun `Quando inserir tag, retornar com código`(){
        val post = Post("título", "descricao", pessoa1!!)
        val postSalvo = postRepository.save(post)
        assertNotNull(postSalvo.codigo)
        assertNotNull(postSalvo.dataCriacao)
        assertNull(postSalvo.dataEdicao)
        assertNotNull(postSalvo.pessoa)
        assertEquals(post.titulo, postSalvo.titulo)
        assertEquals(post.descricao, postSalvo.descricao)
    }

    @Test
    fun `Quando insert violar constraint, disparar exception`(){
        val post = Post(texto141Caracteres, "", pessoa1!!)
        val ex = assertThrows<Exception> { postRepository.save(post) }
        Assertions.assertNotNull(ex)
    }

    @Test
    fun `Testando update Tag`(){
        val post = Post("título", "descrição", pessoa1!!)
        var postSalvo = postRepository.save(post)
        postSalvo.titulo = "novo título"
        postSalvo.descricao = "nova descrição"
        postSalvo.pessoa = pessoa2!!
        postRepository.save(postSalvo)
        val postRecuperado : Post = postRepository.findById(post.codigo).get()
        assertEquals(postSalvo.titulo, postRecuperado.titulo)
        assertEquals(postSalvo.descricao, postRecuperado.descricao)
        assertEquals(postSalvo.pessoa, postRecuperado.pessoa)
    }

    @Test
    fun `Quando update violar contraint, disparar exception`(){
        val post = Post("título", "descrição", pessoa1!!)
        var postSalvo = postRepository.save(post)
        postSalvo.pessoa = pessoa1!!
        postSalvo.titulo = texto141Caracteres
        val ex = assertThrows<Exception> { postRepository.save(postSalvo) }
        assertNotNull(ex)
    }

    @Test
    fun `Testando exclusão post`(){
        val post = Post("título", "descricao", pessoa1!!)
        val postSalvo = postRepository.save(post)
        postRepository.deleteById(postSalvo.codigo)
        Assertions.assertFalse(postRepository.findById(postSalvo.codigo).isPresent)
    }

    @ExtendWith(SpringExtension::class)
    @SpringBootTest
    class `Teste de exclusão de Post com vínculos`{

        @Autowired
        lateinit var tagRepository: TagRepository

        @Autowired
        lateinit var postRepository: PostRepository

        @Autowired
        lateinit var pessoaRepository: PessoaRepository

        var tag: Tag? = null

        var pessoa: Pessoa? = null

        var post: Post? = null

        @BeforeEach
        fun `setUp`(){
            tag = tagRepository.save(Tag("Entreterimento"))
            pessoa = pessoaRepository.save(Pessoa("nome", "email", "senha"))
            post = Post("título", "Descrição", pessoa!!)
            post = postRepository.save(post!!)
            post!!.tags = listOf(tagRepository.findById(tag!!.codigo).get())
            post = postRepository.save(post!!)
        }

        @Test
        fun `Quando excluir post, manter tag e pessoa`(){
            postRepository.deleteById(post!!.codigo)
            assertTrue(tagRepository.findById(tag!!.codigo).isPresent)
            assertTrue(pessoaRepository.findById(pessoa!!.codigo).isPresent)
            assertFalse(postRepository.findById(post!!.codigo).isPresent)
        }

        @AfterEach
        fun `tearDown`(){
            postRepository.deleteAll()
            postRepository.deleteAll()
            postRepository.deleteAll()
        }

    }

}