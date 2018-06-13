package com.dam.financialgame.controllers.activities;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.dam.financialgame.R;
import com.dam.financialgame.model.JugadorOnline;
import com.dam.financialgame.servicesImpl.AlmacenSesionImpl;
import com.dam.financialgame.servicesImpl.JugadorOnlineServiceImpl;
import com.dam.financialgame.servicesImpl.PartidaOnlineServiceImpl;
import com.dam.financialgame.threads.GestorEstadoPartidaOnlineInicializada;

import java.util.ArrayList;

public class PartidaOnlineInicializada extends AppCompatActivity {

    TextView textViewNumJugadores;
    TextView textViewNumRondas;
    private boolean soyAnfitrion;
    private String textoNumRondas;
    int numRondas;
    private String textoNumJugadoresMax;
    ListView jugadoresUnidos;
    LinearLayout notaJugadorOnlinePreparado;
    ArrayAdapter<String> adaptador;
    GestorEstadoPartidaOnlineInicializada gestor;
    boolean jugadorPreparado = false;   // Variable auxiliar que nos servirá para controlar el estado del jugador.
    boolean actividadYaMostrada = false;    // Variable auxiliar que me permite saber si ya se ha mostrado la actividad siquiente.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partida_online_inicializada);

        numRondas = getIntent().getExtras().getInt("numRondas");

        textoNumRondas = "Número de rondas: " + Integer.toString(numRondas);
        textoNumJugadoresMax = "Jugadores máximos: " + Integer.toString(getIntent().getExtras().getInt("numJugadoresMax"));
        soyAnfitrion = getIntent().getExtras().getBoolean("soyAnfitrion");

