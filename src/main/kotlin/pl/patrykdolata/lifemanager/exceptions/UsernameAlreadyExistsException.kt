package pl.patrykdolata.lifemanager.exceptions

import org.springframework.http.HttpStatus

class UsernameAlreadyExistsException : WebException(MESSAGE, HttpStatus.CONFLICT) {

    companion object {
        const val MESSAGE: String = "Username is already in use"
    }
}
