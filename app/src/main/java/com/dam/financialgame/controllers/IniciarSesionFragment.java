package com.dam.financialgame.controllers;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dam.financialgame.R;
import com.dam.financialgame.servicesImpl.UsuarioServiceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IniciarSesionFragment extends DialogFragment {

    EditText correo;
    EditText contrasenia;
    Button logearButton;

    public static IniciarSesionFragment newInstance() {
        IniciarSesionFragment frag = new IniciarSesionFragment();

        return frag;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        // Indicamos el estilo que seguirá el dialog fragment, uno creado personalmente y que encontraremos en la carpeta de "values"
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialogFragment);
    }

    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_iniciar_sesion, contenedor, false);

        // Buscamos los view corespondientes generados en la vista
        correo = (EditText)vista.findViewById(R.id.correoInicioSesion);
        contrasenia = (EditText)vista.findViewById(R.id.contraseniaIniciaSesion);
        logearButton = (Button)vista.findViewById(R.id.logearButton);

        // Obtenemos la fecha del sistema.
        final Date date = new Date();
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String fecha = dateFormat.format(date);

        logearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Llamamos al servicio correspondiente de logear usuario.
                // Le pasamos la fecha actual para indicar que se actualice la fecha de la ultima conexion.
                UsuarioServiceImpl.getInstance().logearUsuario(correo.getText().toString(), contrasenia.getText().toString(), fecha, (MenuDeInicio)getActivity());
            }
        });

        return vista;
    }
}
