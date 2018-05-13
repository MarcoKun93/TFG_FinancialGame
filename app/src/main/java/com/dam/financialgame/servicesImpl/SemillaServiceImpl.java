package com.dam.financialgame.servicesImpl;

import android.app.Activity;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dam.financialgame.R;
import com.dam.financialgame.controllers.Comunidad;
import com.dam.financialgame.controllers.IntroducirDatos;
import com.dam.financialgame.model.Semilla;
import com.dam.financialgame.services.SemillaService;
import com.dam.financialgame.threads.VolleyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// La clase hará uso de un singleton para no tener que instanciarla cada vez que hagamos uso de ella.
// Por eso, cuando queremos obtener la instancia, comprobamos antes que no se haya instanciado ya.
// IMPORTANTE: Para acceder a los servicios de semilla, debemos ofrecer la claveapi del usuario.
public class SemillaServiceImpl implements SemillaService {

    private static SemillaServiceImpl semillaService;

    public synchronized static SemillaServiceImpl getInstance() {
        if (semillaService == null) {
            synchronized(SemillaServiceImpl.class) {
                if (semillaService == null)
                    semillaService = new SemillaServiceImpl();
            }
        }

        // Return the instance
        return semillaService;
    }

    // Una petición GET, obtenemos la url del archivo de recursos strings.xml
    public void obtenerSemillas(final Comunidad activity) {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, activity.getResources().getString(R.string.url_semilla), null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display response
                        Log.d("Response: ", response.toString());

                        // Debemos parsear el contenido recibido del JSON.
                        if(!parseJson(response).isEmpty())
                            activity.callbackListadoSemillas(parseJson(response));
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR Response:", error.toString());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("authorization",AlmacenSesionImpl.getInstance(activity.getApplicationContext()).obtenerUsuarioLogeado().getClaveapi());

                return params;
            }
        };

        // Add it to the RequestQueue
        VolleyApplication.getInstance().getRequestQueue().add(getRequest);
    }

    // Una petición GET, obtenemos la url del archivo de recursos strings.xml
    // En esta ocasion, recibimos el idSemilla y lo acoplamos en la url.
    public void obtenerSemillas(final Comunidad activity, final int idSemilla) {
        String url = activity.getResources().getString(R.string.url_semilla) + "/" + Integer.toString(idSemilla);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display response
                        Log.d("Response: ", response.toString());

                        // Debemos parsear el contenido recibido del JSON.
                        if (!parseJson(response).isEmpty())
                            activity.callbackSemillaSeleccionada(parseJson(response));
                    }

                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR Response:", error.toString());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("authorization",AlmacenSesionImpl.getInstance(activity.getApplicationContext()).obtenerUsuarioLogeado().getClaveapi());

                return params;
            }
        };

        // Add it to the RequestQueue
        VolleyApplication.getInstance().getRequestQueue().add(getRequest);
    }

    public void obtenerSemilla(final IntroducirDatos activity, int idSemilla) {
        String url = activity.getResources().getString(R.string.url_semilla) + "/" + Integer.toString(idSemilla);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display response
                        Log.d("Response: ", response.toString());

                        // Debemos parsear el contenido recibido del JSON.
                        if (!parseJson(response).isEmpty())
                            activity.callBackCompruebaSemilla(parseJson(response).get(0));
                    }

                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("ERROR Response:", error.toString());
                    }
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("authorization",AlmacenSesionImpl.getInstance(activity.getApplicationContext()).obtenerUsuarioLogeado().getClaveapi());

                return params;
            }
        };

        // Add it to the RequestQueue
        VolleyApplication.getInstance().getRequestQueue().add(getRequest);
    }

    /** Metodos para parsear la informacion de partida **/

    // Obtenemos las semillas del servidor. Debido a la estructura del
    // JSON, debemos indicar que cogemos los valores del objeto datos del JSON.
    private ArrayList<Semilla> parseJson(JSONObject jsonObject){
        // Variables locales
        ArrayList<Semilla> partidas = new ArrayList();
        // Creamos un array para el resultado devuelto.
        JSONArray jsonArray = null;

        try {
            jsonArray = jsonObject.getJSONArray("datos");

            for(int i=0; i<jsonArray.length(); i++) {

                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);

                    Semilla semilla = new Semilla(
                            objeto.getInt("idSemilla"),
                            objeto.getString("titulo"),
                            objeto.getString("descripcion"),
                            objeto.getString("efecto"),
                            objeto.getInt("valorbonos"),
                            objeto.getInt("valorpensiones"));

                    partidas.add(semilla);

                } catch (JSONException e) {
                    Log.d("Parseando:", "Error de parsing: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return partidas;
    }
}
