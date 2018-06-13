package com.dam.financialgame.services;

import android.app.Activity;

import com.dam.financialgame.controllers.activities.Comunidad;

// En esta interfaz se muestran los distintos servicios de la clase Logro.
public interface LogroService {

    // Metodo que obtiene los logros de un usuario. GET
    public void obtenerLogros(Comunidad activity);

    // Metodo que comprueba si cumple el logro TuPrimeraPartida. POST
    public void compruebaLogroPrimeraPartida(Activity activity);

    // Metodo que comprueba si cumple el logro Millonario. POST
    public void compruebaLogroMillonario(int puntuacionganador, Activity activity);

    // Metodo que comprueba si cumple el logro PartidaLarga. POST
    public void compruebaLogroPartidaLarga(int numrondas, Activity activity);

}
