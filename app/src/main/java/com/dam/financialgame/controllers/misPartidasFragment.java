package com.dam.financialgame.controllers;


import android.widget.ArrayAdapter;
import android.widget.ListView;

public class misPartidasFragment {
    ListView listViewPartidas;
    ArrayAdapter<String> adaptador;

    /*
    listViewPartidas = (ListView) findViewById(R.id.listViewPartidas);

    // Metodo que muestra los datos obtenidos de la consulta en el contenedor deseado.
    public void mostrarDatosObtenidos(ArrayList<Partida> partidas) {
        ArrayList<String> infoPartidas = new ArrayList<String>();

        for(Partida partidaAux: partidas) {
            infoPartidas.add(partidaAux.toString());
        }

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, infoPartidas);
        listViewPartidas.setAdapter(adaptador);
    }

     */

    /*
    // Método llamado desde un botón para enviar al servidor info partida al servidor.
    public void postPartida(View view) {
        PartidaServiceImpl.getInstance().subirPartida(8, 8, nombre.getText().toString(), 200000, this);
    }

    public void obtenerPartidasSubidas(View view) {
        PartidaServiceImpl.getInstance().obtenerPartidasSubidas(this);
    }

    // Método llamado desde un botón para recargar el ranking de la vista con la información almacenada en el servidor.
    public void recargaRanking(View view) {
        PartidaServiceImpl.getInstance().obtenerPartidasSubidas(this);
    }

     */
}
