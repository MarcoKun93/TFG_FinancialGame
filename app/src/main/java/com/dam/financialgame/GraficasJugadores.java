package com.dam.financialgame;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.dam.financialgame.R;

// Esta clase administrará los fragments correspondientes a las distintas gráficas que esterán embebidos en esta clase
public class GraficasJugadores extends AppCompatActivity {

    public static int numRondasFinJuego = 0;
    Button botonSalir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graficas_jugadores);
        botonSalir = (Button) findViewById(R.id.salirDeLasGraficas);


        // Obtenemos el número de rondas totales
        numRondasFinJuego = getIntent().getIntExtra("numRondasFinJuego", numRondasFinJuego);
        Log.i("GraficasJugadores", "El numero de rondas totales es de " + numRondasFinJuego);

        // Definimos un manejador de fragmentos para permitir la comunicación entre éstos y la actividad contenedora
        android.app.FragmentManager fragmentManager = getFragmentManager();

        // Buscamos el fragmento con identificador deseado, por si hace falta para comunicarse con el en el futuro
        LineGraph lineGraph = (LineGraph) fragmentManager.findFragmentById(R.id.lineGraphFragment);
    }

    // Para obtener el numero de rondas totales de la partida desde los fragments
    public static int obtenerNumRondasFinJuego () {
        return numRondasFinJuego;
    }

    public void salirDeLasGraficas (View view) {
        finish();
    }
}
