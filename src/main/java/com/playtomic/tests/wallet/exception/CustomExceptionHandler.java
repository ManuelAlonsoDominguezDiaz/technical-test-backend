package com.playtomic.tests.wallet.exception;

import com.playtomic.tests.wallet.exception.payment.PaymentException;
import com.playtomic.tests.wallet.exception.wallet.WalletNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomExceptionHandler {

    private Logger log = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<ErrorResponse> paymentExceptionHandler(PaymentException ex) {

        log.error(ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(ex.getStatus(), ex.getMessage(),
                ex.getCode());

        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(WalletNotFoundException.class)
    public ResponseEntity<ErrorResponse> walletNotFoundExceptionHandler(WalletNotFoundException ex) {

        log.error(ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(ex.getStatus(), ex.getMessage(),
                ex.getCode());

        return new ResponseEntity<>(errorResponse, ex.getStatus());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> genericRuntimeException(RuntimeException ex) {

        log.error(ex.getMessage());

        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                ErrorCode.INTERNAL_ERROR.getMessage(),
                ErrorCode.INTERNAL_ERROR.getCode());

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
