package com.dam.financialgame.controllers;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.dam.financialgame.R;

public class OpcionesDeManualFragment extends DialogFragment {
    Button buttonInstrucciones;
    Button buttonManualDeUsuario;
    ImageButton cerrarDialog;

    public static OpcionesDeManualFragment newInstance() {
        OpcionesDeManualFragment frag = new OpcionesDeManualFragment();

        return frag;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        // Indicamos el estilo que seguirá el dialog fragment, uno creado personalmente y que encontraremos en la carpeta de "values"
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialogFragment);
    }

    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_opciones_de_manual, contenedor, false);

        // Buscamos los view corespondientes generados en la vista
        buttonInstrucciones = (Button)vista.findViewById(R.id.buttonInstrucciones);
        cerrarDialog = (ImageButton)vista.findViewById(R.id.cerrarManualesDialog);
        buttonManualDeUsuario = (Button)vista.findViewById(R.id.buttonManualDeUsuario);

        buttonInstrucciones.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Lanzamos la actividad que contiene el pdfView de instrucciones
                Intent mainIntent = new Intent().setClass(view.getContext(), InstruccionesPartida.class);
                startActivity(mainIntent);
                dismiss();
            }
        });

        buttonManualDeUsuario.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Lanzamos la actividad que contiene el pdfView de manual de usuario
                Intent mainIntent = new Intent().setClass(view.getContext(), ManualDeUsuario.class);
                startActivity(mainIntent);
                dismiss();
            }
        });

        // Listener a la imagebutton para que, si hacemos clic, se cierre la ventana.
        cerrarDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dismiss();
            }
        });

        return vista;
    }
}
