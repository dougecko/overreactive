package com.shinesolutions.poc.overreactive.exceptions

import com.shinesolutions.poc.overreactive.exceptions.model.Error
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest

//import org.springframework.web.reactive.handler.WebFluxResponseStatusExceptionHandler

@ControllerAdvice
//class RestResponseEntityExceptionHandler : WebFluxResponseStatusExceptionHandler() {
class RestResponseEntityExceptionHandler {

    @ExceptionHandler(value = [ResourceNotFoundException::class])
    fun handleNotFoundException(ex: RuntimeException, request: WebRequest): ResponseEntity<Error> {
        val body = ex.localizedMessage
        val code = "POC-404"
        return handleExceptionInternal(code = code, body = body, status = HttpStatus.NOT_FOUND)
    }

    private fun handleExceptionInternal(code: String, body: String, status: HttpStatus): ResponseEntity<Error> {
        val error = Error(status, code, body)

        return ResponseEntity(error, status)
    }

}