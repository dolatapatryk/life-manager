package pl.patrykdolata.lifemanager.exceptions

import org.springframework.http.HttpStatus

class PasswordMatchException : WebException(MESSAGE, HttpStatus.UNPROCESSABLE_ENTITY) {

    companion object {
        const val MESSAGE = "Password and confirm password don't match"
    }
}
