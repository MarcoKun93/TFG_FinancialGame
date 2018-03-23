package com.dam.financialgame;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dam.financialgame.services.AlmacenJuegoImpl;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;
import java.util.Vector;

public class LineGraph extends Fragment {

    LineChart lineChart;
    int numRondasFinJuego = 0;

    // Creamos un vector de vectores Integer para almacenar la informacion de puntuaciones de un jugador recibida de la BBDD
    Vector<Vector<Integer>> historialJugador = new Vector<Vector<Integer>>();

    // Creamos vector de String donde guardaremos los nombre de los jugadores
    Vector<String> nombresJugadores = new Vector<String>();

    // Creamos un ArrayList para el eje y (el cual almacenará un array de longitud igual al numero de rondas donde almacena los distintos valores de un mismo jugador)
    ArrayList<ArrayList<Entry>> yAxes = new ArrayList<ArrayList<Entry>>();  // Contiene el conjunto de valores de cada línea de cada jugador. ArrayList porque el número de jugadores varía según la partida

    // Creamos un vector de datos de lineas cada una correspondiente a cada jugador
    ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

    // Creamos un vector de colores para la representación gráfica
    Vector<Integer> colores = new Vector<Integer>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Aquí ponemos el estilo que seguirá el fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_line_graph, container, false);

        // Buscamos la grafica del layout
        lineChart = (LineChart) vista.findViewById(R.id.lineChart);


        // Introducimos los colores en el vector, 6 como máximo y mínimo ya que el número de jugadores máximos son 6
        colores.add(Color.BLACK);   // Los colores por defecto tienen un identificador tipo Integer
        colores.add(Color.BLUE);
        colores.add(Color.GREEN);
        colores.add(Color.YELLOW);
        colores.add(Color.MAGENTA);
        colores.add(Color.RED);

        return vista;
    }

    // No dibujaremos la gráfica hasta que no esté la actividad contenedora creada en su totalidad
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Obtenemos una referencia de la BBDD
        AlmacenJuegoImpl almacen = new AlmacenJuegoImpl(getActivity());

        // Obtenemos el conjunto de nombres de los jugadores
        nombresJugadores = almacen.getJugadores();

        // Empezamos con los bucles para crear los yAxes y LineDataSet de cada jugador
        for (int i = 0; i < nombresJugadores.size(); i++) {
            historialJugador = almacen.getHistorialJugador(nombresJugadores.get(i));

            yAxes.add(new ArrayList<Entry>());
            yAxes.get(i).add(new Entry(0, 0));  // Los jugadores empezarán con una puntuación de cero
            for (int j = 0; j < historialJugador.size(); j++){
                yAxes.get(i).add(new Entry(historialJugador.get(j).get(0), historialJugador.get(j).get(1)));    // Posicion 0 es la ronda y posicion 1 es la puntuacion obtenida en esa ronda
            }
            LineDataSet dataset = new LineDataSet(yAxes.get(i),nombresJugadores.get(i));
            dataset.setColor(colores.get(i));
            lineDataSets.add(dataset);
            lineDataSets.get(i).setDrawFilled(true);
        }

        // Introducimos las líneas de datos en la gráfica y representamos con las opciones deseadas
        LineData data = new LineData(lineDataSets);
        lineChart.setData(data);
        lineChart.setDrawGridBackground(false);
        lineChart.getAxisLeft().setGranularity(1f);
        lineChart.getXAxis().setGranularity(1f);
        lineChart.getAxisLeft().setGranularityEnabled(true);
        lineChart.getXAxis().setGranularityEnabled(true);
        lineChart.getXAxis().setAxisMinimum(0f);

        lineChart.getAxisLeft().setDrawLabels(false); // no axis labels
        lineChart.getAxisLeft().setDrawAxisLine(false); // no axis line
        lineChart.getAxisLeft().setDrawGridLines(false); // no grid lines
        lineChart.getAxisLeft().setDrawZeroLine(true); // draw a zero line
        lineChart.getAxisRight().setEnabled(false); // no right axis

        numRondasFinJuego = GraficasJugadores.obtenerNumRondasFinJuego();   // Obtengo el valor del número de rondas totales de la partida
        Log.i("LineGraph", "El numero de rondas totales es de " + numRondasFinJuego);
        lineChart.getXAxis().setAxisMaximum(numRondasFinJuego);
        lineChart.getAxisLeft().setAxisMinimum(0f);
        lineChart.invalidate();
    }
}
