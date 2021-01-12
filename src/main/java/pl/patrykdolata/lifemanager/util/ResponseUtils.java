package pl.patrykdolata.lifemanager.util;

import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@UtilityClass
public class ResponseUtils {

    public ResponseEntity<Void> ok() {
        return ResponseEntity.ok().build();
    }

    public <T> ResponseEntity<T> ok(T body, HttpHeaders headers) {
        return new ResponseEntity<>(body, headers, HttpStatus.OK);
    }

    public <T> ResponseEntity<T> ok(T body) {
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    public <T> ResponseEntity<T> response(T body, HttpStatus status) {
        return new ResponseEntity<>(body, status);
    }
}
