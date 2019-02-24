package br.com.blog.blogapi.repository

import br.com.blog.blogapi.model.Pessoa
import br.com.blog.blogapi.model.Post
import br.com.blog.blogapi.model.Tag
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class `Teste Crud Básico Tag` {

    @Autowired
    lateinit var tagRepository: TagRepository

    @Test
    fun `Quando inserir tag, retornar com código`(){
        val tag = Tag("Informativo")
        val tagSalva = tagRepository.save(tag)
        assertEquals(tag.descricao, tagSalva.descricao)
        assertTrue(tagSalva.codigo > 0)
    }

    @Test
    fun `Quando insert violar constraint, disparar exception`(){
        val descricao = "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901"
        val tag = Tag(descricao)
        val ex = assertThrows<Exception> { tagRepository.save(tag) }
        assertNotNull(ex)
    }

    @Test
    fun `Testando update Tag`(){
        val tag = Tag("Informativo")
        var tagSalva = tagRepository.save(tag)
        tagSalva.descricao = "Informativo novo"
        tagRepository.save(tagSalva)
        val tagRecuperada : Tag = tagRepository.findById(tag.codigo).get()
        assertEquals(tagSalva.descricao, tagRecuperada.descricao)
    }

    @Test
    fun `Quando update violar contraint, disparar exception`(){
        val descricao = "1234567890123456789012345678901234567890123456789012345678901234567890" +
                "12345678901234567890123456789012345678901234567890123456789012345678901"
        val tag = Tag("Informativo")
        var tagSalva = tagRepository.save(tag)
        tagSalva.descricao = descricao
        val ex = assertThrows<Exception> { tagRepository.save(tagSalva) }
        assertNotNull(ex)
    }

    @Test
    fun `Testando exclusão tag`(){
        val tag = Tag("Informativo")
        var tagSalva = tagRepository.save(tag)
        tagRepository.deleteById(tagSalva.codigo)
        assertFalse(tagRepository.findById(tagSalva.codigo).isPresent)
    }

    @ExtendWith(SpringExtension::class)
    @SpringBootTest
    class `Teste bloqueio exclusão Tag vinculada ao Post` {

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
        fun `Quando tentar excluir tag vinculada em um Post, disparar exception`(){
            val ex = assertThrows<Exception>{ tagRepository.deleteById(tag!!.codigo) }
            assertNotNull(ex)
            assertTrue(tagRepository.findById(tag!!.codigo).isPresent)
            assertTrue(pessoaRepository.findById(pessoa!!.codigo).isPresent)
            assertTrue(postRepository.findById(post!!.codigo).isPresent)
        }

        @AfterEach
        fun `tearDown`(){
            postRepository.deleteAll()
            postRepository.deleteAll()
            postRepository.deleteAll()
        }

    }

}