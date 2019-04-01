package br.com.blog.blogapi.service

import br.com.blog.blogapi.config.mapper.PessoaConverter
import br.com.blog.blogapi.dto.PessoaCadastrarDTO
import br.com.blog.blogapi.model.Pessoa
import br.com.blog.blogapi.model.Pessoa_.*
import br.com.blog.blogapi.repository.PessoaRepository
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

@ExtendWith(SpringExtension::class)
@SpringBootTest
class `Verificando classe de servico da Pessoa`{

    @Autowired
    lateinit var pessoaService: PessoaService

    @Autowired
    lateinit var pessoaConverter: PessoaConverter

    private val pessoaRepository: PessoaRepository = mock()

    @BeforeEach
    fun `setUp`(){
        pessoaService = PessoaService(pessoaRepository, pessoaConverter)
    }

    @Test
    fun `verificando mapeamento do cadastro de pessoa`(){
        val pessoa = argumentCaptor<Pessoa>()
        val nome = "nome"
        val email = "email"
        val senha = "senha"
        whenever(pessoaRepository.save(pessoa.capture())).thenReturn(Pessoa())
        pessoaService.cadastrar(PessoaCadastrarDTO(nome, email, senha, senha))
        val pessoaObtida = pessoa.firstValue
        assertEquals(nome, pessoaObtida.nome)
        assertEquals(email, pessoaObtida.email)
        assertEquals(senha, pessoaObtida.senha)
        assertEquals(0L, pessoaObtida.codigo)
        assertTrue(pessoaObtida.posts.isEmpty())
    }

}