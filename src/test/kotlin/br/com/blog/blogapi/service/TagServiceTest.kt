package br.com.blog.blogapi.service

import br.com.blog.blogapi.config.mapper.TagConverter
import br.com.blog.blogapi.dto.TagCadastrarDTO
import br.com.blog.blogapi.dto.TagDetalhesDTO
import br.com.blog.blogapi.model.Tag
import br.com.blog.blogapi.repository.TagRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class `Verificando classe de servico da Tag` {

    @Autowired
    lateinit var tagService: TagService

    @Autowired
    lateinit var tagConverter: TagConverter

    private val tagRepository: TagRepository = mock()

    @BeforeEach
    fun `setUp`(){
        tagService = TagService(tagRepository, tagConverter)
    }

    @Test
    fun `verificando mapeamento do cadastro de tag`(){
        val tag = argumentCaptor<Tag>()
        val descricao = "tag 1"
        whenever(tagRepository.save(tag.capture())).thenReturn(Tag())
        whenever(tagRepository.buscarPorCodigo(any())).thenReturn(TagDetalhesDTO(1, "teste"))
        tagService.cadastrar(TagCadastrarDTO(descricao))
        assertEquals(descricao, tag.firstValue.descricao)
    }

}