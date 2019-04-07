package br.com.blog.blogapi.resource.tag

import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.whenever
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class `Teste do endpoint de exclusão Tags`() : AbstractTagServiceTest() {

    @BeforeEach
    override fun setUp() {
        super.setUp()
        doNothing().whenever(tagService).excluir(1L)
        given(tagService.excluir(-1L)).willAnswer{throw Exception()}
    }

    @Test
    fun `Quando excluir normalmente, retornar NoContent`(){
        mockMvc.perform(MockMvcRequestBuilders.delete(urlConsulta.plus("1"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent)
                .andReturn()
    }

    @Test
    fun `Quando ocorrer erro no servidr, retornar internalserver Error`(){
        mockMvc.perform(MockMvcRequestBuilders.delete(urlConsulta.plus("-1"))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError)
                .andReturn()
    }

}