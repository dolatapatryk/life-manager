package pl.patrykdolata.lifemanager.util

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import pl.patrykdolata.lifemanager.model.IdResponse
import pl.patrykdolata.lifemanager.util.ResponseUtils.ok
import pl.patrykdolata.lifemanager.util.ResponseUtils.response

class ResponseUtilsTest {

    @Test
    fun `Method ok should return null body and status 200`() {
        val result = ok()
        assertThat(result.statusCode, equalTo(HttpStatus.OK))
        assertThat(result.headers, anEmptyMap())
        assertNull(result.body)
    }

    @Test
    fun `Method ok with body and headers should return not null body, status 200 and proper headers`() {
        val headers = HttpHeaders()
        headers.add("test_header", "test")
        val result = ok(IdResponse(1L), headers)
        assertThat(result.statusCode, equalTo(HttpStatus.OK))
        assertNotNull(result.body)
        assertThat(result.body, isA(IdResponse::class.java))
        assertThat(result.headers, not(anEmptyMap()))
        assertThat(result.headers, hasKey("test_header"))
    }

    @Test
    fun `Method ok with body should return not null body, status 200 and empty headers`() {
        val result = ok(IdResponse(1L))
        assertThat(result.statusCode, equalTo(HttpStatus.OK))
        assertNotNull(result.body)
        assertThat(result.body, isA(IdResponse::class.java))
        assertThat(result.headers, anEmptyMap())
    }

    @Test
    fun `Method response should return proper body and proper status`() {
        val result = response(IdResponse(1L), HttpStatus.BAD_GATEWAY)
        assertThat(result.statusCode, equalTo(HttpStatus.BAD_GATEWAY))
        assertNotNull(result.body)
        assertThat(result.body, isA(IdResponse::class.java))
        assertThat(result.headers, anEmptyMap())
    }
}
