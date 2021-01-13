package pl.patrykdolata.lifemanager.controller.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.patrykdolata.lifemanager.exceptions.WebException;
import pl.patrykdolata.lifemanager.model.ErrorResponse;
import pl.patrykdolata.lifemanager.util.ResponseUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@ControllerAdvice
public class WebExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class)
    public final ResponseEntity<ErrorResponse> handleAuthenticationExceptions(Exception ex, HttpServletRequest request) {
        return ResponseUtils.response(createErrorResponse(ex, HttpStatus.UNAUTHORIZED.value(), request),
                HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(WebException.class)
    public final ResponseEntity<ErrorResponse> handleWebExceptions(WebException ex, HttpServletRequest request) {
        return ResponseUtils.response(createErrorResponse(ex, ex.getStatusCode().value(), request),
                ex.getStatusCode());
    }

    private ErrorResponse createErrorResponse(Exception ex, int status, HttpServletRequest request) {
        return new ErrorResponse(ex.getMessage(), status, new Date().getTime(), request.getRequestURI());
    }
}
