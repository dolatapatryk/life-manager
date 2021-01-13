package pl.patrykdolata.lifemanager.exceptions;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends WebException {

    public static final String MESSAGE = "Email is already in use";

    public EmailAlreadyExistsException() {
        super(MESSAGE, HttpStatus.CONFLICT);
    }
}
