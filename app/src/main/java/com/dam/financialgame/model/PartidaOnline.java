package com.dam.financialgame.model;

public class PartidaOnline extends Partida {

    private int idEvento;
    private int temporizador;
    private int estado;
    private int numjugadoresmax;
    private String nombreAnfitrion;

    // Como nuestro sistema, la PartidaOnline no hace uso de idUsuario y idSemilla, las instanciamos siempre a 0.
    public PartidaOnline(int idPartida, String nombreAnfitrion, String fecha, int numrondas, int numjugadores, int numjugadoresmax, String nombreganador,
                         int puntuacionganador, int idEvento, int temporizador, int estado) {
        super(idPartida, fecha, numrondas, numjugadores, nombreganador, puntuacionganador, 0, 0);
        this.idEvento = idEvento;
        this.temporizador = temporizador;
        this.estado = estado;
        this.nombreAnfitrion = nombreAnfitrion;
        this.numjugadoresmax = numjugadoresmax;
    }

    public int getNumjugadoresmax() {
        return numjugadoresmax;
    }

    public void setNumjugadoresmax(int numjugadoresmax) {
        this.numjugadoresmax = numjugadoresmax;
    }

    public int getIdEvento() {
        return idEvento;
    }

    public void setIdEvento(int idEvento) {
        this.idEvento = idEvento;
    }

    public String getNombreAnfitrion() {
        return nombreAnfitrion;
    }

    public void setNombreAnfitrion(String nombreAnfitrion) {
        this.nombreAnfitrion = nombreAnfitrion;
    }

    public int getTemporizador() {
        return temporizador;
    }

    public void setTemporizador(int temporizador) {
        this.temporizador = temporizador;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Anfitrion: " +getNombreAnfitrion()+ "\n"+
                "Rondas: " +Integer.toString(getNumrondas())+ "\n"+
                "Jugadores: " +Integer.toString(getNumjugadores())+ "  Jugadores Max: " +Integer.toString(getNumjugadoresmax()) +"\n";
    }
}
