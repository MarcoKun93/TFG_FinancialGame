package com.dam.financialgame.model;

public class Logro {
    private int idLogro;
    private String nombre;
    private String descripcion;
    private String tipo;
    private String variableafectada;
    private int valor;

    public Logro(int idLogro, String nombre, String descripcion, String tipo, String variableafectada, int valor) {
        this.idLogro = idLogro;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.variableafectada = variableafectada;
        this.valor = valor;
    }

    public int getIdLogro() {
        return idLogro;
    }

    public void setIdLogro(int idLogro) {
        this.idLogro = idLogro;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getVariableafectada() {
        return variableafectada;
    }

    public void setVariableafectada(String variableafectada) {
        this.variableafectada = variableafectada;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    @Override
    public String toString() {
        return "Nombre: "+nombre+ "\n" + "Tipo: "+tipo;
    }

}
