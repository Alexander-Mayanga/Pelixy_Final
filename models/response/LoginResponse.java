package com.sise.pelixy.models.response;

import com.sise.pelixy.models.Usuario;

public class LoginResponse {
    private Usuario usuario;
    private String token;

    public Usuario getUsuario() {
        return usuario;
    }

    public String getToken() {
        return token;
    }
}