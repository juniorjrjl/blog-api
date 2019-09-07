package br.com.blog.blogapi.resource.tag

import br.com.blog.blogapi.exceptionhandler.BlogApiExceptionHandler
import br.com.blog.blogapi.resource.TagResource
import br.com.blog.blogapi.service.TagService
import com.fasterxml.jackson.databind.SerializationFeature
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.MessageSource
import org.springframework.data.web.PageableHandlerMethodArgumentResolver
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver
import org.springframework.web.method.HandlerMethod



@SpringBootTest
abstract class AbstractTagServiceTest {

    protected val urlConsulta = "/tags"

    protected lateinit var mockMvc: MockMvc

    @Mock
    protected val tagService: TagService = mock()

    @Autowired
    protected lateinit var tagResource: TagResource

    @Autowired
    protected lateinit var messageSource: MessageSource

    @Autowired
    protected lateinit var webApplicationContext: WebApplicationContext

    private fun createExceptionResolver(): ExceptionHandlerExceptionResolver {
        val exceptionResolver = object : ExceptionHandlerExceptionResolver() {
            override fun getExceptionHandlerMethod(handlerMethod: HandlerMethod?, exception: Exception): ServletInvocableHandlerMethod {
                val method = ExceptionHandlerMethodResolver(BlogApiExceptionHandler::class.java).resolveMethod(exception)
                return ServletInvocableHandlerMethod(BlogApiExceptionHandler(messageSource), method!!)
            }
        }
        exceptionResolver.afterPropertiesSet()
        return exceptionResolver
    }

    @BeforeEach
    fun setUp(){
        tagResource = TagResource(tagService)
        val mappingJackson2HttpMessageConverter = MappingJackson2HttpMessageConverter()
        mappingJackson2HttpMessageConverter.objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build()
    }

}