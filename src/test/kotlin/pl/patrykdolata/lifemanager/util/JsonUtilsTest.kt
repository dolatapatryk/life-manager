package pl.patrykdolata.lifemanager.util

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import pl.patrykdolata.lifemanager.model.IdResponse
import pl.patrykdolata.lifemanager.util.JsonUtils.asJsonString

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
}
