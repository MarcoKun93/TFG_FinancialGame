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
import com.travijuu.numberpicker.library.NumberPicker;
import android.widget.Spinner;
import android.widget.TabHost;

import com.dam.financialgame.R;
import com.dam.financialgame.servicesImpl.AlmacenJuegoImpl;

import java.util.Vector;

public class IntroducirDatos extends AppCompatActivity {

    // Declaramos los ditintos elementos del layout
    Vector<NumberPicker> cantidades = new Vector<NumberPicker>();
    EditText efectivo;
    EditText financiacion;
    Vector<EditText> valorAcciones = new Vector<EditText>();
    EditText valorInmuebles;
    Spinner listadoJugadores;
    Vector<String> jugadoresParaActualizar = new Vector<String>();
    TabHost introducirValoresTabHost;

    // Declaramos variables estaticas
    static int VALOR_BONOS = 4000;
    static int VALOR_PENSIONES = 5000;

    // Declaramos la variable gestor de la base de datos
    AlmacenJuegoImpl almacen = new AlmacenJuegoImpl(this);

    // Declaramos un adaptador para guardar el vector que contiene los jugadores
    ArrayAdapter<String> adaptador;

    // Declaramos variable String del jugador actual del cual se mete datos
    String jugadorActual;

    // Para depuración
    private static final String TAG = IntroducirDatos.class.getSimpleName();  // Para depurar

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

        // Activamos el tabHost de introducir valores
        introducirValoresTabHost = (TabHost) findViewById(R.id.introducirValoresTabHost);
        introducirValoresTabHost.setup();
        TabHost.TabSpec tab1 = introducirValoresTabHost.newTabSpec("tab1");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec tab2 = introducirValoresTabHost.newTabSpec("tab2");
        tab1.setIndicator("VALORES");    //qué queremos que aparezca en las pestañas
        tab1.setContent(R.id.introducirValoresTab); //definimos el id de cada Tab (pestaña)
        tab2.setIndicator("CANTIDADES POR JUGADOR");
        tab2.setContent(R.id.indicarCantidadesTab);
        introducirValoresTabHost.addTab(tab1); //añadimos los tabs ya programados
        introducirValoresTabHost.addTab(tab2);

        efectivo = (EditText) findViewById(R.id.variable5);
        financiacion = (EditText) findViewById(R.id.variable6);

        cantidades.add((NumberPicker) findViewById(R.id.variable1_1));
        cantidades.add((NumberPicker) findViewById(R.id.variable1_2));
        cantidades.add((NumberPicker) findViewById(R.id.variable1_3));
        cantidades.add((NumberPicker) findViewById(R.id.variable2));
        cantidades.add((NumberPicker) findViewById(R.id.variable3));
        cantidades.add((NumberPicker) findViewById(R.id.variable4));

        for (int i = 0; i<cantidades.size(); i++) {
            cantidades.get(i).setMin(0);
            cantidades.get(i).setMax(30);
        }

        valorAcciones.add((EditText) findViewById(R.id.valorAccionesEuropeas));
        valorAcciones.add((EditText) findViewById(R.id.valorAccionesAmericanas));
        valorAcciones.add((EditText) findViewById(R.id.valorAccionesAsiaticas));

        valorInmuebles = (EditText) findViewById(R.id.valorInmuebles);

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
            for (int i = 0; i < valorAcciones.size(); i++){
                if (valorAcciones.get(i).getText() == null || valorAcciones.get(i).getText().toString().equals("")) {
                    valorAcciones.get(i).setText("0");
                }
            }
            if (valorInmuebles.getText() == null || valorInmuebles.getText().toString().equals("")) {
                valorInmuebles.setText("0");
            }
            if (efectivo.getText() == null || efectivo.getText().toString().equals("")) {
                efectivo.setText("0");
            }
            if (financiacion.getText() == null || financiacion.getText().toString().equals("")) {
                financiacion.setText("0");
            }


            // Introducimos los valores del jugador correspondiente
            almacen.setInfoJugador(jugadorActual, rondaActual, Integer.parseInt(valorAcciones.get(0).getText().toString()) * cantidades.get(0).getValue() +
                                                                            Integer.parseInt(valorAcciones.get(1).getText().toString()) * cantidades.get(1).getValue() +
                                                                                Integer.parseInt(valorAcciones.get(2).getText().toString()) * cantidades.get(2).getValue(),
                                                               Integer.parseInt(valorInmuebles.getText().toString()) * cantidades.get(3).getValue(),
                                                               VALOR_BONOS * cantidades.get(4).getValue(),
                                                               VALOR_PENSIONES * cantidades.get(5).getValue(),
                                                               Integer.parseInt(efectivo.getText().toString()),
                                                               Integer.parseInt(financiacion.getText().toString()));

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

            // Vaciamos los edit text para el siguiente jugador, también los NumberPicker los ponemos a cero
            efectivo.setText("0");
            financiacion.setText("0");
            for(int i = 0; i < cantidades.size(); i++) {
                cantidades.get(i).setValue(0);
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
