package com.dam.financialgame.controllers.fragments;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.dam.financialgame.R;
import com.dam.financialgame.controllers.activities.MenuDeInicio;
import com.dam.financialgame.servicesImpl.UsuarioServiceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IniciarSesionFragment extends DialogFragment {

    EditText correo;
    EditText contrasenia;
    Button logearButton;
    Button registrarUsuario;
    ImageButton cerrarDialog;
    private boolean desdeMenuInicio = false;

    public static IniciarSesionFragment newInstance() {
        IniciarSesionFragment frag = new IniciarSesionFragment();

        return frag;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        // Indicamos el estilo que seguirá el dialog fragment, uno creado personalmente y que encontraremos en la carpeta de "values"
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialogFragment);

        // Este fragmento, como será llamado desde varias actividades, se le manda un parametro indicando si se le llama del menu de
        // inicio o de otro sitio. Como el boton registrar solo esta activo en inicio, lo desactivamos en caso contrario.
        if (getArguments() != null) {
            desdeMenuInicio = getArguments().getBoolean("desdeMenuInicio", false);
        }
    }

    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_iniciar_sesion, contenedor, false);

        // Buscamos los view corespondientes generados en la vista
        correo = (EditText)vista.findViewById(R.id.correoInicioSesion);
        contrasenia = (EditText)vista.findViewById(R.id.contraseniaIniciaSesion);
        logearButton = (Button)vista.findViewById(R.id.logearButton);
        cerrarDialog = (ImageButton)vista.findViewById(R.id.cerrarIniciarSesionDialog);
        registrarUsuario = (Button)vista.findViewById(R.id.buttonRegistrar);

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

        // Listener a la imagebutton para que, si hacemos clic, se cierre la ventana.
        cerrarDialog.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dismiss();
            }
        });

        if(!desdeMenuInicio) {
            registrarUsuario.setClickable(false);
        }

        return vista;
    }
}
