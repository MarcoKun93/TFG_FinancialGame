package com.dam.financialgame;

import java.util.Vector;

public interface almacenJuego {
    //public void crearMazo();  //De momento lo hago nada mas crearse la base de datos
    public void setInfoJugador(String nombre, int rondaActual, int variable1, int variable2, int variable3, int variable4, int variable5, int variable6);
    public Vector<String> getInfoJugadores(); // Obtengo un vector de los jugadores y su puntuacion, ordenados descendente
    public Vector<Integer> getPuntuacionAtributosJugador (String nombreJugador);    // Obtengo un vector con el valor de los atributos de la puntuación final, en orden
    public Vector<Vector<Integer>> getHistorialJugador(String nombreJugador);   // Obtenemos el historial de un jugador indicado
    public Vector<String> getJugadores();   // Obtengo un vector unicamente de los nombre de los jugadores
    public void vaciarTablaJugadores();   // Cuando termina la partida vaciamos la tabala jugadores, dejándolas listas para una partida nueva
    public Vector<String> obtenerCartaDelMazo(int indice);   // Obtengo una carta del mazo, recibe el indice de la carta deseada
    public String getJugadorGanador();  // Obtengo el nombre del jugador ganador
    public Vector<Integer> getIndicesCartas();  // Obtengo el indice de las cartas del mazo barajado
}
