package br.com.blog.blogapi.repository.query.impl

import br.com.blog.blogapi.dto.PessoaAtualizarDTO
import br.com.blog.blogapi.dto.PessoaDetalhesDTO
import br.com.blog.blogapi.model.Pessoa
import br.com.blog.blogapi.model.Post_.pessoa
import br.com.blog.blogapi.repository.PessoaRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
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
class `Testando a implementação da classe query da pessoa` {

    @Autowired
    lateinit var pessoaRepository: PessoaRepository

    var pessoaSalva: Pessoa? = null

    @BeforeEach
    fun `setUp`(){
        pessoaSalva = pessoaRepository.save(Pessoa("nome", "email", "senha"))
    }

    @AfterEach
    fun `tearDown`(){
        pessoaRepository.deleteAll()
    }

    @Test
    fun `Quando alterar pessoa, retornar alterações`(){
        val nome = "novo Nome"
        val email = "novo email"
        val senha = "nova senha"
        val pessoaAtualizada: PessoaDetalhesDTO = pessoaRepository.atualizar(PessoaAtualizarDTO(pessoaSalva!!.codigo, nome, email, senha))
        Assertions.assertEquals(pessoaSalva!!.codigo, pessoaAtualizada.codigo)
        Assertions.assertEquals(nome, pessoaAtualizada.nome)
        Assertions.assertEquals(email, pessoaAtualizada.email)
        Assertions.assertEquals(senha, pessoaAtualizada.senha)
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
    fun `Quando constraint de campo violada disparar exception`(nome : String, email : String, senha : String){
        val ex = assertThrows<Exception> { pessoaRepository.atualizar(PessoaAtualizarDTO(pessoaSalva!!.codigo, nome, email, senha)) }
        Assertions.assertNotNull(ex)
    }

}