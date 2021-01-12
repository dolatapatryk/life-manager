package pl.patrykdolata.lifemanager.exceptions;

import org.springframework.http.HttpStatus;

public class PasswordsMatchException extends WebException {

    public PasswordsMatchException() {
        super("Password and confirm password don't match", HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
