package com.dam.financialgame.controllers.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.dam.financialgame.R;
import com.dam.financialgame.servicesImpl.AlmacenJuegoImpl;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Vector;

public class PieGraph extends Fragment {

    PieChart pieChart;
    Spinner listaJugadores;
    TextView puntuacionJugador;
    AlmacenJuegoImpl almacen = AlmacenJuegoImpl.getInstance(getActivity());

    // Creamos un vector de colores para la representación gráfica
    Vector<Integer> colores = new Vector<Integer>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Aquí ponemos el estilo que seguirá el fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_pie_graph, container, false);

        // Buscamos en el layout
        pieChart = (PieChart) vista.findViewById(R.id.pieChart);
        listaJugadores = (Spinner) vista.findViewById(R.id.listadoJugadores);
        puntuacionJugador = (TextView) vista.findViewById(R.id.puntuacionJugador);

        // Definimos algunos atributos
        pieChart.setHoleRadius(40f);
        pieChart.setRotationEnabled(true);
        pieChart.animateXY(1500, 1500);

        // Introducimos los colores en el vector, 6 como máximo y mínimo ya que el número de jugadores máximos son 6
        colores.add(Color.GRAY);   // Los colores por defecto tienen un identificador tipo Integer
        colores.add(Color.CYAN);
        colores.add(Color.GREEN);
        colores.add(Color.YELLOW);
        colores.add(Color.MAGENTA);
        colores.add(Color.RED);

        return vista;
    }

    // No dibujaremos la gráfica hasta que no es´te la actividad contenedora creada en su totalidad
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Ponemos el conjunto de jugadores y un listener para que se ejecute el método recargarPieChart
        listaJugadores.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, almacen.getJugadores()));
        listaJugadores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                recargarPieChart(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    // Método que será llamado cuando cambiemos el jugador en el spinner, de forma que se actualicen los datos de forma dinámica y se representa la gráfica deseada
    public void recargarPieChart (String nombreJugador) {
        Vector<Integer> valoresJugador = almacen.getPuntuacionAtributosJugador(nombreJugador);

        // Actualizamos el textView con la puntuacion total del jugador indicado
        puntuacionJugador.setText(valoresJugador.get(0).toString());

        // Creamos los puntos de datos y creamos gráfica
        // Creamos una lista para los valores Y, en %
        ArrayList<PieEntry> yAxes = new ArrayList<PieEntry>();
        yAxes.add(new PieEntry(valoresJugador.get(1), "Acciones"));
        yAxes.add(new PieEntry(valoresJugador.get(2), "Inmuebles"));
        yAxes.add(new PieEntry(valoresJugador.get(3), "Estado"));
        yAxes.add(new PieEntry(valoresJugador.get(4), "Pensiones"));
        yAxes.add(new PieEntry(valoresJugador.get(5), "Efectivo"));
        yAxes.add(new PieEntry(valoresJugador.get(6), "Financiacion"));

 		// Seteamos los valores de Y y los colores
        PieDataSet set1 = new PieDataSet(yAxes, "");
        set1.setSliceSpace(3f);
        set1.setColors(colores);

		/*seteamos los valores de X*/
        PieData data = new PieData(set1);
        pieChart.setData(data);
        pieChart.highlightValues(null);
        pieChart.invalidate();
        pieChart.setDescription(null);
        pieChart.setDrawSliceText(false);
        pieChart.animateXY(1500, 1500);
    }
}
