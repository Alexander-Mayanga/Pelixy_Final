package com.sise.pelixy.models;

import java.io.Serializable;

public class Favorito implements Serializable {
    private int id;
    private int usuario_id;
    private String nombre_usuario;
    private int pelicula_id;
    private String titulo_pelicula;
    private String url_portada;
    private String fecha_guardado;

    // Getters y Setters (puedes generarlos automáticamente en Android Studio)
    public int getId() {
        return id;
    }

    public int getUsuario_id() {
        return usuario_id;
    }

    public String getNombre_usuario() {
        return nombre_usuario;
    }

    public int getPelicula_id() {
        return pelicula_id;
    }

    public String getTitulo_pelicula() {
        return titulo_pelicula;
    }

    public String getUrl_portada() {
        return url_portada;
    }

    public String getFecha_guardado() {
        return fecha_guardado;
    }
}