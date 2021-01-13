package pl.patrykdolata.lifemanager.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;


public class WebException extends RuntimeException {
    @Getter
    private final String message;
    @Getter
    private final HttpStatus statusCode;

    public WebException(String message, HttpStatus statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
}
