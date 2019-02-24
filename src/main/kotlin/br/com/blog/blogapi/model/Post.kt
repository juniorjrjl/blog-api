package br.com.blog.blogapi.model

import java.time.LocalDateTime
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.PrePersist
import javax.persistence.PreUpdate
import javax.persistence.Table
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@Entity
@Table(name = "POSTS")
data class Post (

        @Column(nullable = false, length = 140)
        @NotNull
        @Size(min = 2, max = 140)
        var titulo : String,

        @Column(nullable = false)
        @NotNull
        var descricao: String,

        @ManyToOne
        @JoinColumn(name = "codigo_pessoa")
        var pessoa: Pessoa
){
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var codigo : Long = 0

    @ManyToMany(cascade = [CascadeType.MERGE, CascadeType.PERSIST])
    @JoinTable(name = "POSTS_TAGS",
            joinColumns = [JoinColumn(name = "CODIGO_POST")],
            inverseJoinColumns = [JoinColumn(name = "CODIGO_TAG")])
    var tags: List<Tag> = emptyList()

    @Column
    var dataEdicao: LocalDateTime? = null

    @Column
    var dataCriacao: LocalDateTime? = null

    @PrePersist
    fun onCreate(){
        dataCriacao = LocalDateTime.now()
    }

    @PreUpdate
    fun onUpdate(){
        dataEdicao = LocalDateTime.now()
    }

}