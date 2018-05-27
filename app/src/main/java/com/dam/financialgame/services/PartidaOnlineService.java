package com.dam.financialgame.services;

import android.app.Activity;

import com.dam.financialgame.controllers.PrincipalPartidasOnline;

public interface PartidaOnlineService {
    // Metodo que obtengo las partidas online disponibles
    public void getPartidasOnline(PrincipalPartidasOnline activity);

    // Metodo que crea una partida online
    public void crearPartidaOnline(int numrondas, int numjugadoresmax, PrincipalPartidasOnline activity);

    // Metodo que elimina la partida online en la que pertenece el jugador online
    public void eliminarPartidaOnline(Activity activity);

    // Metodo que manda peticion para cambiar el estadod e la partida por el indicado
    public void setEstadoPartidaOnline(String nuevoEstado, Activity activity);

    // Metodo que manda la peticion de cambiar el numero de jugadores unidos a la partida
    public void setNumeroJugadores(int numJugadores, Activity activity);
}
