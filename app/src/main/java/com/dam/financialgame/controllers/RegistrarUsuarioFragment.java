package com.dam.financialgame.controllers;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.dam.financialgame.R;
import com.dam.financialgame.servicesImpl.UsuarioServiceImpl;

import java.util.regex.Pattern;

public class RegistrarUsuarioFragment extends DialogFragment {

    // Variables de la interfaz de usuario
    EditText nombre;
    EditText contrasenia;
    EditText correo;
    Button registrarButton;

    public static RegistrarUsuarioFragment newInstance() {
        RegistrarUsuarioFragment frag = new RegistrarUsuarioFragment();

        return frag;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        // Indicamos el estilo que seguirá el dialog fragment, uno creado personalmente y que encontraremos en la carpeta de "values"
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialogFragment);
    }

    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_registrar_usuario, contenedor, false);

        // Buscamos los view corespondientes generados en la vista
        nombre = (EditText)vista.findViewById(R.id.nombreRegistrarUsuario);
        contrasenia = (EditText)vista.findViewById(R.id.contraseniaRegistrarUsuario);
        correo = (EditText)vista.findViewById(R.id.correoRegistrarUsuario);
        registrarButton = (Button)vista.findViewById(R.id.registrarButton);

        registrarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Comprobamos que el formato rea correcto.
                if(validarEmail(correo.getText().toString())) {
                    // Lamamos al servicio correspondiente de registrar usuario
                    UsuarioServiceImpl.getInstance().registrarUsuario(nombre.getText().toString(), contrasenia.getText().toString(), correo.getText().toString(), (MenuDeInicio)getActivity());
                } else {
                    correo.setText("Email no válido");
                }
            }
        });

        return vista;
    }

    public boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;

        return pattern.matcher(email).matches();
    }
}
