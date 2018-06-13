package com.dam.financialgame.controllers.activities;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.dam.financialgame.R;
import com.dam.financialgame.controllers.fragments.IniciarSesionFragment;
import com.dam.financialgame.controllers.fragments.OpcionesDeManualFragment;
import com.dam.financialgame.controllers.fragments.RegistrarUsuarioFragment;
import com.dam.financialgame.model.Usuario;
import com.dam.financialgame.servicesImpl.AlmacenSesionImpl;

public class MenuDeInicio extends AppCompatActivity {

    ImageButton iniciarSesionButton;
    TextView nombreUsuarioLogeado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_de_inicio);

        iniciarSesionButton = (ImageButton)findViewById(R.id.iniciarSesion);
        nombreUsuarioLogeado = (TextView)findViewById(R.id.nombreUsuarioLogeado);

        // Comprobamos si la sesion del usuario estaba iniciada, y tenemos la claveapi.
        // En caso positivo, cambiamos la imagen del icono.
        if(AlmacenSesionImpl.getInstance(this).comprobarLogeado()) {
            iniciarSesionButton.setBackgroundResource(R.drawable.salirsesion);
            nombreUsuarioLogeado.setText(AlmacenSesionImpl.getInstance(this).obtenerUsuarioLogeado().getNombre());
        }
    }

    // Se arrancará la actividad de iniciar àrtida normal, como siempre.
    public void irIniciarPartida(View view) {
        // Start the next activity
        Intent mainIntent = new Intent().setClass(view.getContext(), InicializarPartida.class);
        startActivity(mainIntent);
    }

    // Se arrancará la actividad de iniciar partida online.
    public void irIniciarPartidaOnline(View view) {
        // Start the next activity
        if(AlmacenSesionImpl.getInstance(this).comprobarLogeado()) {
            // Start the next activity
            Intent mainIntent = new Intent().setClass(view.getContext(), PrincipalPartidasOnline.class);
            startActivity(mainIntent);
        } else {
            showDialogIniciaSesion(view);
        }
    }

    // El método iniciará una actividad que arrancará todas las funcionalidades online del juego.
    public void irComunidad(View view) {
        // Comprobamos si la sesion del usuario estaba iniciada, y tenemos la claveapi.
        // En caso positivo, accedemos a la actividad comunidad.
        // En caso negativo, mostramos el dialogo de iniciar sesion
        if(AlmacenSesionImpl.getInstance(this).comprobarLogeado()) {
            // Start the next activity
            Intent mainIntent = new Intent().setClass(view.getContext(), Comunidad.class);
            startActivity(mainIntent);
        } else {
            showDialogIniciaSesion(view);
        }
    }

    // Las instrucciones serán mostradas de forma separada en actividades, antes se lanza un fragment para seleccionar cual ver.
    public void irInstrucciones(View view) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialogManuales");
        if (prev != null) {
            //ft.remove(prev);
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = OpcionesDeManualFragment.newInstance();
        newFragment.show(ft, "dialogManuales"); // Instanciamos el fragment con el tag dialogManuales
    }

    // Método que lanza el dialogFragment referente a la ventana de iniciar sesión.
    public void showDialogIniciaSesion(View view) {
        // Comprobamos si la sesion del usuario estaba iniciada, y tenemos la claveapi.
        // En caso positivo, cerramos sesion.
        if(AlmacenSesionImpl.getInstance(this).comprobarLogeado()) {
            AlmacenSesionImpl.getInstance(this).borrarDatos();

            // Mostramos mensaje de sesion cerrada.
            Toast toast = Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
            toast.show();

            // Cambiamos icono del boton iniciasesion y vaciamos nombre de usuario logeado.
            iniciarSesionButton.setBackgroundResource(R.drawable.iconologin);
            nombreUsuarioLogeado.setText("");
        } else {
            // DialogFragment.show() will take care of adding the fragment
            // in a transaction.  We also want to remove any currently showing
            // dialog, so make our own transaction and take care of that here.
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment prev = getFragmentManager().findFragmentByTag("dialogIniciaSesion");
            if (prev != null) {
                //ft.remove(prev);
                DialogFragment df = (DialogFragment) prev;
                df.dismiss();
            }
            ft.addToBackStack(null);

            // Create and show the dialog.
            DialogFragment newFragment = IniciarSesionFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putBoolean("desdeMenuInicio", true);
            newFragment.setArguments(bundle);
            newFragment.show(ft, "dialogIniciaSesion"); // Instanciamos el fragment con el tag dialogFragment
        }
    }

    // Método que lanza el dialogFragment referente a la ventana de iniciar sesión.
    public void showDialogRegistrarUsuario(View view) {
        // DialogFragment.show() will take care of adding the fragment
        // in a transaction.  We also want to remove any currently showing
        // dialog, so make our own transaction and take care of that here.
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialogIniciaSesion");
        if (prev != null) {
            //ft.remove(prev);
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = RegistrarUsuarioFragment.newInstance();
        newFragment.show(ft, "dialogIniciaSesion"); // Instanciamos el fragment con el tag dialogFragment
    }

    // Método que cerrará la ventana del dialog.
    // Sirve para cerrar el fragment de iniciar sesion y el de registrar, ya que ambos mismo tag.
    public void closeDialogIniciaSesion(View view) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialogIniciaSesion");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
        ft.addToBackStack(null);
    }

    // Metodo que cerrara la ventana dialog tras realizar la acción con exito (logear y registrar).
    public void closeDialog() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialogIniciaSesion");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
        ft.addToBackStack(null);
    }

    // Metodo que sera llamado para mandar la informacion del usuario parseado de un JSON al iniciar sesion.
    // Se hara alguna representacion fisica del estado del usuario.
    public void usuarioParseado(Usuario usuario) {
        Log.d("Usuario parseado:", usuario.getNombre() + ", " + usuario.getCorreo() + ", " + usuario.getClaveapi());

        // Almacenamos la informacion de sesion
        AlmacenSesionImpl.getInstance(this).salvarDatos(usuario.getNombre(), usuario.getCorreo(), usuario.getClaveapi(), usuario.getUltimaconexion());

        // Cambiamos el icono del imagebutton para evidencia grafica de sesion iniciada, y mostramos el nombre del usuario.
        iniciarSesionButton.setBackgroundResource(R.drawable.salirsesion);
        nombreUsuarioLogeado.setText(usuario.getNombre());
    }

    // Cuando la actividad vuelve a primer plano, compruebo que la sesión siga iniciada.
    @Override
    protected void onResume() {
        super.onResume();

        if(!AlmacenSesionImpl.getInstance(this).comprobarLogeado()) {
            // Cambiamos icono del boton iniciasesion y vaciamos nombre de usuario logeado.
            iniciarSesionButton.setBackgroundResource(R.drawable.iconologin);
            nombreUsuarioLogeado.setText("");
        }
    }
}
