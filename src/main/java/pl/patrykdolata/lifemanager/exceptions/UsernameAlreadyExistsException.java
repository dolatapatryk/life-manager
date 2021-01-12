package pl.patrykdolata.lifemanager.exceptions;

import org.springframework.http.HttpStatus;

public class UsernameAlreadyExistsException extends WebException {

    public UsernameAlreadyExistsException() {
        super("Username is already in use", HttpStatus.CONFLICT);
    }
}
