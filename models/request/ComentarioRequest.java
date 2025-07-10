package com.sise.pelixy.models.request;

public class ComentarioRequest {
    private int usuario_id;
    private int pelicula_id;
    private String contenido;

    public ComentarioRequest(int usuario_id, int pelicula_id, String contenido) {
        this.usuario_id = usuario_id;
        this.pelicula_id = pelicula_id;
        this.contenido = contenido;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public int getPelicula_id() {
        return pelicula_id;
    }

    public String getContenido() {
        return contenido;
    }
}
