package br.com.blog.blogapi.repository.query.impl

import br.com.blog.blogapi.model.Tag
import br.com.blog.blogapi.repository.TagRepository
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.springframework.beans.factory.annotation.Autowired

class `Testando a implementação da classe query da tag` {

    @Autowired
    lateinit var tagRepository: TagRepository

    var tagSalva1: Tag? = null
    var tagSalva2: Tag? = null
    var tagSalva3: Tag? = null
    var tagSalva4: Tag? = null
    var tagSalva5: Tag? = null
    var tagSalva6: Tag? = null
    var tagSalva7: Tag? = null
    var tagSalva8: Tag? = null
    var tagSalva9: Tag? = null

    @BeforeEach
    fun `setUp`(){
        tagSalva1 = tagRepository.save(Tag("tag 1"))
        tagSalva2 = tagRepository.save(Tag("tag 2"))
        tagSalva3 = tagRepository.save(Tag("tag 3"))
        tagSalva4 = tagRepository.save(Tag("tag 4"))
        tagSalva5 = tagRepository.save(Tag("tag 5"))
        tagSalva6 = tagRepository.save(Tag("tag 6"))
        tagSalva7 = tagRepository.save(Tag("tag 7"))
        tagSalva8 = tagRepository.save(Tag("tag 8"))
        tagSalva9 = tagRepository.save(Tag("tag 9"))
    }

    @AfterEach
    fun `tearDown`(){
        tagRepository.deleteAll()
    }

}