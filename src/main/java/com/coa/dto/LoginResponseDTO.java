package com.coa.dto;

import com.coa.model.Usuario;

public class LoginResponseDTO {

    private Usuario usuario;
    private String token;

    public LoginResponseDTO(Usuario usuario, String token) {
        this.usuario = usuario;
        this.token = token;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
