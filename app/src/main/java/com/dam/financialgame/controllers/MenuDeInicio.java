package com.dam.financialgame.controllers;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.dam.financialgame.R;

public class MenuDeInicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_de_inicio);
    }

    // Se arrancará la actividad de iniciar àrtida normal, como siempre.
    public void irIniciarPartida(View view) {
        // Start the next activity
        Intent mainIntent = new Intent().setClass(view.getContext(), MainActivity.class);
        startActivity(mainIntent);
    }

    // El método iniciará una actividad que arrancará todas las funcionalidades online del juego.
    public void irComunidad(View view) {
        // Start the next activity
        Intent mainIntent = new Intent().setClass(view.getContext(), Comunidad.class);
        startActivity(mainIntent);
    }

    // Las instrucciones serán mostradas en un fragment, el método NO arrancará un activity.
    public void irInstrucciones(View view) {

    }

    // Se realiza en un fragment, el método NO arrancará un activity. Se llamará a un Servicio.
    public void irIniciarSesion(View view) {

    }
}
