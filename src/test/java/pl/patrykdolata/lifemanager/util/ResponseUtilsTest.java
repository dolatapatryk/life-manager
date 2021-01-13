package pl.patrykdolata.lifemanager.util;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.patrykdolata.lifemanager.model.IdResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

class ResponseUtilsTest {

    @Test
    void ok_test() {
        ResponseEntity<Void> result = ResponseUtils.ok();
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertNull(result.getBody());
        assertThat(result.getHeaders(), is(anEmptyMap()));
    }

    @Test
    void ok_withBodyAndHeaders_test() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("test_header", "test");
        ResponseEntity<IdResponse> result = ResponseUtils.ok(new IdResponse(1L), headers);

        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertNotNull(result.getBody());
        assertThat(result.getBody(), isA(IdResponse.class));
        assertThat(result.getHeaders(), is(not(anEmptyMap())));
        assertThat(result.getHeaders(), hasKey("test_header"));
    }

    @Test
    void ok_withBody_test() {
        ResponseEntity<IdResponse> result = ResponseUtils.ok(new IdResponse(1L));
        assertThat(result.getStatusCode(), is(HttpStatus.OK));
        assertNotNull(result.getBody());
        assertThat(result.getBody(), isA(IdResponse.class));
        assertThat(result.getHeaders(), is(anEmptyMap()));
    }

    @Test
    void response_test() {
        ResponseEntity<IdResponse> result = ResponseUtils.response(new IdResponse(1L), HttpStatus.BAD_GATEWAY);
        assertThat(result.getStatusCode(), is(HttpStatus.BAD_GATEWAY));
        assertNotNull(result.getBody());
        assertThat(result.getBody(), isA(IdResponse.class));
        assertThat(result.getHeaders(), is(anEmptyMap()));
    }
}
