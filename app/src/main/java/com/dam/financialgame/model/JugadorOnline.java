package com.dam.financialgame.model;


public class JugadorOnline {

    private int idJugadorOnline;
    private String nombreUsuario;
    private int puntuacion;
    private int numAccionesEuropeas;
    private int numAccionesAsiaticas;
    private int numAccionesAmericanas;
    private int numBonosDelEstado;
    private int numPlanDePensiones;
    private int liquidez;
    private int financiacion;
    private boolean isAnfitrion;
    private int estado;

    public int getIdJugadorOnline() {
        return idJugadorOnline;
    }

    public void setIdJugadorOnline(int idJugadorOnline) {
        this.idJugadorOnline = idJugadorOnline;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public int getNumAccionesEuropeas() {
        return numAccionesEuropeas;
    }

    public void setNumAccionesEuropeas(int numAccionesEuropeas) {
        this.numAccionesEuropeas = numAccionesEuropeas;
    }

    public int getNumAccionesAsiaticas() {
        return numAccionesAsiaticas;
    }

    public void setNumAccionesAsiaticas(int numAccionesAsiaticas) {
        this.numAccionesAsiaticas = numAccionesAsiaticas;
    }

    public int getNumAccionesAmericanas() {
        return numAccionesAmericanas;
    }

    public void setNumAccionesAmericanas(int numAccionesAmericanas) {
        this.numAccionesAmericanas = numAccionesAmericanas;
    }

    public int getNumBonosDelEstado() {
        return numBonosDelEstado;
    }

    public void setNumBonosDelEstado(int numBonosDelEstado) {
        this.numBonosDelEstado = numBonosDelEstado;
    }

    public int getNumPlanDePensiones() {
        return numPlanDePensiones;
    }

    public void setNumPlanDePensiones(int numPlanDePensiones) {
        this.numPlanDePensiones = numPlanDePensiones;
    }

    public int getLiquidez() {
        return liquidez;
    }

    public void setLiquidez(int liquidez) {
        this.liquidez = liquidez;
    }

    public int getFinanciacion() {
        return financiacion;
    }

    public void setFinanciacion(int financiacion) {
        this.financiacion = financiacion;
    }

    public boolean isAnfitrion() {
        return isAnfitrion;
    }

    public void setAnfitrion(boolean anfitrion) {
        isAnfitrion = anfitrion;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public JugadorOnline(int idJugadorOnline, String nombreUsuario, int puntuacion, int numAccionesEuropeas, int numAccionesAsiaticas, int numAccionesAmericanas,
                         int numBonosDelEstado, int numPlanDePensiones, int liquidez, int financiacion, boolean isAnfitrion, int estado) {
        this.idJugadorOnline = idJugadorOnline;
        this.nombreUsuario = nombreUsuario;
        this.puntuacion = puntuacion;
        this.numAccionesEuropeas = numAccionesEuropeas;
        this.numAccionesAsiaticas = numAccionesAsiaticas;
        this.numAccionesAmericanas = numAccionesAmericanas;
        this.numBonosDelEstado = numBonosDelEstado;
        this.numPlanDePensiones = numPlanDePensiones;
        this.liquidez = liquidez;
        this.financiacion = financiacion;
        this.isAnfitrion = isAnfitrion;
        this.estado = estado;
    }

    @Override
    public String toString() {
        return " "+getNombreUsuario()+"\n"+" Puntuación actual: "+Integer.toString(puntuacion)+"\n";
    }
}
