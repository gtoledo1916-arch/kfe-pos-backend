package org.kfe.api.demo.exception;

import java.time.LocalDateTime;

public class Apierror {

    private int status;
    private String message;
    private LocalDateTime timestamp;

    public Apierror(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    public int getStatus() { return status; }
    public String getMessage() { return message; }
    public LocalDateTime getTimestamp() { return timestamp; }
}
