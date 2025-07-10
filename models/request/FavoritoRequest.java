package com.sise.pelixy.models.request;

public class FavoritoRequest {
    private int usuario_id;
    private int pelicula_id;

    public FavoritoRequest(int usuario_id, int pelicula_id) {
        this.usuario_id = usuario_id;
        this.pelicula_id = pelicula_id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public int getPelicula_id() {
        return pelicula_id;
    }
}
