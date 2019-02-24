package br.com.blog.blogapi.repository

import br.com.blog.blogapi.model.Pessoa
import br.com.blog.blogapi.repository.query.PessoaRepositoryQuery
import org.springframework.data.jpa.repository.JpaRepository

interface PessoaRepository: JpaRepository<Pessoa, Long>, PessoaRepositoryQuery {
}