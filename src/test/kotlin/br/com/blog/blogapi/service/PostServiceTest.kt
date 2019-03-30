package br.com.blog.blogapi.service

import br.com.blog.blogapi.config.mapper.PostConverter
import br.com.blog.blogapi.repository.PostRepository
import br.com.blog.blogapi.repository.TagRepository
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class `Verificando classe de servico do Post` {

    @Autowired
    lateinit var postService: PostService

    @Autowired
    lateinit var postConverter: PostConverter

    private val postRepository: PostRepository = mock()
    private val tagRepository: TagRepository = mock()

    @BeforeEach
    fun `setUp`(){
        postService = PostService(postRepository, tagRepository, postConverter)
    }

}