package com.dam.financialgame.controllers.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dam.financialgame.R;
import com.dam.financialgame.controllers.activities.Comunidad;
import com.dam.financialgame.model.Logro;
import com.dam.financialgame.servicesImpl.LogroServiceImpl;

import java.util.ArrayList;


public class LogrosFragment extends Fragment {

    ArrayAdapter<String> adaptador;
    TextView numerosLogrosBronce;
    TextView numerosLogrosPlata;
    TextView numerosLogrosOro;
    ListView listLogros;


    public static LogrosFragment newInstance() {
        LogrosFragment frag = new LogrosFragment();

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
        View vista = inflater.inflate(R.layout.fragment_logros, container, false);

        numerosLogrosBronce = (TextView)vista.findViewById(R.id.numeroLogrosBronce);
        numerosLogrosPlata = (TextView)vista.findViewById(R.id.numerosLogrosPlata);
        numerosLogrosOro = (TextView)vista.findViewById(R.id.numeroLogrosOro);
        listLogros = (ListView)vista.findViewById(R.id.listLogros);

        // Lamamos al servicio de obtener logros del usuario.
        LogroServiceImpl.getInstance().obtenerLogros((Comunidad)getActivity());

        return vista;
    }

    // Metodo que muestra los logros obtenidos del usuario.
    public void mostrarDatosObtenidos(ArrayList<Logro> logros) {

        ArrayList<Logro> logrosBronce = new ArrayList<>();
        ArrayList<Logro> logrosPlata = new ArrayList<>();
        ArrayList<Logro> logrosOro = new ArrayList<>();
        ArrayList<String> infoLogroBronce = new ArrayList<>();
        ArrayList<String> infoLogroPlata = new ArrayList<>();
        ArrayList<String> infoLogroOro = new ArrayList<>();

        for(Logro logroAuxiliar: logros) {
            switch (logroAuxiliar.getTipo()) {
                case "Bronce":
                    logrosBronce.add(logroAuxiliar);
                    infoLogroBronce.add(logroAuxiliar.toString());
                    break;
                case "Plata":
                    logrosPlata.add(logroAuxiliar);
                    infoLogroPlata.add(logroAuxiliar.toString());
                    break;
                case "Oro":
                    logrosOro.add(logroAuxiliar);
                    infoLogroOro.add(logroAuxiliar.toString());
                    break;
                default:

            }
        }

        numerosLogrosBronce.setText(Integer.toString(logrosBronce.size()));
        numerosLogrosPlata.setText(Integer.toString(logrosPlata.size()));
        numerosLogrosOro.setText(Integer.toString(logrosOro.size()));

        infoLogroBronce.addAll(infoLogroPlata);
        infoLogroBronce.addAll(infoLogroOro);

        // Creamos un adapter para los listview expandibles y lo introducimos.
        adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, infoLogroBronce);
        listLogros.setAdapter(adaptador);

    }
}
