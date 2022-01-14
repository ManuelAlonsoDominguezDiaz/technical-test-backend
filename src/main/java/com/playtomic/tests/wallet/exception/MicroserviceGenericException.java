package com.playtomic.tests.wallet.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MicroserviceGenericException extends RuntimeException {

    private final String code;
    private final String message;
    private final HttpStatus status;

    public MicroserviceGenericException(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
