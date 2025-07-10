package com.sise.pelixy.models;

import java.io.Serializable;

public class Comentario implements Serializable {
    private int id;
    private int usuario_id;
    private int pelicula_id;
    private String nombre;
    private String contenido;
    private String fecha_comentario;
    private String avatarUri;

    public Comentario() {
        // Constructor vacío requerido por Retrofit y Gson
    }

    // Getters

    public int getId() {
        return id;
    }

    public int getUsuarioId() {
        return usuario_id;
    }

    public int getPeliculaId() {
        return pelicula_id;
    }

    public String getNombreUsuario() {
        return nombre;
    }

    public String getComentario() {
        return contenido;
    }

    public String getFechaComentario() {
        return fecha_comentario;
    }

    public String getAvatarUri() {
        return avatarUri;
    }

    // Setters opcionales
    public void setComentario(String comentario) {
        this.contenido = comentario;
    }
}