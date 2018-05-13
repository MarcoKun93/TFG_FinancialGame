package com.dam.financialgame.model;

public class Semilla {

    private int idSemilla;
    private String titulo;
    private String descripcion;
    private String efecto;
    private int valorbonos;
    private int valorpensiones;

    public Semilla(int idSemilla, String titulo, String descripcion, String efecto, int valorbonos, int valorpensiones) {
        this.idSemilla = idSemilla;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.efecto = efecto;
        this.valorbonos = valorbonos;
        this.valorpensiones = valorpensiones;
    }

    public int getIdSemilla() {
        return idSemilla;
    }

    public void setIdSemilla(int idSemilla) {
        this.idSemilla = idSemilla;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEfecto() {
        return efecto;
    }

    public void setEfecto(String efecto) {
        this.efecto = efecto;
    }

    public int getValorbonos() {
        return valorbonos;
    }

    public void setValorbonos(int valorbonos) {
        this.valorbonos = valorbonos;
    }

    public int getValorpensiones() {
        return valorpensiones;
    }

    public void setValorpensiones(int valorpensiones) {
        this.valorpensiones = valorpensiones;
    }

    @Override
    public String toString() {
        return " Título: " + titulo + "\n";
    }
}
