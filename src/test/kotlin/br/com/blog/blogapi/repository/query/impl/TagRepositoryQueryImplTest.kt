package br.com.blog.blogapi.repository.query.impl

import br.com.blog.blogapi.dto.TagAtualizarDTO
import br.com.blog.blogapi.dto.TagDetalhesDTO
import br.com.blog.blogapi.model.Pessoa
import br.com.blog.blogapi.model.Post
import br.com.blog.blogapi.model.Tag
import br.com.blog.blogapi.repository.PessoaRepository
import br.com.blog.blogapi.repository.PostRepository
import br.com.blog.blogapi.repository.TagRepository
import br.com.blog.blogapi.repository.query.filtro.PostListagemFiltro
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class `Testando a implementação da classe query da tag` {

    @Autowired
    lateinit var tagRepository: TagRepository

    @Autowired
    lateinit var postRepository: PostRepository

    @Autowired
    lateinit var pessoaRepository: PessoaRepository

    var tagSalva1: Tag? = null
    var tagSalva2: Tag? = null
    var tagSalva3: Tag? = null
    var tagSalva4: Tag? = null
    var tagSalva5: Tag? = null
    var tagSalva6: Tag? = null
    var tagSalva7: Tag? = null
    var tagSalva8: Tag? = null
    var tagSalva9: Tag? = null

    var postSalvo1: Post? = null
    var postSalvo2: Post? = null
    var postSalvo3: Post? = null
    var postSalvo4: Post? = null
    var postSalvo5: Post? = null
    var postSalvo6: Post? = null
    var postSalvo7: Post? = null
    var postSalvo8: Post? = null
    var postSalvo9: Post? = null
    var postSalvo10: Post? = null
    var postSalvo11: Post? = null
    var postSalvo12: Post? = null
    var postSalvo13: Post? = null
    var postSalvo14: Post? = null
    var postSalvo15: Post? = null
    var postSalvo16: Post? = null
    var postSalvo17: Post? = null
    var postSalvo18: Post? = null

    @BeforeEach
    fun `setUp`(){
        tagSalva1 = tagRepository.save(Tag("tag 1"))
        tagSalva2 = tagRepository.save(Tag("tag 2"))
        tagSalva3 = tagRepository.save(Tag("tag 3"))
        tagSalva4 = tagRepository.save(Tag("tag 4"))
        tagSalva5 = tagRepository.save(Tag("tag 5"))
        tagSalva6 = tagRepository.save(Tag("tag 6"))
        tagSalva7 = tagRepository.save(Tag("tag 7"))
        tagSalva8 = tagRepository.save(Tag("tag 8"))
        tagSalva9 = tagRepository.save(Tag("tag 9"))
        val pessoaSalva = pessoaRepository.save(Pessoa("nome", "email", "senha"))
        postSalvo1 = Post("post 1", "descricao 1", pessoaSalva)
        postSalvo2 = Post("post 2", "descricao 2", pessoaSalva)
        postSalvo3 = Post("post 3", "descricao 3", pessoaSalva)
        postSalvo4 = Post("post 4", "descricao 4", pessoaSalva)
        postSalvo5 = Post("post 5", "descricao 5", pessoaSalva)
        postSalvo6 = Post("post 6", "descricao 6", pessoaSalva)
        postSalvo7 = Post("post 7", "descricao 7", pessoaSalva)
        postSalvo8 = Post("post 8", "descricao 8", pessoaSalva)
        postSalvo9 = Post("post 9", "descricao 9", pessoaSalva)
        postSalvo10 = Post("post 10", "descricao 10", pessoaSalva)
        postSalvo11 = Post("post 11", "descricao 11", pessoaSalva)
        postSalvo12 = Post("post 12", "descricao 12", pessoaSalva)
        postSalvo13 = Post("post 13", "descricao 13", pessoaSalva)
        postSalvo14 = Post("post 14", "descricao 14", pessoaSalva)
        postSalvo15 = Post("post 15", "descricao 15", pessoaSalva)
        postSalvo16 = Post("post 16", "descricao 16", pessoaSalva)
        postSalvo17 = Post("post 17", "descricao 17", pessoaSalva)
        postSalvo18 = Post("post 18", "descricao 18", pessoaSalva)
        postSalvo1!!.tags = listOfNotNull(tagSalva5, tagSalva3)
        postSalvo2!!.tags = listOfNotNull(tagSalva1)
        postSalvo3!!.tags = listOfNotNull(tagSalva1, tagSalva2, tagSalva6)
        postSalvo5!!.tags = listOfNotNull(tagSalva9, tagSalva8, tagSalva3, tagSalva2)
        postSalvo6!!.tags = listOfNotNull(tagSalva9)
        postSalvo8!!.tags = listOfNotNull(tagSalva4, tagSalva7)
        postSalvo9!!.tags = listOfNotNull(tagSalva1, tagSalva2, tagSalva3, tagSalva4, tagSalva5, tagSalva6, tagSalva7, tagSalva8, tagSalva9)
        postSalvo10!!.tags = listOfNotNull(tagSalva2, tagSalva3, tagSalva4, tagSalva5, tagSalva6, tagSalva7, tagSalva8, tagSalva9)
        postSalvo11!!.tags = listOfNotNull(tagSalva1, tagSalva2, tagSalva3, tagSalva4, tagSalva5, tagSalva6, tagSalva7, tagSalva8)
        postSalvo12!!.tags = listOfNotNull(tagSalva2, tagSalva4, tagSalva6, tagSalva8)
        postSalvo13!!.tags = listOfNotNull(tagSalva1, tagSalva3, tagSalva5, tagSalva7, tagSalva9)
        postSalvo14!!.tags = listOfNotNull(tagSalva1, tagSalva2, tagSalva3, tagSalva4)
        postSalvo15!!.tags = listOfNotNull(tagSalva5, tagSalva6, tagSalva7, tagSalva8, tagSalva9)
        postSalvo16!!.tags = listOfNotNull(tagSalva1)
    }

    @AfterEach
    fun `tearDown`(){
        pessoaRepository.deleteAll()
        postRepository.deleteAll()
        tagRepository.deleteAll()
    }

    @Test
    fun `Quando atualizar Tag, retornar objeto atualizado`(){
        val novaDescricao = "novo nome"
        val tagAtualizada : TagDetalhesDTO = tagRepository.atualizar(TagAtualizarDTO(tagSalva1!!.codigo, novaDescricao))
        assertEquals(tagSalva1!!.codigo, tagAtualizada.codigo)
        assertEquals(novaDescricao, tagAtualizada.descricao)
    }

    fun `Quando informar código tag, retornar objeto`(){
        val tagRecuperada : TagDetalhesDTO = tagRepository.buscarPorCodigo(tagSalva5!!.codigo)
        assertEquals(tagSalva5!!.codigo, tagRecuperada.codigo)
        assertEquals(tagSalva5!!.descricao, tagRecuperada.descricao)
    }

    fun `Quando informar código não cadastrado, retornar nulo`(){
        assertNull(tagRepository.buscarPorCodigo(tagSalva9!!.codigo + 1))
    }

    fun `Quando informar código post, retornar tags relacionadas`(){
        val tagsRecuperadas : List<TagDetalhesDTO> = tagRepository.buscarTagsPost(postSalvo10!!.codigo)
        assertSame(postSalvo10!!.tags, tagsRecuperadas)
    }

    @Test
    fun `Buscando listagem de posts`() {
        val pagina = 0
        val tamanho = 3
        val pageable = PageRequest.of(pagina, tamanho)
        val tags = tagRepository.listagem(pageable)
        assertEquals(pagina, tags.number)
        assertEquals(tamanho, tags.size)
    }

}