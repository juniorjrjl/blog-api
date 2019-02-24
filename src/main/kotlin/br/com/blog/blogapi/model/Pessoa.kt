package br.com.blog.blogapi.model

import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size


@Entity
@Table(name = "pessoas")
data class Pessoa (

        @Column(nullable = false, length = 140)
        @NotNull
        @Size(min = 2, max = 140)
        var nome : String,

        @Column(nullable = false, length = 140)
        @NotNull
        @Email
        @Size(min = 2, max = 140)
        var email : String,

        @Column(nullable = false, length = 40)
        @NotNull
        @Size(min = 8, max = 40)
        var senha : String
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var codigo : Long = 0

    @OneToMany(mappedBy = "pessoa", cascade = [CascadeType.ALL], orphanRemoval = true)
    var posts : List<Post> = emptyList()
}