package com.dam.financialgame.controllers;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.dam.financialgame.R;
import com.dam.financialgame.services.AlmacenJuegoImpl;

import java.util.Vector;

public class IntroducirDatos extends AppCompatActivity {

    // Declaramos los ditintos elementos del layout
    Vector<EditText> variables = new Vector<EditText>();
    Spinner listadoJugadores;
    Vector<String> jugadoresParaActualizar = new Vector<String>();

    // Declaramos la variable gestor del la base de datos
    AlmacenJuegoImpl almacen = new AlmacenJuegoImpl(this);

    // Declaramos un adaptador para guardar el vector que contiene los jugadores
    ArrayAdapter<String> adaptador;

    // Declaramos variable String del jugador actual del cual se mete datos
    String jugadorActual;

    // Para depuración
    private static final String TAG = Escenario.class.getSimpleName();  // Para depurar

    // Declaramos una variable que guarde el modo del alertFragment
    int modoAlert;

    // Declaramos una variable que guarde la información recibida del número de ronda
    int rondaActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducir_datos);

        iniciarIntroducirDatos();
    }

    public void iniciarIntroducirDatos() {
        rondaActual = getIntent().getIntExtra("rondaActual", rondaActual);

        variables.add((EditText) findViewById(R.id.variable1));
        variables.add((EditText) findViewById(R.id.variable2));
        variables.add((EditText) findViewById(R.id.variable3));
        variables.add((EditText) findViewById(R.id.variable4));
        variables.add((EditText) findViewById(R.id.variable5));
        variables.add((EditText) findViewById(R.id.variable6));
        listadoJugadores = (Spinner) findViewById(R.id.listadoJugadores);
        jugadoresParaActualizar = almacen.getJugadores();

        listadoJugadores.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, almacen.getJugadores()));
        listadoJugadores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                jugadorActual = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //jugadorActual = almacen.getJugadores().get(0);  // Por defecto el jugador será el primero de la lista ordenada alfabeticamente
            }
        });

    }

    // Método que se llamará cuando hagamos clic al botón de aceptar datos
    public void aceptarDatos (View view) {
        /* Consistira en llamar a un alertshow con la informacion que vamos a escribir, si luego confirmamos en el alert,
           es cuando llamamos al metodo de almacen setInfoJugador con el nombre del jugador actual y los datos introducidos (convertidos a int), ese metodo
           se encarga de actualizar la puntuacion total del jugador de forma automatica */

        showAlert(1);
    }

    // Método que se llamará cuando hagamos clic en el botón de volver, volvemos a la actividad padre cerrando este
    public void volver (View view) {
        /* Cuando pulsamos volver también debería salir un alert en el caso de que queden jugadores sin introducir datos,
           se puede conseguir si yo, al darle aceptar, elimino de un array ese jugador, si al darle al botón volver ese array
           no está vacío, pues indico los jugadores que aún no se han actualizado los datos */
        Log.d(TAG, "Tamaño del vector de jugadores para actualizar: "+jugadoresParaActualizar.size()); // Para depurar

        if (jugadoresParaActualizar.size() != 0){
            showAlert(2);
        } else {
            finish();
        }
    }

    // Método que mostrará el alertFragment cuando le dé al botón para guardar información pidiendo que se confirmen esos datos (modo 1) ó para volver en caso de que haya jugadores sin actualizar (modo 2)
    public void showAlert(int modo) {
        /* Deberá diferenciar el modo, y en ambos enviar al alertFragment un string con la información deseada:
           modo 1: datos introducidos
           modo 2: jugadores aun no actualizados */
        String mensajeMostrado;

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("AlertFragment");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        if (modo == 1){
            modoAlert = modo;
            mensajeMostrado = "¿Seguro que los datos introducidos son los correctos?";
            DialogFragment newFragment = MyAlertDialogFragment.newInstance(mensajeMostrado);
            //ft.replace(android.R.id.content, newFragment, MyAlertDialogFragment.TAG);
            newFragment.show(ft, "AlertFragment");  // Importante que no nos equivoquemos de TAG, ya que lo utilizamos pa buscarlo y eliminarlo si estuviese abierto
        } else if (modo == 2){
            modoAlert = modo;
            mensajeMostrado = "Quedan los siguientes jugadores para introducir sus datos: ";

            for (int i = 0; i<jugadoresParaActualizar.size(); i++){
                mensajeMostrado = mensajeMostrado + jugadoresParaActualizar.get(i) + "  ";
            }

            DialogFragment newFragment = MyAlertDialogFragment.newInstance(mensajeMostrado);
            newFragment.show(ft, "AlertFragment");  // Importante que no nos equivoquemos de TAG, ya que lo utilizamos pa buscarlo y eliminarlo si estuviese abierto
        }
    }

    // Método que se ejecutarán según el botón seleccionado del dialogFragment aparecido
    public void doPositiveClick(View view) {
        Log.i("FragmentAlertDialog", "Positive click!");

        // Eliminamos el dialog fragment is lo hay
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("AlertFragment");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
        ft.addToBackStack(null);

        if (modoAlert == 1){
            // Comprobamos que las variables, si no hay nada escrito, el valor por defecto sea 0
            for (int i = 0; i < variables.size(); i++){
                if (variables.get(i).getText() == null || variables.get(i).getText().toString().equals("")) {
                    variables.get(i).setText("0");
                }
            }

            // Introducimos los valores del jugador correspondiente
            almacen.setInfoJugador(jugadorActual, rondaActual, Integer.parseInt(variables.get(0).getText().toString()), Integer.parseInt(variables.get(1).getText().toString()), Integer.parseInt(variables.get(2).getText().toString()), Integer.parseInt(variables.get(3).getText().toString()), Integer.parseInt(variables.get(4).getText().toString()), Integer.parseInt(variables.get(5).getText().toString()));

            // Eliminamos del vector de jugadores para actualizar, el que tenga el mismo nombre del que hemos actualizados
            for (int i = 0; i < jugadoresParaActualizar.size(); i++){   //DEBEMOS QUITARLO DE LA LISTA!!!
                if(jugadoresParaActualizar.get(i).equals(jugadorActual))
                    jugadoresParaActualizar.remove(i);
            }

            // Actualizamos el Spinner de listadoJugadores con los actuales, seleccionamos el primero. Si no queda ninguno por actualizar, cerramos la actividad automaticamente
            if (jugadoresParaActualizar.size() == 0) {
                // Cerramos la actividad
                finish();
            } else {
                listadoJugadores.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, jugadoresParaActualizar));
                listadoJugadores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        jugadorActual = parent.getItemAtPosition(position).toString();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        //jugadorActual = almacen.getJugadores().get(0);  // Por defecto el jugador será el primero de la lista ordenada alfabeticamente
                    }
                });
            }

            // Vaciamos los edit text para el siguiente jugador
            for (int i = 0; i < variables.size(); i++){
                variables.get(i).setText("");
            }
        } else if(modoAlert == 2){
            finish();
        }
    }

    public void doNegativeClick(View view) {
        Log.i("FragmentAlertDialog", "Negative click!");

        // Eliminamos el dialog fragment is lo hay
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("AlertFragment");
        if (prev != null) {
            DialogFragment df = (DialogFragment) prev;
            df.dismiss();
        }
        ft.addToBackStack(null);
    }
}
