package com.playtomic.tests.wallet.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final HttpStatus status;
    private final String message;
    private final String code;
    private final LocalDateTime timestamp;

    public ErrorResponse(final HttpStatus status, final String message, final String code) {
        this.status = status;
        this.message = message;
        this.code = code;
        this.timestamp = LocalDateTime.now();
    }

}
