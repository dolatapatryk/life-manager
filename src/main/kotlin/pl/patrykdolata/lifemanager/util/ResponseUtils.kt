package pl.patrykdolata.lifemanager.util

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

object ResponseUtils {
    fun ok(): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }

    fun <T> ok(body: T, headers: HttpHeaders): ResponseEntity<T> {
        return ResponseEntity(body, headers, HttpStatus.OK)
    }

    fun <T> ok(body: T): ResponseEntity<T> {
        return ResponseEntity(body, HttpStatus.OK)
    }

    fun <T> response(body: T, status: HttpStatus): ResponseEntity<T> {
        return ResponseEntity(body, status)
    }
}
