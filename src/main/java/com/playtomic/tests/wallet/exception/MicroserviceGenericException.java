package com.playtomic.tests.wallet.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MicroserviceGenericException extends RuntimeException {

    private String code;
    private String message;
    private HttpStatus status;

    public MicroserviceGenericException(String code, String message, HttpStatus status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
