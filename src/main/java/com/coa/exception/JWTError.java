package com.coa.exception;

public class JWTError extends RuntimeException{

    private static final long serialVersionUID = 43823452253245L;

    public JWTError(String mensaje){
        super(mensaje);
    }
}
