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
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class `Tests Crud Básico Pessoa` {

    @Autowired
    lateinit var pessoaRepository: PessoaRepository

    @Test
    fun `Quando inserir Pessoa, retornar código`(){
        val pessoa = Pessoa("nome", "email@email.com.br", "123456")
        val pessoaSalva = pessoaRepository.save(pessoa)
        assertEquals(pessoa.nome, pessoaSalva.nome)
        assertEquals(pessoa.email, pessoaSalva.email)
        assertEquals(pessoa.senha, pessoaSalva.senha)
        assertNotNull(pessoaSalva.codigo)
    }

    companion object {
        val texto141Caracteres = "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901"
        val texto41Caracteres = "12345678901234567890123456789012345678901"
        @JvmStatic
        fun pessoasPropriedadesInvalidas() = listOf(
                Arguments.of(texto141Caracteres, "email", "senha"),
                Arguments.of("nome", texto141Caracteres, "senha"),
                Arguments.of("nome", "email", texto41Caracteres)
        )
    }

    @ParameterizedTest
    @MethodSource("pessoasPropriedadesInvalidas")
    fun `Quando insert violar constraint, disparar exception`(nome : String, email : String, senha : String){
        val pessoa = Pessoa(nome, email, senha)
        val ex = assertThrows<Exception> { pessoaRepository.save(pessoa) }
        assertNotNull(ex)
    }

    @Test
    fun `Testando update Pessoa`(){
        val pessoa = Pessoa("nome", "email@email.com.br", "123456")
        var pessoaSalva = pessoaRepository.save(pessoa)
        pessoaSalva.email = "novo e-mail"
        pessoaSalva.nome = "novo nome"
        pessoaSalva.senha = "654321"
        pessoaRepository.save(pessoaSalva)
        val pessoaRecuperada : Pessoa = pessoaRepository.findById(pessoa.codigo).get()
        assertEquals(pessoaSalva.nome, pessoaRecuperada.nome)
        assertEquals(pessoaSalva.email, pessoaRecuperada.email)
        assertEquals(pessoaSalva.senha, pessoaRecuperada.senha)
    }

    @ParameterizedTest
    @MethodSource("pessoasPropriedadesInvalidas")
    fun `Quando update violar constraint, disparar exception`(nome : String, email : String, senha : String){
        val pessoa = Pessoa("nome", "email", "senha")
        var pessoaSalva = pessoaRepository.save(pessoa)
        pessoaSalva.email = email
        pessoaSalva.nome = nome
        pessoaSalva.senha = senha
        val ex = org.junit.jupiter.api.assertThrows<Exception> { pessoaRepository.save(pessoaSalva) }
        assertNotNull(ex)
    }

    @Test
    fun `Testando exclusão de Pessoa`(){
        val pessoa = Pessoa("nome", "email@email.com.br", "123456")
        var pessoaSalva = pessoaRepository.save(pessoa)
        pessoaRepository.deleteById(pessoaSalva.codigo)
        assertFalse(pessoaRepository.findById(pessoaSalva.codigo).isPresent)
    }

    @ExtendWith(SpringExtension::class)
    @SpringBootTest
    class `Verificando exclusão de Pessoa vinculada`{
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
            pessoaRepository.deleteById(pessoa!!.codigo)
            assertTrue(tagRepository.findById(tag!!.codigo).isPresent)
            assertFalse(pessoaRepository.findById(pessoa!!.codigo).isPresent)
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