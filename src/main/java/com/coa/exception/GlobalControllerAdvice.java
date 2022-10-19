package com.coa.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ModelException modelException = new ModelException(status, LocalDateTime.now(), ex.getMessage());

        return ResponseEntity.status(status).headers(headers).body(modelException);
    }


    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<Object> handleNoEncontreado(Exception ex) {
        ModelException modelException = new ModelException(HttpStatus.NOT_FOUND, LocalDateTime.now(), "No encontrado");

        return ResponseEntity.status(modelException.getHttpStatus()).body(modelException);
    }



}
