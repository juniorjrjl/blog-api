package br.com.blog.blogapi.service

import br.com.blog.blogapi.dto.PessoaAtualizarDTO
import br.com.blog.blogapi.dto.PessoaCadastrarDTO
import br.com.blog.blogapi.dto.PessoaDetalhesDTO
import br.com.blog.blogapi.repository.PessoaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PessoaService {

    @Autowired
    lateinit var pessoaRepository: PessoaRepository

    fun cadastrar(dto: PessoaCadastrarDTO): PessoaDetalhesDTO{
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun excluir(codigoPessoa: Long) = pessoaRepository.deleteById(codigoPessoa)

    fun atualizar(dto: PessoaAtualizarDTO): PessoaDetalhesDTO{
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}