package com.dam.financialgame.controllers;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.dam.financialgame.R;
import com.dam.financialgame.model.Partida;
import com.dam.financialgame.servicesImpl.PartidaServiceImpl;

import java.util.ArrayList;


public class RankingFragment extends Fragment {

    ListView listViewRanking;
    ArrayAdapter<String> adaptador;
    ArrayList<Partida> partidasAux;

    public static RankingFragment newInstance() {
        RankingFragment frag = new RankingFragment();

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
        View vista = inflater.inflate(R.layout.fragment_ranking, container, false);

        listViewRanking = (ListView) vista.findViewById(R.id.listViewRanking);

        // Ejecutamos el servicio de ranking.
        PartidaServiceImpl.getInstance().obtenerRanking((Comunidad)getActivity());

        return vista;
    }

    // Metodo que muestra los datos obtenidos de la consulta en el contenedor deseado.
    // Indicamos un listener a los elementos del listview.
    public void mostrarDatosObtenidos(ArrayList<Partida> partidas) {
        ArrayList<String> infoPartidas = new ArrayList<String>();
        partidasAux = partidas;

        for(Partida partidaAux: partidasAux) {
            infoPartidas.add(partidaAux.toStringRanking());
        }

        adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, infoPartidas);
        listViewRanking.setAdapter(adaptador);
    }
}
