package com.dam.financialgame.servicesImpl;

import android.app.Activity;
import android.app.Application;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.dam.financialgame.controllers.Comunidad;
import com.dam.financialgame.services.PartidaService;
import com.dam.financialgame.threads.VolleyApplication;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

// La clase hará uso de un singleton para no tener que instanciarla cada vez que hagamos uso de ella.
// Por eso, cuando queremos obtener la instancia, comprobamos antes que no se haya instanciado ya.
public class PartidaServiceImpl implements PartidaService {

    private Comunidad comunidad;
    private static PartidaServiceImpl partidaService;
    final String URL_PARTIDAS_SUBIDAS = "https://tfgserviceses.000webhostapp.com/tablaprueba/lista.php";
    final String URL_SUBIR_PARTIDAS = "https://tfgserviceses.000webhostapp.com/tablaprueba/nueva.php";

    public synchronized static PartidaServiceImpl getInstance() {
        if (partidaService == null) {
            synchronized(PartidaServiceImpl.class) {
                if (partidaService == null)
                    partidaService = new PartidaServiceImpl();
            }
        }

        // Return the instance
        return partidaService;
    }

    // Una petición GET
    public void obtenerPartidasSubidas(Comunidad activity) {
        comunidad = activity;
        StringRequest getRequest = new StringRequest(Request.Method.GET, URL_PARTIDAS_SUBIDAS,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // Display response
                        Log.d("Response: ", response.toString());
                        comunidad.setListaPartidas(response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR Response:", error.toString());
                        comunidad.setListaPartidas(error.toString());
                    }
                });


        /** Si recibimos un Json **
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, URL_PARTIDAS_SUBIDAS, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display response
                        Log.d("Response: ", response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR Response:", error.toString());
                    }
                }); ****/


        // Add it to the RequestQueue
        VolleyApplication.getInstance().getRequestQueue().add(getRequest);
    }

    // Una petición POST
    public void subirPartidas(final String puntuacion, final String nombre, final Activity activity) {
        final Date date = new Date();
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String fecha = dateFormat.format(date);
        StringRequest postRequest = new StringRequest(Request.Method.POST, URL_SUBIR_PARTIDAS,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // Response
                        Log.d("Response: ", response);
                        Toast toast = Toast.makeText(activity.getApplicationContext(), "Información subida", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
                        toast.show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Error
                        Log.d("Error response: ", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("puntuacion", puntuacion);  // Enviamos la puntuacion
                params.put("nombre", nombre);  // Enviamos el nombre
                params.put("fecha", fecha);  // enviamos fecha formato yyyy-mm-dd

                return params;
            }
        };

        // Add it to the RequestQueue
        VolleyApplication.getInstance().getRequestQueue().add(postRequest);
    }
}
