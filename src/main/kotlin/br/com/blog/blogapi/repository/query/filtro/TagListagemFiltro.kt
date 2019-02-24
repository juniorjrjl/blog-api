package br.com.blog.blogapi.repository.query.filtro

class TagListagemFiltro {
    var descricao: String = ""
        get() = if (this.descricao.isBlank()) this.descricao else "%" + this.descricao + "%"
    var ordemDescricaoCrescente = true
}