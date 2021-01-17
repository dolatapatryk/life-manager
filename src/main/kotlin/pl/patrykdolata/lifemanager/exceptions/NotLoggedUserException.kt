package pl.patrykdolata.lifemanager.exceptions

import org.springframework.http.HttpStatus

class NotLoggedUserException : WebException(MESSAGE, HttpStatus.UNAUTHORIZED) {

    companion object {
        const val MESSAGE = "There is currently not logged user"
    }
}
