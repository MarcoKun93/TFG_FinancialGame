package com.dam.financialgame.controllers;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.dam.financialgame.R;
import com.dam.financialgame.model.Partida;
import com.dam.financialgame.servicesImpl.PartidaServiceImpl;

import java.util.ArrayList;

public class MisPartidasFragment extends Fragment{
    ListView listViewPartidas;
    ArrayAdapter<String> adaptador;
    ArrayList<Partida> partidasAux;
    Partida partidaSeleccionada;
    TextView fechaPartida;
    TextView numrondaPartida;
    TextView numjugadoresPartida;
    TextView nombreganadorPartida;
    TextView puntuacionganadorPartida;
    ImageButton borrarPartidaButton;

    public static MisPartidasFragment newInstance() {
        MisPartidasFragment frag = new MisPartidasFragment();

        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Aquí ponemos el estilo que seguirá el fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_mis_partidas, container, false);

        listViewPartidas = (ListView) vista.findViewById(R.id.listViewPartidas);
        fechaPartida = (TextView)vista.findViewById(R.id.fechaPartida);
        numrondaPartida = (TextView)vista.findViewById(R.id.numrondasPartida);
        numjugadoresPartida = (TextView)vista.findViewById(R.id.numjugadoresPartida);
        nombreganadorPartida = (TextView)vista.findViewById(R.id.nombreganadorPartida);
        puntuacionganadorPartida = (TextView)vista.findViewById(R.id.puntuacionganadorPartida);
        borrarPartidaButton = (ImageButton) vista.findViewById(R.id.buttonBorrarPartida);

        borrarPartidaButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Llamamos al metodo que procesa el servicio de borrar partida
                borrarPartidaSeleccionada();
            }
        });

        borrarPartidaButton.setVisibility(View.INVISIBLE);

        listViewPartidas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                borrarPartidaButton.setVisibility(View.VISIBLE);
                // Lammamos al metodo que muestrta informacion sobre la partida seleccionada.
                muestraDatosPartida(pos);
            }
        });

        // Llamamos al servicio que da las partidas subidas por el usuario.
        PartidaServiceImpl.getInstance().obtenerPartidasSubidas((Comunidad) getActivity());

        return vista;
    }

    // Metodo que muestra los datos obtenidos de la consulta en el contenedor deseado.
    // Indicamos un listener a los elementos del listview.
    public void mostrarDatosObtenidos(ArrayList<Partida> partidas) {
        ArrayList<String> infoPartidas = new ArrayList<String>();
        partidasAux = partidas;

        for(Partida partidaAux: partidasAux) {
            infoPartidas.add(partidaAux.toString());
        }

        adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, infoPartidas);
        listViewPartidas.setAdapter(adaptador);
    }

    // Metodo que muestra la info de una partida seleccionada del listView.
    public void muestraDatosPartida(int indice) {
        // Cogemos la partida seleccionada por si queremos borrarla.
        partidaSeleccionada = partidasAux.get(indice);

        fechaPartida.setText(partidasAux.get(indice).getFecha());
        numrondaPartida.setText(Integer.toString(partidasAux.get(indice).getNumrondas()));
        numjugadoresPartida.setText(Integer.toString(partidasAux.get(indice).getNumjugadores()));
        nombreganadorPartida.setText(partidasAux.get(indice).getNombreganador());
        puntuacionganadorPartida.setText(Integer.toString(partidasAux.get(indice).getPuntuacionganador()));
    }

    // Metodo que borrar la partida deseada.
    public void borrarPartidaSeleccionada() {
        if(partidaSeleccionada != null) {
            PartidaServiceImpl.getInstance().borrarPartida(partidaSeleccionada.getIdPartida(), getActivity());

            // Reiniciamos la ventana.
            partidaSeleccionada = null;
            borrarPartidaButton.setVisibility(View.INVISIBLE);
            fechaPartida.setText("");
            numrondaPartida.setText("");
            numjugadoresPartida.setText("");
            nombreganadorPartida.setText("");
            puntuacionganadorPartida.setText("");
            PartidaServiceImpl.getInstance().obtenerPartidasSubidas((Comunidad) getActivity());
        }
    }
}
