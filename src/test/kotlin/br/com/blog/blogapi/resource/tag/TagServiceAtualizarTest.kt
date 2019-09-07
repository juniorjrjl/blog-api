package br.com.blog.blogapi.resource.tag

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.provider.Arguments
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

    companion object {
        val texto141Caracteres = "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901"
        /*@JvmStatic
        fun tagsInvalidas() = listOf(
                //Arguments.of(Tag)
        )*/
    }

}