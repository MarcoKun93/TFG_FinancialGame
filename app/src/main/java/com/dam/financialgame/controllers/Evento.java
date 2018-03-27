package com.dam.financialgame.controllers;

import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dam.financialgame.R;

import java.util.Vector;

// Clase dialog fragment que mostrará el evento correspondiente (una carta) cuando le invoquen
public class Evento extends DialogFragment{

    TextView nombreCarta;
    ImageView imagenTipo;
    TextView tipoCarta;
    TextView atributo1;
    TextView atributo2;
    TextView atributo3;
    TextView atributo4;
    TextView atributo5;
    int eventos;
    int rondaActual;
    ImageButton botonCerrarVentana;
    // Numero de eventos que tiene una ronda
    public final int eventosPorRonda = 4;

    // Recibe como parámetro el vector con la información de la carta evento, y el numero de eventos por si cuando se destruya ese dialog hay que lanzar la actividad de instroducir datos
    static Evento newInstance(Vector<String> result, int eventos, int rondaActual) {
        Evento f = new Evento();

        // Si el dialogFragment recibe parámetros, aquí se recogen
        Bundle args = new Bundle();
        args.putString("nombre", result.get(0));
        args.putString("tipo", result.get(1));
        args.putString("atributo1", result.get(2));
        args.putString("atributo2", result.get(3));
        args.putString("atributo3", result.get(4));
        args.putString("atributo4", result.get(5));
        args.putString("atributo5", result.get(6));
        args.putInt("eventos", eventos);
        args.putInt("rondaActual", rondaActual);
        f.setArguments(args);

        return f;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        // Indicamos el estilo que seguirá el dialog fragment, uno creado personalmente y que encontraremos en la carpeta de "values"
        // Dentro de ese recurso, cambiamos el background de la ventana del dialog, por un color que hemos creado y registrado dentro del otro archivod e recurso "colors"
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialogFragment);
    }

    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_evento, contenedor, false);
        // Buscamos los view corespondientes generados en la vista
        nombreCarta = (TextView) vista.findViewById(R.id.nombreCarta);
        imagenTipo = (ImageView) vista.findViewById(R.id.imagenCarta);
        tipoCarta = (TextView) vista.findViewById(R.id.tipoCarta);
        atributo1 = (TextView) vista.findViewById(R.id.atributo1);
        atributo2 = (TextView) vista.findViewById(R.id.atributo2);
        atributo3 = (TextView) vista.findViewById(R.id.atributo3);
        atributo4 = (TextView) vista.findViewById(R.id.atributo4);
        atributo5 = (TextView) vista.findViewById(R.id.atributo5);
        botonCerrarVentana = (ImageButton) vista.findViewById(R.id.botonCerrarVentana);
        inicializarEvento();

        return vista;
    }

    private void inicializarEvento() {
        String tipoParaImagen;   // Lo utilizaremos varias veces para insertar la imagen oportuna

        // Rellenamos los views correspondientes para mostrar la carta elegida
        nombreCarta.setText(getArguments().getString("nombre"));
        tipoCarta.setText(getArguments().getString("tipo"));

        // Guardamos el numero de eventos pasados por parámetro y la ronda actual (se enviará como parámetro para la actividad de introducir datos)
        eventos = getArguments().getInt("eventos");
        rondaActual = getArguments().getInt("rondaActual");

        // Obtenemos el nombre y vamos comparando con las imágenes y la insertamos en el ImageView
        tipoParaImagen = getArguments().getString("nombre");
        switch (tipoParaImagen) {
            case "Variaciones mercado financiero":
                imagenTipo.setImageResource(R.drawable.stock);
                break;
            case "Desempleo temporal":
                imagenTipo.setImageResource(R.drawable.no_money);
                break;
            case "Reformas en el hogar":
                imagenTipo.setImageResource(R.drawable.new_home);
                break;
            case "Reparto dividendos":
                imagenTipo.setImageResource(R.drawable.dividends);
                break;
            case "Gastos universitarios":
                imagenTipo.setImageResource(R.drawable.books);
                break;
            case "Adquisicion de vehiculos":
                imagenTipo.setImageResource(R.drawable.car);
                break;
            case "Viaje familiar":
                imagenTipo.setImageResource(R.drawable.greece);
                break;
            default:
                imagenTipo.setImageResource(R.drawable.stock);
                break;
        }

        // Metemos los atributos en sus TextView
        if (getArguments().getString("atributo1") != "0")
            atributo1.setText("->  "+getArguments().getString("atributo1"));
        if (getArguments().getString("atributo2") != "0")
            atributo2.setText("->  "+getArguments().getString("atributo2"));
        if (getArguments().getString("atributo3") != "0")
            atributo3.setText("->  "+getArguments().getString("atributo3"));
        if (getArguments().getString("atributo4") != "0")
            atributo4.setText("->  "+getArguments().getString("atributo4"));
        if (getArguments().getString("atributo5") != "0")
            atributo5.setText("->  "+getArguments().getString("atributo5"));
    }

    // Si el dialogFragment es destruido, vemos si hay que lanzar la actividad de introducir datos
    @Override
    public void onDestroy() {
        if (eventos%eventosPorRonda == 0){    // Si el resto es cero, cuando el fragment es destruido lanzamos la actividad de introducir datos
            Intent intent = new Intent(getActivity(), IntroducirDatos.class);
            intent.putExtra("rondaActual", rondaActual);
            startActivityForResult(intent, 0);
        } else {
            // Reiniciamos el temporizador de la actividad contenedora del fragment
            ((Escenario)getActivity()).reiniciarTemporizador();
        }
        super.onDestroy();
    }
}
