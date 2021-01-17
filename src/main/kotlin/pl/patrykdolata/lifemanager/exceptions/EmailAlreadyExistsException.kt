package pl.patrykdolata.lifemanager.exceptions

import org.springframework.http.HttpStatus

class EmailAlreadyExistsException : WebException(MESSAGE, HttpStatus.CONFLICT) {

    companion object {
        const val MESSAGE: String = "Email is already in use"
    }
}
