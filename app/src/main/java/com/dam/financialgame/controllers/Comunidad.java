package com.dam.financialgame.controllers;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import com.dam.financialgame.R;
import com.dam.financialgame.servicesImpl.PartidaServiceImpl;

public class Comunidad extends AppCompatActivity {

    TextView listaPartidas;
    EditText nombre;
    EditText puntuacion;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunidad);

        listaPartidas = (TextView) findViewById(R.id.listaPartidas);
        nombre = (EditText) findViewById(R.id.nombreJugador);
        puntuacion = (EditText) findViewById(R.id.puntuacion);

        // DE PRUEBA: Llamamos al servicio que muestra todas las partidas subidas en el servidor.
        PartidaServiceImpl.getInstance().obtenerPartidasSubidas(this);
    }

    // Método llamado desde un servicio para actualizar el textView listaPartidas.
    public void setListaPartidas(String listaActualizada) {
        listaPartidas.setText(listaActualizada);
    }

    // Método llamado desde un botón para enviar al servidor info partida al servidor.
    public void postPartida(View view) {
        PartidaServiceImpl.getInstance().subirPartidas(puntuacion.getText().toString(), nombre.getText().toString(), this);
    }

    // Método llamado desde un botón para recargar el ranking de la vista con la información almacenada en el servidor.
    public void recargaRanking(View view) {
        PartidaServiceImpl.getInstance().obtenerPartidasSubidas(this);
    }
}
