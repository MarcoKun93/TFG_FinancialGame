package com.dam.financialgame.controllers;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.dam.financialgame.R;
import com.dam.financialgame.services.AlmacenSesion;
import com.dam.financialgame.servicesImpl.AlmacenSesionImpl;
import com.dam.financialgame.servicesImpl.PartidaOnlineServiceImpl;

public class CrearPartidaOnlineFragment extends DialogFragment {

    TextView nombreAnfitrion;
    Spinner numRondas;
    Spinner numJugadoresMax;
    ImageButton cerrarDialogo;
    Button confirmarPartidaOnline;

    public static CrearPartidaOnlineFragment newInstance() {
        CrearPartidaOnlineFragment frag = new CrearPartidaOnlineFragment();

        return frag;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        // Indicamos el estilo que seguirá el dialog fragment, uno creado personalmente y que encontraremos en la carpeta de "values"
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialogFragment);
    }

    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_crear_partida_online, contenedor, false);
        String[] opcionesRondas = {"2","4","6"};
        String[] opcionesJugadoresMax = {"2","3","4","5","6"};

        // Buscamos los view corespondientes generados en la vista
        nombreAnfitrion = (TextView) vista.findViewById(R.id.nombreAnfitrion);
        numRondas = (Spinner) vista.findViewById(R.id.numRondas);
        numJugadoresMax = (Spinner) vista.findViewById(R.id.numJugadoresMax);

        numRondas.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, opcionesRondas));
        numJugadoresMax.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, opcionesJugadoresMax));
        nombreAnfitrion.setText(AlmacenSesionImpl.getInstance(getActivity()).obtenerUsuarioLogeado().getNombre());

        confirmarPartidaOnline = (Button) vista.findViewById(R.id.confirmaPartidaOnline);
        confirmarPartidaOnline.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((PrincipalPartidasOnline)getActivity()).actualizaParametros(Integer.parseInt(numRondas.getSelectedItem().toString()),
                        Integer.parseInt(numJugadoresMax.getSelectedItem().toString()), true);

                // Creamos la partida con los valores indicados por el usuario
                PartidaOnlineServiceImpl.getInstance().crearPartidaOnline(Integer.parseInt(numRondas.getSelectedItem().toString()),
                        Integer.parseInt(numJugadoresMax.getSelectedItem().toString()), (PrincipalPartidasOnline) getActivity());
                dismiss();
            }
        });

        cerrarDialogo = (ImageButton)vista.findViewById(R.id.cerrarDialogCrearPartidaOnline);
        cerrarDialogo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Cerramos el dialog fragment
                dismiss();
            }
        });

        return vista;
    }
}
