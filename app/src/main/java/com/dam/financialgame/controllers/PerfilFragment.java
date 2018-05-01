package com.dam.financialgame.controllers;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.dam.financialgame.R;
import com.dam.financialgame.model.Usuario;
import com.dam.financialgame.services.AlmacenSesion;
import com.dam.financialgame.servicesImpl.AlmacenSesionImpl;

public class PerfilFragment extends Fragment{

    TextView nombrePerfil;
    TextView correoPerfil;
    TextView fechaUltimaConexion;
    Usuario usuarioAuxiliar;

    public static PerfilFragment newInstance() {
        PerfilFragment frag = new PerfilFragment();

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
        View vista = inflater.inflate(R.layout.fragment_perfil, container, false);

        nombrePerfil = (TextView)vista.findViewById(R.id.nombrePerfil);
        correoPerfil = (TextView)vista.findViewById(R.id.correoPerfil);
        fechaUltimaConexion = (TextView)vista.findViewById(R.id.ultimaPartidaPerfil);

        usuarioAuxiliar = AlmacenSesionImpl.getInstance(getActivity()).obtenerUsuarioLogeado();

        nombrePerfil.setText(usuarioAuxiliar.getNombre());
        correoPerfil.setText(usuarioAuxiliar.getCorreo());
        fechaUltimaConexion.setText(usuarioAuxiliar.getUltimaconexion());

        return vista;
    }
}
