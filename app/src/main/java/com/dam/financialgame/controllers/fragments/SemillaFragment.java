package com.dam.financialgame.controllers.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import com.dam.financialgame.R;
import com.dam.financialgame.controllers.activities.Comunidad;
import com.dam.financialgame.model.Semilla;
import com.dam.financialgame.servicesImpl.AlmacenSesionImpl;
import com.dam.financialgame.servicesImpl.SemillaServiceImpl;

import java.util.ArrayList;

public class SemillaFragment extends Fragment {

    ListView listViewSemillas;
    TextView semillaTitulo;
    TextView semillaDescripcion;
    TextView semillaEfecto;
    CheckBox checkBoxSemilla;
    ArrayList<Semilla> semillasAux;
    ArrayAdapter<String> adaptador;
    Semilla semillaSeleccionada;

    public static SemillaFragment newInstance() {
        SemillaFragment frag = new SemillaFragment();

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
        View vista = inflater.inflate(R.layout.fragment_semilla, container, false);

        listViewSemillas = (ListView)vista.findViewById(R.id.listViewSemillas);
        semillaTitulo = (TextView)vista.findViewById(R.id.semillaTitulo);
        semillaDescripcion = (TextView)vista.findViewById(R.id.semillaDescripcion);
        semillaEfecto = (TextView)vista.findViewById(R.id.semillaEfecto);
        checkBoxSemilla = (CheckBox)vista.findViewById(R.id.checkBoxSemilla);

        // Ponemos un listener al checkBox, si lo activa, guardamos el idSemilla en el archivo de preferencias.
        checkBoxSemilla.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    // perform logic
                    AlmacenSesionImpl.getInstance(getActivity()).guardarSemillaId(semillaSeleccionada.getIdSemilla());
                } else {
                    Log.d("Semilla Fragment", "CheckBox quitado");
                    if(semillaSeleccionada.getIdSemilla() == AlmacenSesionImpl.getInstance(getActivity()).obtenerSemillaId()) {
                        AlmacenSesionImpl.getInstance(getActivity()).borrarSemilla();
                    }
                }
            }

        });

        listViewSemillas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int pos = position;
                checkBoxSemilla.setVisibility(View.VISIBLE);
                // Lammamos al metodo que muestrta informacion sobre la semilla seleccionada.
                muestraDatosSemilla(pos);
            }
        });

        // Llamamos al servicio que da las partidas subidas por el usuario.
        SemillaServiceImpl.getInstance().obtenerSemillas((Comunidad)getActivity());

        // Comprobamos si ya hay una semilla seleccionada con anterioridad.
        // En caso positivo, se la mostramos directamente.
        if(AlmacenSesionImpl.getInstance(getActivity()).obtenerSemillaId() != 0) {
            SemillaServiceImpl.getInstance().obtenerSemillas((Comunidad)getActivity(), AlmacenSesionImpl.getInstance(getActivity()).obtenerSemillaId());
        } else {
            checkBoxSemilla.setVisibility(View.INVISIBLE);
        }

        return vista;
    }

    // Metodo que muestra los datos obtenidos de la consulta en el contenedor deseado.
    // Indicamos un listener a los elementos del listview.
    public void mostrarDatosObtenidos(ArrayList<Semilla> semillas) {
        ArrayList<String> infoSemillas = new ArrayList<String>();
        semillasAux = semillas;

        for(Semilla semillaAux: semillasAux) {
            infoSemillas.add(semillaAux.toString());
        }

        adaptador = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, infoSemillas);
        listViewSemillas.setAdapter(adaptador);
    }

    // Metodo que muestra la info de una partida seleccionada del listView.
    public void muestraDatosSemilla(int indice) {
        semillaSeleccionada = semillasAux.get(indice);

        // Cuando hacemos referencia a una semilla de la lista, antes comprobamos si está ya seleccionada o no.
        if(semillasAux.get(indice).getIdSemilla() == AlmacenSesionImpl.getInstance(getActivity()).obtenerSemillaId()) {
            checkBoxSemilla.setChecked(true);
        } else {
            checkBoxSemilla.setChecked(false);
        }

        semillaTitulo.setText(semillasAux.get(indice).getTitulo());
        semillaDescripcion.setText(semillasAux.get(indice).getDescripcion());
        semillaEfecto.setText(semillasAux.get(indice).getEfecto());
    }

    // Metodo que muestra directamente, al inicializar la vista de Semilla, la semilla ya seleccionada anteriormente.
    public void mostrarSemillaYaSeleccionada(Semilla semilla) {
        semillaSeleccionada = semilla;

        checkBoxSemilla.setVisibility(View.VISIBLE);
        checkBoxSemilla.setChecked(true);
        semillaTitulo.setText(semillaSeleccionada.getTitulo());
        semillaDescripcion.setText(semillaSeleccionada.getDescripcion());
        semillaEfecto.setText(semillaSeleccionada.getEfecto());
    }
}
