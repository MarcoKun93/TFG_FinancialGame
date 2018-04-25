package com.dam.financialgame.model;

public class Usuario {
    private String nombre;
    private String correo;
    private String claveapi;

    public Usuario(String nombre, String correo, String claveapi) {
        this.nombre = nombre;
        this.correo = correo;
        this.claveapi = claveapi;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getClaveapi() {
        return claveapi;
    }

    public void setClaveapi(String claveapi) {
        this.claveapi = claveapi;
    }
}
