package com.dam.financialgame.services;

import android.app.Activity;

import com.dam.financialgame.controllers.Comunidad;

import java.util.Date;

// En esta interfaz se muestran los distintos servicios del objeto Service.
public interface PartidaService {

    // Obtenemos un listado de las partidas subidas al servidor.
    public void obtenerPartidasSubidas(Comunidad activity);

    // Enviamos al servicor el resultado de una partida.
    public void subirPartida(int numrondas, int numjugadores, String nombreganador, int puntuacionganador, Activity activity);

    // Metodo para borrar una partida pasándole su id.
    public void borrarPartida(int idPartida, Activity activity);
}
