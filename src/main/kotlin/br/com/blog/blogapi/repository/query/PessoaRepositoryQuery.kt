package br.com.blog.blogapi.repository.query

import br.com.blog.blogapi.dto.PessoaAtualizarDTO
import br.com.blog.blogapi.dto.PessoaDetalhesDTO

interface PessoaRepositoryQuery {
    fun atualizar(dto: PessoaAtualizarDTO): PessoaDetalhesDTO
}