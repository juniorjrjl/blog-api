package br.com.blog.blogapi.resource.tag

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class `Teste do endpoint de atualização Tags`(): AbstractTagServiceTest(){

    @BeforeEach
    override fun setUp() {
        super.setUp()
    }

    @Test
    fun `Quando ocorrer erro no servidr, retornar internalserver Error`(){
        mockMvc.perform(MockMvcRequestBuilders.put(urlConsulta)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError)
                .andReturn()
    }

}