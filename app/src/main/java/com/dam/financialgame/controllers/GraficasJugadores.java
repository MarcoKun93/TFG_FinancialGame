package com.dam.financialgame.controllers;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;

import com.dam.financialgame.R;
import com.dam.financialgame.servicesImpl.AlmacenJuegoImpl;
import com.dam.financialgame.servicesImpl.PartidaServiceImpl;

// Esta clase administrará los fragments correspondientes a las distintas gráficas que esterán embebidos en esta clase
public class GraficasJugadores extends AppCompatActivity {

    public static int numRondasFinJuego = 0;
    Button botonSalir;
    TabHost graficasTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficas_jugadores);
        botonSalir = (Button) findViewById(R.id.salirDeLasGraficas);
        graficasTabHost = (TabHost) findViewById(R.id.graficasTabHost);

        // Obtenemos el número de rondas totales
        numRondasFinJuego = getIntent().getIntExtra("numRondasFinJuego", numRondasFinJuego);
        Log.i("GraficasJugadores", "El numero de rondas totales es de " + numRondasFinJuego);

        /***
        // Definimos un manejador de fragmentos para permitir la comunicación entre éstos y la actividad contenedora
        android.app.FragmentManager fragmentManager = getFragmentManager();
        // Buscamos el fragmento con identificador deseado, por si hace falta para comunicarse con el en el futuro
        LineGraph lineGraph = (LineGraph) fragmentManager.findFragmentById(R.id.lineGraphFragment);
         ***/

        // Activamos el TabHost de las graficas
        graficasTabHost.setup();

        TabHost.TabSpec tab1 = graficasTabHost.newTabSpec("tab1");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec tab2 = graficasTabHost.newTabSpec("tab2");

        tab1.setIndicator("PROGRESO");    //qué queremos que aparezca en las pestañas
        tab1.setContent(R.id.lineTab); //definimos el id de cada Tab (pestaña)

        tab2.setIndicator("PORCENTAJES");
        tab2.setContent(R.id.pieTab);

        graficasTabHost.addTab(tab1); //añadimos los tabs ya programados
        graficasTabHost.addTab(tab2);
    }

    // Para obtener el numero de rondas totales de la partida desde los fragments
    public static int obtenerNumRondasFinJuego () {
        return numRondasFinJuego;
    }

    // Método llamado desde un botón para enviar al servidor info partida al servidor, en este caso información del jugador ganador.
    public void postPartidaDesdeGraficas(View view) {
        AlmacenJuegoImpl almacenJuego = new AlmacenJuegoImpl(this);
        PartidaServiceImpl.getInstance().subirPartidas(Integer.toString(almacenJuego.getPuntuacionJugadorGanador()), almacenJuego.getNombreJugadorGanador(), this);
    }

    public void salirDeLasGraficas (View view) {
        finish();
    }
}
