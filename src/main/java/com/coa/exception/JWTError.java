package com.coa.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class JWTError extends RuntimeException{

    private static final long serialVersionUID = 43823452253245L;

    public JWTError(String mensaje){
        super(mensaje);
    }
}
