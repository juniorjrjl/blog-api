package br.com.blog.blogapi.resource

import br.com.blog.blogapi.dto.PostAtualizarDTO
import br.com.blog.blogapi.dto.PostCadastrarDTO
import br.com.blog.blogapi.dto.PostDetalhesDTO
import br.com.blog.blogapi.dto.PostListagemDTO
import br.com.blog.blogapi.repository.query.filtro.PostListagemFiltro
import br.com.blog.blogapi.service.PostService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/posts")
class PostResource
@Autowired constructor(private val postService: PostService){

    @PostMapping
    fun cadastrar(@Valid @RequestBody dto: PostCadastrarDTO): ResponseEntity<PostDetalhesDTO> =
            try{
                ResponseEntity.status(HttpStatus.CREATED).body(postService.cadastrar(dto))
            }catch (e: Exception){
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
            }

    @DeleteMapping("/{codigo}")
    fun excluir(@PathVariable codigo: Long): ResponseEntity<Any> =
            try{
                postService.excluir(codigo)
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            }catch (e: Exception){
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
            }

    @PutMapping
    fun atualizar(@Valid @RequestBody dto: PostAtualizarDTO): ResponseEntity<PostDetalhesDTO> =
            try{
                ResponseEntity.status(HttpStatus.CREATED).body(postService.atualizar(dto))
            }catch (e: Exception){
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
            }

    @GetMapping
    fun listagem(filtro: PostListagemFiltro, pageable: Pageable): ResponseEntity<Page<PostListagemDTO>> =
            try{
                val dto: Page<PostListagemDTO> = postService.listagem(filtro, pageable)
                if (dto.isEmpty)  ResponseEntity.notFound().build() else ResponseEntity.ok(dto)
            }catch (e: Exception){
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
            }

}