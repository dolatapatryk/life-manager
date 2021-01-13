package pl.patrykdolata.lifemanager.model;

import lombok.Value;

@Value
public class ErrorResponse {
    String message;
    int status;
    long timestamp;
    String path;
}
