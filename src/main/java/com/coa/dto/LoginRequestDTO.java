package com.coa.dto;

public class LoginRequestDTO {

    private String emailOrUsuario;
    private String password;

    public String getEmailOrUsuario() {
        return emailOrUsuario;
    }

    public void setEmailOrUsuario(String emailOrUsuario) {
        this.emailOrUsuario = emailOrUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
