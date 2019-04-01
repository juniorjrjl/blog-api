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
        try{
            pessoaRepository.save(pessoaConverter.DTOToModel(dto))
        }catch (e: Exception){
            throw e
        }
    }

    fun excluir(codigoPessoa: Long) {
        try{
            pessoaRepository.deleteById(codigoPessoa)
        }catch (e: Exception){
            throw e
        }
    }

    fun atualizar(dto: PessoaAtualizarDTO){
        try{
            pessoaRepository.atualizar(dto)
        }catch (e: Exception){
            throw e
        }
    }

}