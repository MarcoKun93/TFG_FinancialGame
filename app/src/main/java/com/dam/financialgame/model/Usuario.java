package com.dam.financialgame.model;

public class Usuario {
    private String nombre;
    private String correo;
    private String claveapi;
    private String ultimaconexion;

    public Usuario(String nombre, String correo, String claveapi, String ultimaconexion) {
        this.nombre = nombre;
        this.correo = correo;
        this.claveapi = claveapi;
        this.ultimaconexion = ultimaconexion;
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

    public String getUltimaconexion() {
        return ultimaconexion;
    }

    public void setUltimaconexion(String ultimaconexion) {
        this.ultimaconexion = ultimaconexion;
    }
}
