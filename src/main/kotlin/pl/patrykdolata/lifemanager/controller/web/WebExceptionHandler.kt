package pl.patrykdolata.lifemanager.controller.web

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.AuthenticationException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import pl.patrykdolata.lifemanager.exceptions.WebException
import pl.patrykdolata.lifemanager.model.ErrorResponse
import pl.patrykdolata.lifemanager.util.ResponseUtils.response
import java.util.*
import javax.persistence.EntityNotFoundException
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class WebExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationExceptions(ex: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> =
            response(createErrorResponse(ex.message!!, HttpStatus.UNAUTHORIZED.value(), request), HttpStatus.UNAUTHORIZED)

    @ExceptionHandler(WebException::class)
    fun handleWebExceptions(ex: WebException, request: HttpServletRequest): ResponseEntity<ErrorResponse> =
            response(createErrorResponse(ex.message!!, ex.statusCode.value(), request), ex.statusCode)

    @ExceptionHandler(EntityNotFoundException::class)
    fun handleEntityNotFoundException(ex: EntityNotFoundException, request: HttpServletRequest)
            : ResponseEntity<ErrorResponse> {
        val message = ex.message ?: "Entity not found"
        return response(createErrorResponse(message, HttpStatus.NOT_FOUND.value(), request), HttpStatus.NOT_FOUND)
    }

    private fun createErrorResponse(message: String, status: Int, request: HttpServletRequest): ErrorResponse =
            ErrorResponse(message, status, Date().time, request.requestURI)
}
