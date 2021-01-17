package pl.patrykdolata.lifemanager.exceptions

import org.springframework.http.HttpStatus
import java.lang.RuntimeException

open class WebException(override val message: String, val statusCode: HttpStatus) : RuntimeException()
