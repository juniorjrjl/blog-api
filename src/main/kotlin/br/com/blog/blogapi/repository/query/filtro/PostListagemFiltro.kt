package br.com.blog.blogapi.repository.query.filtro

class PostListagemFiltro {
    private var _titulo : String = ""

    var tags = emptyList<Long>()
    var dataCriacaoDecrescente = true


    var titulo: String
        get() = if (_titulo.isNullOrBlank()) this._titulo else "%" + this._titulo + "%"
        set(value) {
            _titulo = value
        }
}