package com.sise.pelixy.models;

import java.io.Serializable;

public class Pelicula implements Serializable {

    private int id;
    private String titulo;
    private String descripcion;
    private int anio;
    private String duracion;
    private String director;
    private String clasificacion;
    private String idioma;
    private String url_portada;
    private float calificacion;

    public Pelicula() {
        // Constructor vacío requerido por Retrofit y Gson
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getAnio() {
        return anio;
    }

    public String getDuracion() {
        return duracion;
    }

    public String getDirector() {
        return director;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public String getIdioma() {
        return idioma;
    }

    public String getUrlPortada() {
        return url_portada;
    }

    public float getCalificacion() {
        return calificacion;
    }

    public boolean tieneImagen() {
        return url_portada != null && !url_portada.isEmpty();
    }

    public void setId(int id) { this.id = id; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public void setUrlPortada(String urlPortada) { this.url_portada = urlPortada; }

    public String[] obtenerGeneros() {
        return clasificacion != null ? clasificacion.split(",") : new String[0];
    }
}