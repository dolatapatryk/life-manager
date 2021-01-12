package pl.patrykdolata.lifemanager.exceptions;

import org.springframework.http.HttpStatus;

public class EmailAlreadyExistsException extends WebException {

    public EmailAlreadyExistsException() {
        super("Email is already in use", HttpStatus.CONFLICT);
    }
}
