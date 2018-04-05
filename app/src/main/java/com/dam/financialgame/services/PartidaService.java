package com.dam.financialgame.services;

import com.dam.financialgame.controllers.Comunidad;

// En esta interfaz se muestran los distintos servicios del objeto Service.
public interface PartidaService {
    public void obtenerPartidasSubidas(Comunidad activity);   // DE EJEMPLO: Obtenemos un listado de las partidas subidas al servidor.
    public void subirPartidas(final String puntuacion, final String nombre); // DE EJEMPLO: Enviamos al servicor el resultado de una partida.
}
