package br.com.blog.blogapi.repository

import br.com.blog.blogapi.model.Tag
import br.com.blog.blogapi.repository.query.TagRepositoryQuery
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository: JpaRepository<Tag, Long>, TagRepositoryQuery