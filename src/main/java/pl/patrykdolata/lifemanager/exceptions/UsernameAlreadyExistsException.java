package pl.patrykdolata.lifemanager.exceptions;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyExistsException extends WebException {

    public static final String MESSAGE = "Password and confirm password don't match";

    public UsernameAlreadyExistsException() {
        super(MESSAGE, HttpStatus.CONFLICT);
    }
}
