package br.com.blog.blogapi.repository

import br.com.blog.blogapi.model.Post
import br.com.blog.blogapi.repository.query.PostRepositoryQuery
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository : JpaRepository<Post, Long>, PostRepositoryQuery {

}