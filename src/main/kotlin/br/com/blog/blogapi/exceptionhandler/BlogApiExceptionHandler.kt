package br.com.blog.blogapi.exceptionhandler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType

@ControllerAdvice
class BlogApiExceptionHandler @Autowired constructor(private val messageSource: MessageSource ):
        ResponseEntityExceptionHandler() {

    private fun criarListaErros(bindingResult: BindingResult): List<ErrorHandler>{
        val errors: ArrayList<ErrorHandler> = ArrayList()
        bindingResult.fieldErrors.forEach{
            errors.add(ErrorHandler(messageSource.getMessage(it, LocaleContextHolder.getLocale()), it.toString()))
        }
        return errors
    }

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatus, request: WebRequest): ResponseEntity<Any>
        = handleExceptionInternal(ex, criarListaErros(ex.bindingResult), headers, HttpStatus.BAD_REQUEST, request)

}