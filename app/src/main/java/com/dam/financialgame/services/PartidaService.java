package com.dam.financialgame.services;

import android.app.Activity;

import com.dam.financialgame.controllers.Comunidad;

// En esta interfaz se muestran los distintos servicios del objeto Service.
public interface PartidaService {
    public void obtenerPartidasSubidas(Comunidad activity);   // DE EJEMPLO: Obtenemos un listado de las partidas subidas al servidor.
    public void subirPartidas(final String puntuacion, final String nombre, final Activity activity); // DE EJEMPLO: Enviamos al servicor el resultado de una partida.
}
