package br.com.blog.blogapi.resource.tag

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

class `Teste do endpoint de inclusão Tags`(): AbstractTagServiceTest(){

    @BeforeEach
    override fun setUp() {
        super.setUp()
    }

    @Test
    fun `Quando ocorrer erro no servidr, retornar internalserver Error`(){
        mockMvc.perform(MockMvcRequestBuilders.post(urlConsulta)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isInternalServerError)
                .andReturn()
    }

    companion object {
        val texto141Caracteres = "12345678901234567890123456789012345678901234567890123456789012345678901234567890" +
                "1234567890123456789012345678901234567890123456789012345678901"
        var json: String = "{\"descricao\":\"%s\"}"
        @JvmStatic
        fun tagsInvalidas() = listOf(
                Arguments.of(String.format(json, texto141Caracteres), "Size.tagCadastrarDTO.descricao"),
                Arguments.of(String.format(json, ""), "NotBlank.tagCadastrarDTO.descricao"),
                Arguments.of(String.format(json, "    "), "NotBlank.tagCadastrarDTO.descricao"),
                Arguments.of(String.format(json, "null"), "NotNull.tagCadastrarDTO.descricao")
        )
    }

    @ParameterizedTest
    @MethodSource("tagsInvalidas")
    fun `verificando constraints do DTO de cadastro de tags`(json: String, mensagemErroEsperada: String){
        mockMvc.perform(MockMvcRequestBuilders.post(urlConsulta)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)
                .andDo{
                    val retorno = it.response.contentAsString
                    assertTrue(retorno.contains(messageSource.getMessage(mensagemErroEsperada, null, LocaleContextHolder.getLocale())))
                }
    }

}