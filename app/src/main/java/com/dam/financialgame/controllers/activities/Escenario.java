package com.dam.financialgame.controllers.activities;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.dam.financialgame.R;
import com.dam.financialgame.controllers.fragments.Evento;
import com.dam.financialgame.controllers.fragments.FinDelJuegoFragment;
import com.dam.financialgame.servicesImpl.AlmacenJuegoImpl;
import com.dam.financialgame.threads.TemporizadorThread;

import java.util.Collections;
import java.util.Vector;

public class Escenario extends AppCompatActivity {

    TextView numRondas;
    ImageButton botonIniciar;
    int segundosIntervalo = 0;
    ListView ranking;
    private static final String TAG = Escenario.class.getSimpleName();  // Para depurar
    ArrayAdapter<String> adaptador; // Para guardar los jugadores y puntuaciones
    AlmacenJuegoImpl almacen = AlmacenJuegoImpl.getInstance(this);
    int eventos = 0;

    // Definimos una variable long donde guarda los milisegundos del temporizador
    long segundos;

    //variables de control
    boolean noSegundo = true;
    boolean noTercero = true;
    boolean finJuego = false;

    // Numero de rondas actuales para saber cuando se acaba el juego
    int numRondasActuales = 0;
    // Numero de rondas del fin del juego
    int numRondasFinJuego = 0;
    // Numero de eventos que tiene una ronda
    public final int EVENTOS_POR_RONDA = 2;

    // Vector de indices de cartas procedentes del mazo
    Vector<Integer> indicesCartasDelMazo = new Vector<Integer>();
    // Cursor que apunta a la posicion de los elementos del vector de indice de cartas que obtenemos del mazo
    int posicionActualDelMazo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escenario);
        botonIniciar = (ImageButton) findViewById(R.id.botonIniciar);
        ranking = (ListView) findViewById(R.id.rankingJugadores);
        numRondas = (TextView) findViewById(R.id.numRondas);

        // Recuperamos el numero de rondas actuales, la posicion del mazo y el numero de rondas totales de la partida.
        // Si no hemos salvado estado de la actividad ó venimos de la actividad padre, no hacemos nada y se inician a cero
        if (savedInstanceState != null) {
            numRondasActuales = savedInstanceState.getInt("numRondasActuales");
            posicionActualDelMazo = savedInstanceState.getInt("posicionActualDelMazo");
            numRondasFinJuego = savedInstanceState.getInt("numRondasFinJuego");
        }

        // Obtenemos las rondas del final del juego
        numRondasFinJuego = getIntent().getIntExtra("numRondas", numRondasFinJuego);
        numRondas.setText("Número de ronda: "+Integer.toString(numRondasActuales+1)+"/"+Integer.toString(numRondasFinJuego));

        // Obtenemos el mazo barajado de cartas
        indicesCartasDelMazo = almacen.getIndicesCartas();

        // Actualizamos el ranking de jugadores
        actualizarRanking();
    }

    public void iniciarTemporizador (View view) {
        // Bloqueamos el botón de iniciar
        botonIniciar.setEnabled(false);

        // Calculamos de forma aleatoria el valor entre intervalo, comprendido entre 20 y 50 segundos 30)+21
        segundosIntervalo = (int) (Math.random() * 10)+11;

        // Instanciamos y llamamos al hilo encargado del temporizador, pasandole la actividad padre que le llama
        TemporizadorThread temporizadorThread = new TemporizadorThread(this);
        temporizadorThread.execute(segundosIntervalo);
    }

    // Cuando la actividad se va a eliminar, vacío la tabla de jugadores
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }

    // Cuando la actividad pasa a segundo plano (porque se lanzó otra) a de nuevo a primer plano, actualizo el ranking
    @Override
    protected  void onResume() {
        // Actualizamos ranking de jugadores
        actualizarRanking();
        super.onResume();
        // Compruebo que no sea el final del juego (igualado el num de rondas máximo)
        if(numRondasActuales == numRondasFinJuego) {
            // Indico que es el final del juego
            finJuego = true;
            // Muestro fragment final del juego, en ese fragment se muestra quien ha ganado
            showFinDelJuego();
        }
    }

    public void actualizarRanking () {
        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, almacen.getInfoJugadores());
        ranking.setAdapter(adaptador);
    }

    // Método que lanza el dialogFragment referente al evento
    public void showDialog() {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialogFragment");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = Evento.newInstance(almacen.obtenerCartaDelMazo(indicesCartasDelMazo.get(posicionActualDelMazo)), eventos, numRondasActuales);
        posicionActualDelMazo++;
        if (posicionActualDelMazo > indicesCartasDelMazo.size()) {  // Si ya hemos mostrado todas las cartas del mazo, volvemos a barajar y reiniciamos el cursor a la posicion 0
            Collections.shuffle(indicesCartasDelMazo);
            posicionActualDelMazo = 0;
        }
        newFragment.show(ft, "dialogFragment"); // Instanciamos el fragment con el tag dialogFragment
    }

    // Método que llama al dialog de fin del juego
    public void showFinDelJuego() {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ftFinJuego = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("finDelJuego");
        if (prev != null) {
            ftFinJuego.remove(prev);
        }
        ftFinJuego.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragmentFinJuego = FinDelJuegoFragment.newInstance(almacen.getJugadorGanador());
        newFragmentFinJuego.show(ftFinJuego, "finDelJuego");
    }

    // Metodo publico que podra ser llamado desde un fragment para reiniciar el temporizador cuando el fragment desaparezca
    public void     reiniciarTemporizador() {
        iniciarTemporizador(botonIniciar);
    }

    // Método que será llamado al pulsar el botón de cerrar la ventana
    public void cerrarVentana(View view) {
        // Eliminamos el dialog fragment si lo hay
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialogFragment");   // Buscamos el fragment instanciado con el tag correspondiente
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();   // Eliminamos el fragment buscado
        }
        ft.addToBackStack(null);
    }

    // Metodo que podrá ser llamado desde un fregment para arrancar la actividad que contiene las gráficas
    public void verGraficas (View view) {
        Intent intent = new Intent(view.getContext(), GraficasJugadores.class);
        intent.putExtra("numRondasFinJuego", numRondasFinJuego);
        startActivityForResult(intent, 0);
        finish();
    }

    // Guardamos el estado de la actividad por si al llamar a sucesivas actividades, ésta se elimina. Para ello guardamos el númerod e rondas que llevamos
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("numRondasActuales", numRondasActuales);
        outState.putInt("numRondasFinJuego", numRondasFinJuego);
        outState.putInt("posicionActualDelMazo", posicionActualDelMazo);
        super.onSaveInstanceState(outState);
    }

    // Metodo llamado desde el hilo temporizador cuando se vaya a cerrar hilo
    public void finalizarTemporizador() {
        botonIniciar.setEnabled(true);
        // Aumentamos en 1 el número de eventos, si es múltiplo de EVENTOS_POR_RONDAs quiere decir que se ejecuta la actividad de guardar info de cada jugador
        eventos ++;

        // Calculamos el numero de rondas que llevamos, es la división entre EVENTOS_POR_RONDA
        if (eventos%EVENTOS_POR_RONDA==0)
            numRondasActuales++;
        numRondas.setText("Número de ronda: "+Integer.toString(numRondasActuales+1)+"/"+Integer.toString(numRondasFinJuego));

        // Lanzamos el fragmentDialog con el id de la carta del mazo elegida
        showDialog();
    }
}
