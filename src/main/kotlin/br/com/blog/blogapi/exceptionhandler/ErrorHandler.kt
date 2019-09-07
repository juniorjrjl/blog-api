package br.com.blog.blogapi.exceptionhandler

class ErrorHandler constructor(
        val mensaegmUsuario: String,
        val mensagemDesenvolvedor: String
){
    constructor(): this("", "")
}