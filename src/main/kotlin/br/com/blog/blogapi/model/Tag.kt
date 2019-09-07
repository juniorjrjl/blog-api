package br.com.blog.blogapi.model

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "tags")
data class Tag (
        @Column(nullable = false, length = 140)
        var descricao: String){
    constructor() :this("")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var codigo : Long = 0

    @ManyToMany
    var posts: List<Post> = emptyList()

}