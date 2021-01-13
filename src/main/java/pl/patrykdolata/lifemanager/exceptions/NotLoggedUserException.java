package pl.patrykdolata.lifemanager.exceptions;

import org.springframework.http.HttpStatus;

public class NotLoggedUserException extends WebException {

    public static final String MESSAGE = "There is currently not logged user";

    public NotLoggedUserException() {
        super(MESSAGE, HttpStatus.UNAUTHORIZED);
    }
}
