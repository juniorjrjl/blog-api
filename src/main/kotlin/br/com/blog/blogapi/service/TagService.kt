package br.com.blog.blogapi.service

import br.com.blog.blogapi.dto.TagAtualizarDTO
import br.com.blog.blogapi.dto.TagCadastrarDTO
import br.com.blog.blogapi.dto.TagDetalhesDTO
import br.com.blog.blogapi.dto.TagListagem
import br.com.blog.blogapi.repository.TagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class TagService {

    @Autowired
    lateinit var tagRepository: TagRepository

    fun cadastrar(dto: TagCadastrarDTO): TagDetalhesDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun excluir (codigoTag: Long){
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun atualizar(dto: TagAtualizarDTO): TagDetalhesDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun buscarPorCodigo(codigoTag: Long): TagDetalhesDTO {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun listagem(filtro: TagListagem): List<TagListagem> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}