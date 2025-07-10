package com.sise.pelixy.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Usuario implements Serializable {
    private int id;
    private String nombre;
    private String apellido;
    private String correo;
    private String password;

    @SerializedName("avatar_url")
    private String avatarUrl;

    @SerializedName("fecha_nacimiento")
    private String fechaNacimiento;

    private String pais;

    public Usuario(int id, String nombre, String apellido, String correo, String password,
                   String avatarUrl, String fechaNacimiento, String pais) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.password = password;
        this.avatarUrl = avatarUrl;
        this.fechaNacimiento = fechaNacimiento;
        this.pais = pais;
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getCorreo() { return correo; }
    public String getPassword() { return password; }
    public String getAvatarUrl() { return avatarUrl; }
    public String getFechaNacimiento() { return fechaNacimiento; }
    public String getPais() { return pais; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setApellido(String apellido) { this.apellido = apellido; }
    public void setCorreo(String correo) { this.correo = correo; }
    public void setPassword(String password) { this.password = password; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public void setFechaNacimiento(String fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public void setPais(String pais) { this.pais = pais; }
}