package br.com.blog.blogapi.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "tags")
data class Tag (

        @Column(nullable = false, length = 140)
        @NotNull
        @Size(min = 2, max = 140)
        var descricao: String

){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var codigo : Long = 0

    @ManyToMany
    var posts: List<Post> = emptyList()

}