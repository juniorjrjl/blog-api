package br.com.blog.blogapi.service

import br.com.blog.blogapi.config.mapper.PessoaConverter
import br.com.blog.blogapi.dto.PessoaAtualizarDTO
import br.com.blog.blogapi.dto.PessoaCadastrarDTO
import br.com.blog.blogapi.dto.PessoaDetalhesDTO
import br.com.blog.blogapi.repository.PessoaRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PessoaService
@Autowired constructor(
        private val pessoaRepository: PessoaRepository,
        private val pessoaConverter: PessoaConverter
) {

    fun cadastrar(dto: PessoaCadastrarDTO){
        pessoaRepository.save(pessoaConverter.DTOToModel(dto))
    }

    fun excluir(codigoPessoa: Long) = pessoaRepository.deleteById(codigoPessoa)

    fun atualizar(dto: PessoaAtualizarDTO){
        pessoaRepository.atualizar(dto)
    }

}