        inicializar();
    }

    private void inicializar() {
        textViewNumJugadores = (TextView)findViewById(R.id.textoNumJugadores);
        textViewNumRondas = (TextView)findViewById(R.id.textoNumRondas);
        jugadoresUnidos = (ListView)findViewById(R.id.listJugadoresUnidos);
        notaJugadorOnlinePreparado = (LinearLayout) findViewById(R.id.notaJugadorOnlinePreparado);

        notaJugadorOnlinePreparado.setVisibility(View.INVISIBLE);

        textViewNumRondas.setText(textoNumRondas);
        textViewNumJugadores.setText(textoNumJugadoresMax);

        // Obtenemos los identificadores online del usuario, de su jugador y de la partida en la que participa.
        JugadorOnlineServiceImpl.getInstance().getIdsOnline(this);

        // Iniciamos proceso de escucha activa por el que pedimos informacion periodica de los jugadores unidos, recargando el listView.
        // Lamamos al hilo AsynTask que enviara las peticiones de la escucha activa.
        gestor = new GestorEstadoPartidaOnlineInicializada(this);
        gestor.execute();

    }

    // Primero comprobamos que estemos preparados, si no lo indicamos y avisamos al servidor.
    public void iniciarPartida(View view) {
        if(!jugadorPreparado) {
            jugadorPreparado = true;
            notaJugadorOnlinePreparado.setVisibility(View.VISIBLE);
            JugadorOnlineServiceImpl.getInstance().modificaEstadoJugadorOnline(AlmacenSesionImpl.getInstance(this).getIdJugadorOnlineAlmacenado(),
                    Integer.parseInt(getResources().getString(R.string.estado_jugador_preparado)), this);
        } else {
            jugadorPreparado = false;
            notaJugadorOnlinePreparado.setVisibility(View.INVISIBLE);
            JugadorOnlineServiceImpl.getInstance().modificaEstadoJugadorOnline(AlmacenSesionImpl.getInstance(this).getIdJugadorOnlineAlmacenado(),
                    Integer.parseInt(getResources().getString(R.string.estado_jugador_creado)), this);
        }
    }

    // Metodo que muestra los datos en el listView. Debemos indicar si estan preparados o no segun el estado del jugador online.
    // En caso de que todos preparados, iniciamos partida.
    // En caso de que recibamos un array vacío, quiere decir que la partida ha sido eliminada, por lo tanto nos vamos de la actividad.
    public void muestraDatosJugadoresOnline(ArrayList<JugadorOnline> jugadoresOnline) {
        String infoJugador;
        String jugadorPreparado;
        String jugadorAnfitrion;
        ArrayList<String> infoJugadores = new ArrayList<String>();

        if(jugadoresOnline.isEmpty()) {
            finish();
        } else if(todosPreparados(jugadoresOnline)) {
            Log.d("PartidaOnline: ","Todos preparados!!");
            // El anfitrion, actualiza el estado de la partida al estado Iniciada
            if(soyAnfitrion) {
                PartidaOnlineServiceImpl.getInstance().setEstadoPartidaOnline(getResources().getString(R.string.estado_partida_online_en_proceso), this);
            }

            for(JugadorOnline jugadorAux: jugadoresOnline) {
                if(jugadorAux.getEstado() == Integer.parseInt(this.getResources().getString(R.string.estado_jugador_preparado))) {
                    jugadorPreparado = "PREPARADO!";
                } else {
                    jugadorPreparado = "";
                }

                if(jugadorAux.isAnfitrion()) {
                    jugadorAnfitrion = " ANFITRION";
                } else {
                    jugadorAnfitrion = "";
                }

                infoJugador = jugadorAux.getNombreUsuario() + "   " + jugadorAnfitrion + "\n" + jugadorPreparado + "\n";
                infoJugadores.add(infoJugador);
            }

            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, infoJugadores);
            jugadoresUnidos.setAdapter(adaptador);

            // Pasamos a la actividad escenario, si no esta siendo mostrado ya.
            if(!actividadYaMostrada) {
                Intent intent = new Intent(this, EscenarioOnline.class);
                intent.putExtra("numRondas", numRondas);
                startActivityForResult(intent, 0);
            }
        } else {
            // El anfitrion, actualiza el nº de jugadores unidos de la partida online.
            if(soyAnfitrion) {
                PartidaOnlineServiceImpl.getInstance().setNumeroJugadores(jugadoresOnline.size(), this);
            }

            for(JugadorOnline jugadorAux: jugadoresOnline) {
                if(jugadorAux.getEstado() == Integer.parseInt(this.getResources().getString(R.string.estado_jugador_preparado))) {
                    jugadorPreparado = "PREPARADO!";
                } else {
                    jugadorPreparado = "";
                }

                if(jugadorAux.isAnfitrion()) {
                    jugadorAnfitrion = " ANFITRION";
                } else {
                    jugadorAnfitrion = "";
                }

                infoJugador = jugadorAux.getNombreUsuario() + "   " + jugadorAnfitrion + "\n" + jugadorPreparado + "\n";
                infoJugadores.add(infoJugador);
            }

            adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, infoJugadores);
            jugadoresUnidos.setAdapter(adaptador);
        }
    }

    public boolean todosPreparados(ArrayList<JugadorOnline> jugadoresOnline) {
        boolean preparado = true;

        for (int i = 0; preparado && i<jugadoresOnline.size(); i++) {
            if(jugadoresOnline.get(i).getEstado() != Integer.parseInt(this.getResources().getString(R.string.estado_jugador_preparado))) {
                preparado = false;
            }
        }

        return preparado;
    }

    // Si salimos de la actividad, debemos de indicarlo al servidor. En caso de ser el anfitrion, la partida se da por cancelada.
    // Tambien paramos el servicio de escucha activa de los jugadores unidos.
    @Override
    protected void onDestroy() {
        super.onDestroy();
        gestor.cancel(true);

        // También mandamos petición de eliminar el usuario y la partida (esta ultima solo se elimina si somos anfitrion, eso lo procesa el servidor)
        JugadorOnlineServiceImpl.getInstance().eliminarJugadorOnline(this);
        PartidaOnlineServiceImpl.getInstance().eliminarPartidaOnline(this);

        // Limpiamos los ids online almacenados en preferencias.
        AlmacenSesionImpl.getInstance(this).borrarIdsOnline();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gestor.cancel(true);
    }

    // Cuando la actividad se reanuda, reiniciamos el AsyncTask.
    // Para reiniciar un asyncTask debemos instanciarlos de nuevo, ya que si se cancela no podemos reanudarlo.
    @Override
    protected void onRestart() {
        super.onRestart();
        actividadYaMostrada = false;

        if(gestor.isCancelled()) {
            gestor = new GestorEstadoPartidaOnlineInicializada(this);
            gestor.execute();
        }
    }

    // Como solo queremos que se muestre solo una vez la actividdad EscenarioOnline, y se lanza cuando recivbimos callback
    // de una peticion Rest, debemos controlar que si ya se ha lanzado, no volverse a lanzar. Utilizamos una variable de control.
    @Override
    protected void onStop() {
        super.onStop();

        actividadYaMostrada = true;
    }
}
