package pl.patrykdolata.lifemanager.util

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.isA
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import pl.patrykdolata.lifemanager.model.IdResponse
import pl.patrykdolata.lifemanager.util.JsonUtils.asJsonString
import pl.patrykdolata.lifemanager.util.JsonUtils.fromJson

class JsonUtilsTest {

    @Test
    fun `Mapping null object should be equal text 'null'`() {
        val obj: Any? = null
        val result = asJsonString(obj)
        assertThat(result, equalTo("null"))
    }


    @Test
    fun `Mapping proper object should gives proper String`() {
        val obj: Any = IdResponse(1L)
        val result = asJsonString(obj)
        assertNotNull(result)
        assertThat(result, equalTo("{\"id\":1}"))
    }

    @Test
    fun `Mapping from proper json string of given class should gives proper object`() {
        val json = "{\"id\":1}"
        val result: IdResponse<*>? = fromJson(json, IdResponse::class.java)
        assertNotNull(result)
        assertThat(result, isA(IdResponse::class.java))
        assertThat(result?.id, equalTo(1))
    }

    @Test
    fun `Mapping from invalid json string of given class should gives null`() {
        val json = "{\"name\":\"test\"}"
        val result: IdResponse<*>? = fromJson(json, IdResponse::class.java)
        assertNull(result)
    }
}
