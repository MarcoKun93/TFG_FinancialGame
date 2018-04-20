package com.dam.financialgame.model;

// Los atributos y propiedades son temporales, es una prueba.
public class Partida {
    private int puntuacionWin;
    private String nombreWin;
    private String fechaPartida;

    public int getPuntuacionWin() {
        return puntuacionWin;
    }

    public void setPuntuacionWin(int puntuacionWin) {
        this.puntuacionWin = puntuacionWin;
    }

    public void setNombreWin(String nombreWin) {
        this.nombreWin = nombreWin;
    }

    public String getNombreWin() {
        return nombreWin;
    }

    public void setFechaPartida(String fechaPartida) {
        this.fechaPartida = fechaPartida;
    }

    public String getFechaPartida() {
        return fechaPartida;
    }

}
