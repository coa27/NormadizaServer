package com.coa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NoEncontradoException extends RuntimeException{

    private static final long serialVersionUID = 1884325231434L;

    public NoEncontradoException(String nombre){
        super(nombre);
    }



}
