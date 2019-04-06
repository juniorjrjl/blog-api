package br.com.blog.blogapi.resource

import br.com.blog.blogapi.dto.TagAtualizarDTO
import br.com.blog.blogapi.dto.TagCadastrarDTO
import br.com.blog.blogapi.dto.TagDetalhesDTO
import br.com.blog.blogapi.dto.TagListagem
import br.com.blog.blogapi.service.TagService
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
@RequestMapping("/tags")
class TagResource
@Autowired constructor(private val tagService: TagService){

    @PostMapping
    fun cadastrar(@Valid @RequestBody dto: TagCadastrarDTO): ResponseEntity<TagDetalhesDTO> =
            try{
                ResponseEntity.status(HttpStatus.CREATED).body(tagService.cadastrar(dto))
            }catch (e: Exception){
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
            }

    @DeleteMapping("/{codigo}")
    fun excluir(@PathVariable codigo: Long): ResponseEntity<Any> =
            try{
                tagService.excluir(codigo)
                ResponseEntity.status(HttpStatus.NO_CONTENT).build()
            }catch (e: Exception){
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
            }

    @PutMapping
    fun atualizar(@Valid @RequestBody dto: TagAtualizarDTO): ResponseEntity<TagDetalhesDTO> =
            try{
                ResponseEntity.status(HttpStatus.CREATED).body(tagService.atualizar(dto))
            }catch (e: Exception){
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
            }

    @GetMapping
    fun listagem(pageable: Pageable): ResponseEntity<Page<TagListagem>> =
         try{
            val dto: Page<TagListagem> = tagService.listagem(pageable)
            if (dto.isEmpty)  ResponseEntity.notFound().build() else ResponseEntity.ok(dto)
        }catch (e: Exception){
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }

}