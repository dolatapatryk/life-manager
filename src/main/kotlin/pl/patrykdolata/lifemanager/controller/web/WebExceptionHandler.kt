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
import javax.servlet.http.HttpServletRequest

@ControllerAdvice
class WebExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(AuthenticationException::class)
    fun handleAuthenticationExceptions(ex: Exception, request: HttpServletRequest): ResponseEntity<ErrorResponse> =
            response(createErrorResponse(ex, HttpStatus.UNAUTHORIZED.value(), request), HttpStatus.UNAUTHORIZED)

    @ExceptionHandler(WebException::class)
    fun handleWebExceptions(ex: WebException, request: HttpServletRequest): ResponseEntity<ErrorResponse> =
            response(createErrorResponse(ex, ex.statusCode.value(), request), ex.statusCode)

    private fun createErrorResponse(ex: Exception, status: Int, request: HttpServletRequest): ErrorResponse =
            ErrorResponse(ex.message!!, status, Date().time, request.requestURI)
}
