package com.dam.financialgame.controllers.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.financialgame.R;
import com.dam.financialgame.model.Usuario;
import com.dam.financialgame.servicesImpl.AlmacenSesionImpl;
import com.dam.financialgame.servicesImpl.UsuarioServiceImpl;

public class PerfilFragment extends Fragment{

    TextView nombrePerfil;
    TextView correoPerfil;
    TextView fechaUltimaConexion;
    Usuario usuarioAuxiliar;
    LinearLayout opcionContraseña;
    Button buttonContraseña;
    Button buttonOkContraseña;
    EditText nuevaContraseña;

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
        opcionContraseña = (LinearLayout)vista.findViewById(R.id.opcionCambiarContraseña);
        buttonContraseña = (Button)vista.findViewById(R.id.buttonContraseña);
        buttonOkContraseña = (Button)vista.findViewById(R.id.buttonOkContraseña);
        nuevaContraseña = (EditText)vista.findViewById(R.id.nuevaContraseña);

        buttonContraseña.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Llamamos al metodo que procesa el servicio de borrar partida
                muestraOpcionesCambiarContraseña();
            }
        });

        buttonOkContraseña.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                // Llamamos al metodo que procesa el servicio de borrar partida
                cambiarContraseña();
            }
        });

        opcionContraseña.setVisibility(View.INVISIBLE);

        usuarioAuxiliar = AlmacenSesionImpl.getInstance(getActivity()).obtenerUsuarioLogeado();

        nombrePerfil.setText(usuarioAuxiliar.getNombre());
        correoPerfil.setText(usuarioAuxiliar.getCorreo());
        fechaUltimaConexion.setText(usuarioAuxiliar.getUltimaconexion());

        return vista;
    }

    public void muestraOpcionesCambiarContraseña() {
        opcionContraseña.setVisibility(View.VISIBLE);
    }

    public void cambiarContraseña() {

        if(!nuevaContraseña.getText().toString().equals("")) {
            UsuarioServiceImpl.getInstance().cambiarContrasenia(getActivity(), nuevaContraseña.getText().toString());
            nuevaContraseña.getText().clear();
            opcionContraseña.setVisibility(View.INVISIBLE);
        } else {
            Toast toast = Toast.makeText(getActivity(), "Contraseña no válida", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
            toast.show();
        }
    }
}
