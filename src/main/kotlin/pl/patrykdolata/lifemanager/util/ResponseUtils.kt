package pl.patrykdolata.lifemanager.util

import org.springframework.data.domain.Page
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import pl.patrykdolata.lifemanager.model.IdResponse

object ResponseUtils {

    private const val X_PAGE_COUNT = "X-Page-Count"
    private const val X_TOTAL_COUNT = "X-Total-Count"
    private const val X_TOTAL_PAGES = "X-Total-Pages"

    fun ok(): ResponseEntity<Void> {
        return ResponseEntity.ok().build()
    }

    fun <T> ok(body: T, headers: HttpHeaders = HttpHeaders.EMPTY)
            : ResponseEntity<T> {
        return response(body, HttpStatus.OK, headers)
    }

    fun <T> createdResponse(idResponse: IdResponse<T>): ResponseEntity<IdResponse<T>> {
        return response(idResponse, HttpStatus.CREATED)
    }

    fun <T> pageResponse(body: Page<T>): ResponseEntity<List<T>> {
        return response(body.content, headers = getPageHeaders(body))
    }

    fun <T> response(body: T, status: HttpStatus = HttpStatus.OK, headers: HttpHeaders = HttpHeaders.EMPTY)
            : ResponseEntity<T> {
        return ResponseEntity(body, headers, status)
    }

    private fun <T> getPageHeaders(page: Page<T>): HttpHeaders {
        val headers = HttpHeaders()
        with(headers) {
            add(X_PAGE_COUNT, page.numberOfElements.toString())
            add(X_TOTAL_COUNT, page.totalElements.toString())
            add(X_TOTAL_PAGES, page.totalPages.toString())
        }
        return headers
    }
}
