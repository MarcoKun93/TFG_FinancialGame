package com.dam.financialgame.controllers;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dam.financialgame.R;
import com.dam.financialgame.model.PartidaOnline;
import com.dam.financialgame.servicesImpl.JugadorOnlineServiceImpl;
import com.dam.financialgame.threads.GestorEstadoPartidasOnlineCreadas;

import java.util.ArrayList;

public class PrincipalPartidasOnline extends AppCompatActivity {

    ListView listadoPartidasOnline;
    GestorEstadoPartidasOnlineCreadas gestor;
    ArrayList<PartidaOnline> partidasAux;
    ArrayAdapter<String> adaptador;
    PartidaOnline partidaOnlineSeleccionada;
    private int numRondas;
    private int numJugadoresMax;
    private boolean soyAnfitrion = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal_partidas_online);

        listadoPartidasOnline = (ListView) findViewById(R.id.listViewPartidasOnline);

        // Si pulsamos encima de una partida listada, nos unimos a ella.
        listadoPartidasOnline.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;

                // Llamamos al metodo que nos lanza a la siguiente actividad uniendonos a la partida.
                // Para ello debemos tener el idPartidaOnline y mandarselo como parametro.
                partidaOnlineSeleccionada = partidasAux.get(pos);
                numJugadoresMax = partidaOnlineSeleccionada.getNumjugadoresmax();
                numRondas = partidaOnlineSeleccionada.getNumrondas();

                crearJugadorOnline();

            }
        });

        // Lamamos al hilo AsynTask que enviara las peticiones de la escucha activa.
        gestor = new GestorEstadoPartidasOnlineCreadas(this);
        gestor.execute();
    }

    public void crearJugadorOnline () {
        JugadorOnlineServiceImpl.getInstance().postJugadorOnline(partidaOnlineSeleccionada.getIdPartida(), this);
    }

    // Metodo llamado al pulsar el boton, nos aparecera un fragment para poner algunas opciones de la partida y crearla
    public void crearPartida(View view) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialogCrearPartidaOnline");
        if (prev != null) {
            //ft.remove(prev);
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = CrearPartidaOnlineFragment.newInstance();
        newFragment.show(ft, "dialogCrearPartidaOnline"); // Instanciamos el fragment con el tag dialogFragment
    }

    // Metodo llamado desde un boton para finalizar la actividad
    public void salirDePartidasOnline(View view) {
        finish();
    }

    // Metodo llamado desde un servicio android para recargar el listado de forma automatica.
    public void recargarListadoPartidasOnline(ArrayList<PartidaOnline> partidasOnline) {
        ArrayList<String> infoPartidas = new ArrayList<String>();
        partidasAux = partidasOnline;

        for(PartidaOnline partidaAux: partidasAux) {
            infoPartidas.add(partidaAux.toString());
        }

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, infoPartidas);
        listadoPartidasOnline.setAdapter(adaptador);
    }

    // Metodo callback llamado desde el servicio cuando la partida se crea correctamente, automaticamente pasamos a la siguiente actividad.
    public void irPartidaInicializada() {
        Intent intent = new Intent(this, PartidaOnlineInicializada.class);
        intent.putExtra("numRondas", numRondas);
        intent.putExtra("numJugadoresMax", numJugadoresMax);
        intent.putExtra("soyAnfitrion", soyAnfitrion);
        startActivityForResult(intent, 0);
        finish();
    }

    // Metodo llamado desde el fragment crear partida para indicar los valores de esta.
    public void actualizaParametros(int numRondas, int numJugadoresMax, boolean soyAnfitrion) {
        this.numRondas = numRondas;
        this.numJugadoresMax = numJugadoresMax;
        this.soyAnfitrion = soyAnfitrion;
    }

    // Cuando la actividad sea destruida, o puesta en background, cancelamos el AsyncTask
    @Override
    protected void onPause() {
        super.onPause();
        gestor.cancel(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        gestor.cancel(true);
    }
}
