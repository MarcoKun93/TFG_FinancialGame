package com.dam.financialgame.model;

import java.util.Date;

// Los atributos y propiedades son temporales, es una prueba.
public class Partida {
    private int idPartida;
    private String fecha;
    private int numrondas;
    private int numjugadores;

    public Partida(int idPartida, String fecha, int numrondas, int numjugadores, String nombreganador, int puntuacionganador, int idUsuario) {
        this.idPartida = idPartida;
        this.fecha = fecha;
        this.numrondas = numrondas;
        this.numjugadores = numjugadores;
        this.nombreganador = nombreganador;
        this.puntuacionganador = puntuacionganador;
        this.idUsuario = idUsuario;
    }

    private String nombreganador;
    private int puntuacionganador;
    private int idUsuario;

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public int getNumrondas() {
        return numrondas;
    }

    public void setNumrondas(int numrondas) {
        this.numrondas = numrondas;
    }

    public int getNumjugadores() {
        return numjugadores;
    }

    public void setNumjugadores(int numjugadores) {
        this.numjugadores = numjugadores;
    }

    public String getNombreganador() {
        return nombreganador;
    }

    public void setNombreganador(String nombreganador) {
        this.nombreganador = nombreganador;
    }

    public int getPuntuacionganador() {
        return puntuacionganador;
    }

    public void setPuntuacionganador(int puntuacionganador) {
        this.puntuacionganador = puntuacionganador;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public String toString() {
        return "Partida{" +
                "idPartida=" + idPartida +
                ", fecha='" + fecha + '\'' +
                ", numrondas=" + numrondas +
                ", numjugadores=" + numjugadores +
                ", nombreganador='" + nombreganador + '\'' +
                ", puntuacionganador=" + puntuacionganador +
                ", idUsuario=" + idUsuario +
                '}';
    }
}
