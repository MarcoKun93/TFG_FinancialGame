package com.dam.financialgame.controllers;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;
import android.view.Gravity;

import com.dam.financialgame.R;
import com.dam.financialgame.servicesImpl.AlmacenJuegoImpl;
import com.dam.financialgame.servicesImpl.AlmacenSesionImpl;

import java.util.Vector;

// En esta clase solo preparamos el escenario de juego, y lanzamos el fragment que ejecuta la partida
public class InicializarPartida extends AppCompatActivity {

    EditText nombreJugador;
    AlmacenJuegoImpl almacen = AlmacenJuegoImpl.getInstance(this);
    Spinner rondas;
    Spinner jugadoresRegistrados;
    int numRondas = 0;
    static int NUM_JUGADORES_MAX = 6;
    LinearLayout notaUsoSemilla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciarlizar_partida);
        almacen.vaciarTablaJugadores();
        inicializar();
    }

    private void inicializar() {
        nombreJugador = (EditText) findViewById(R.id.nombreJugador);
        rondas = (Spinner) findViewById(R.id.numeroRondas);
        jugadoresRegistrados = (Spinner) findViewById(R.id.JugadoresRegistrados);
        notaUsoSemilla = (LinearLayout) findViewById(R.id.notaUsoSemilla);

        // Ponemos las rondas totales disponible, y un listener para guardar el dato. Una ronda está compuesto por 5 eventos
        String[] rondasDisponibles = {"1","2","5","8"};
        rondas.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, rondasDisponibles));
        rondas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                numRondas = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                numRondas = 1;
            }
        });

        // Comprobamos si hemos elegido una semilla para jugar con ella, antes ver si usuario logeado. En caso positivo mostramos el mensaje.
        if(AlmacenSesionImpl.getInstance(this).comprobarLogeado() && AlmacenSesionImpl.getInstance(this).obtenerSemillaId() != 0) {
            notaUsoSemilla.setVisibility(View.VISIBLE);
        } else {
            notaUsoSemilla.setVisibility(View.INVISIBLE);
        }
    }

    public void añadirJugador(View view) {
        // Primero comprobamos que no se ha superado el numero maximo de jugadores y que el nombre introducido no sea vacío
        if ((almacen.getInfoJugadores().size() < NUM_JUGADORES_MAX) && (!nombreJugador.getText().toString().equals(""))) {
            almacen.setInfoJugador(nombreJugador.getText().toString(), 0, 0, 0, 0, 0, 0, 0);
            Toast toast = Toast.makeText(getApplicationContext(), "Añadido jugador: " + nombreJugador.getText().toString(), Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
            toast.show();
            nombreJugador.setText("");  // Reseteamos el editText

            // Añadimos jugador registrado en el spinner para que el jugador sea consciente de los nombres introducidos
            jugadoresRegistrados.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, almacen.getJugadores()));

        } else {
            Toast toast = Toast.makeText(getApplicationContext(), "Operación no permitida", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
            toast.show();
        }

    }

   public void iniciarPartida(View view) {
        // Lanzamos el fragment que ejecuta el escenario de la partida pero antes comprobamos que haya como minimo un jugador registrado
       if (almacen.getInfoJugadores().size() > 0) {
           Intent intent = new Intent(view.getContext(), Escenario.class);
           intent.putExtra("numRondas", numRondas);
           startActivityForResult(intent, 0);
           finish();
       } else {
           Toast toast = Toast.makeText(getApplicationContext(), "No hay jugadores registrados", Toast.LENGTH_LONG);
           toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
           toast.show();
       }
    }

    // Cuando volvemos a la actividad principal, vaciamos el spinner de jugadores registrados y vaciamos las tablas que guardarán información dinámica de la partida
    @Override
    protected  void onResume() {
        super.onResume();
        almacen.vaciarTablaJugadores();
        jugadoresRegistrados.setAdapter(new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, new Vector<String>()));
    }
}
