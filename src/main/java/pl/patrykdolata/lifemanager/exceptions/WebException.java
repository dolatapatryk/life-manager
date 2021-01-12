package pl.patrykdolata.lifemanager.exceptions;

import org.springframework.http.HttpStatus;

public class WebException extends RuntimeException {
    private final String message;
    private final HttpStatus statusCode;

    public WebException(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
