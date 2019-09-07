package br.com.blog.blogapi.resource.tag

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

import br.com.blog.blogapi.dto.TagListagem
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import java.util.stream.Collectors
import java.util.stream.Stream

@SpringBootTest
class `Teste do endpoint de listagem Tags`() : AbstractTagServiceTest() {

    @BeforeEach
    override fun setUp(){
        super.setUp()
        val dto: Page<TagListagem> = PageImpl(Stream.of(
                TagListagem(1L, "Música"),
                TagListagem(2L, "Esporte"),
                TagListagem(3L, "Lazer"),
                TagListagem(4L, "Entreterimento"),
                TagListagem(5L, "Política"),
                TagListagem(6L, "Cultura"),
                TagListagem(7L, "Turismo"),
                TagListagem(8L, "Internacional"),
                TagListagem(9L, "Natureza"),
                TagListagem(10L, "Viagem")
        ).collect(Collectors.toList()))
        whenever(tagService.listagem(PageRequest.of(1, 10))).thenReturn(dto)
        whenever(tagService.listagem(PageRequest.of(2, 10))).thenReturn(PageImpl(emptyList()))
        given(tagService.listagem(PageRequest.of(3, 10))).willAnswer{throw Exception()}
    }

    @Test
    fun `Quando buscar registros existentes, retorna-los`(){
        val result: MvcResult =  mockMvc.perform(get(urlConsulta)
                .param("page", "1")
                .param("size", "10")
                .accept(MediaType.APPLICATION_JSON))
                .andDo {
                    MockMvcResultHandlers.print()
                    var teste = it
                }
                .andExpect(status().isOk)
                .andReturn()
        assertTrue(result.response.contentAsString.contains(""""codigo":1,"""))
        assertTrue(result.response.contentAsString.contains(""""codigo":2,"""))
        assertTrue(result.response.contentAsString.contains(""""codigo":3,"""))
        assertTrue(result.response.contentAsString.contains(""""codigo":4,"""))
        assertTrue(result.response.contentAsString.contains(""""codigo":5,"""))
        assertTrue(result.response.contentAsString.contains(""""codigo":6,"""))
        assertTrue(result.response.contentAsString.contains(""""codigo":7,"""))
        assertTrue(result.response.contentAsString.contains(""""codigo":8,"""))
        assertTrue(result.response.contentAsString.contains(""""codigo":9,"""))
        assertTrue(result.response.contentAsString.contains(""""codigo":10,"""))

        assertTrue(result.response.contentAsString.contains(""""descricao":"Música""""))
        assertTrue(result.response.contentAsString.contains(""""descricao":"Esporte""""))
        assertTrue(result.response.contentAsString.contains(""""descricao":"Lazer""""))
        assertTrue(result.response.contentAsString.contains(""""descricao":"Entreterimento""""))
        assertTrue(result.response.contentAsString.contains(""""descricao":"Política""""))
        assertTrue(result.response.contentAsString.contains(""""descricao":"Cultura""""))
        assertTrue(result.response.contentAsString.contains(""""descricao":"Turismo""""))
        assertTrue(result.response.contentAsString.contains(""""descricao":"Internacional""""))
        assertTrue(result.response.contentAsString.contains(""""descricao":"Natureza""""))
        assertTrue(result.response.contentAsString.contains(""""descricao":"Viagem""""))
    }

    @Test
    fun `Quando buscar registro inexistente, retornar Notfound`(){
         mockMvc.perform(get(urlConsulta)
                 .param("page", "2")
                 .param("size", "10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound)
                .andReturn()
    }

    @Test
    fun `Quando ocorrer erro no servidr, retornar internalserver Error`(){
        mockMvc.perform(get(urlConsulta)
                .param("page", "3")
                .param("size", "10")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isInternalServerError)
                .andReturn()
    }

}