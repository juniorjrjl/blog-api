package br.com.blog.blogapi.resource.tag

import br.com.blog.blogapi.resource.TagResource
import br.com.blog.blogapi.service.TagService
import com.fasterxml.jackson.databind.SerializationFeature
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders

@SpringBootTest
abstract class AbstractTagServiceTest {

    protected val urlConsulta = "/tags/"

    protected lateinit var mockMvc: MockMvc

    @Mock
    protected val tagService: TagService = mock()

    @Autowired
    protected lateinit var tagResource: TagResource

    @BeforeEach
    fun setUp(){
        tagResource = TagResource(tagService)
        val mappingJackson2HttpMessageConverter = MappingJackson2HttpMessageConverter()
        mappingJackson2HttpMessageConverter.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        mockMvc = MockMvcBuilders.standaloneSetup(tagResource)
                .setCustomArgumentResolvers(PageableHandlerMethodArgumentResolver())
                .setMessageConverters(mappingJackson2HttpMessageConverter)
                .build()
    }

}