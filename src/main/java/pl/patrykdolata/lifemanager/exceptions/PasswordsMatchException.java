package pl.patrykdolata.lifemanager.exceptions;

import org.springframework.http.HttpStatus;

public class PasswordsMatchException extends WebException {

    public static final String MESSAGE = "Password and confirm password don't match";

    public PasswordsMatchException() {
        super(MESSAGE, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
