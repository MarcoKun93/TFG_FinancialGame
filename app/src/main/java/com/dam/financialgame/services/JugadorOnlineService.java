package com.dam.financialgame.services;

import android.app.Activity;

import com.dam.financialgame.controllers.activities.PartidaOnlineInicializada;
import com.dam.financialgame.controllers.activities.PrincipalPartidasOnline;

public interface JugadorOnlineService {

    // Metodo que llama al servicio de crear un jugadorOnline
    public void postJugadorOnline(int idPartidaOnline, PrincipalPartidasOnline activity);

    // Metodo que llama al servicio que devuelve los jugadores unidos a la partida
    public void getJugadoresUnidos(PartidaOnlineInicializada partidaOnlineInicializada);

    // Metodo del que obtengo los ids online del usuario necesarios para los update de recursos.
    public void getIdsOnline(final Activity activity);

    // Metodo que cambia el estado del jugador online por el indicado.
    public void modificaEstadoJugadorOnline(int idJugadorOnline, int estado, Activity activity);

    // Metodo que realiza la peticion de eliminar el jugador online
    public void eliminarJugadorOnline(Activity activity);

}


