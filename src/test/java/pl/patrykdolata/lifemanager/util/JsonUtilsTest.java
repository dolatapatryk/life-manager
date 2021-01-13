package pl.patrykdolata.lifemanager.util;

import org.junit.jupiter.api.Test;
import pl.patrykdolata.lifemanager.model.IdResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class JsonUtilsTest {

    private static class ClassThatJacksonCannotSerialize {}

    @Test
    public void asJsonString_whenObjNull_expectNullText() {
        Object obj = null;
        String result = JsonUtils.asJsonString(obj);

        assertThat(result, is("null"));
    }

    @Test
    public void asJsonString_whenInvalidObject_expectNull() {
        Object obj = new ClassThatJacksonCannotSerialize();
        String result = JsonUtils.asJsonString(obj);

        assertNull(result);
    }

    @Test
    public void asJsonString_whenProperObject_expectProperString() {
        Object obj = new IdResponse(1L);
        String result = JsonUtils.asJsonString(obj);

        assertNotNull(result);
        assertThat(result, is("{\"id\":1}"));
    }

}
