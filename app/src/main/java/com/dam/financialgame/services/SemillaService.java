package com.dam.financialgame.services;

import com.dam.financialgame.controllers.activities.Comunidad;
import com.dam.financialgame.controllers.activities.IntroducirDatos;

public interface SemillaService {

    // Metodo que devuelve el listado de semillas almacenados en el servidor.
    public void obtenerSemillas(Comunidad activity);

    // Sobreescribimos el metodo para permitir enviar el idSemilla y obtener solo la que interesa.
    public void obtenerSemillas(Comunidad activity, int idSemilla);

    // Metodo que nos sirve para obtener la semilla desde la actividad IntroducirDatos.
    public void obtenerSemilla(IntroducirDatos activity, int idSemilla);
}
