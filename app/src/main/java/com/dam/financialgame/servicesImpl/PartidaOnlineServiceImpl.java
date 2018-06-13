package com.dam.financialgame.servicesImpl;

import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dam.financialgame.R;
import com.dam.financialgame.controllers.activities.PrincipalPartidasOnline;
import com.dam.financialgame.model.PartidaOnline;
import com.dam.financialgame.services.PartidaOnlineService;
import com.dam.financialgame.threads.VolleyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PartidaOnlineServiceImpl implements PartidaOnlineService {

    private static PartidaOnlineServiceImpl partidaOnlineService;

    public synchronized static PartidaOnlineServiceImpl getInstance() {
        if (partidaOnlineService == null) {
            synchronized(PartidaOnlineServiceImpl.class) {
                if (partidaOnlineService == null)
                    partidaOnlineService = new PartidaOnlineServiceImpl();
            }
        }

        // Return the instance
        return partidaOnlineService;
    }

    public void getPartidasOnline(final PrincipalPartidasOnline activity) {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, activity.getResources().getString(R.string.url_get_partidas_online), null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display response
                        Log.d("Response: ", response.toString());

                        activity.recargarListadoPartidasOnline(parseJson(response));
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

                params.put("authorization", AlmacenSesionImpl.getInstance(activity.getApplicationContext()).obtenerUsuarioLogeado().getClaveapi());

                return params;
            }
        };

        // Add it to the RequestQueue
        VolleyApplication.getInstance().getRequestQueue().add(getRequest);
    }

    public void crearPartidaOnline(int numrondas, int numjugadoresmax, final PrincipalPartidasOnline activity) {
        final Date date = new Date();
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String fecha = dateFormat.format(date);

        Map<String, String> params = new HashMap();
        params.put("nombreanfitrion", AlmacenSesionImpl.getInstance(activity).obtenerUsuarioLogeado().getNombre());
        params.put("fecha", fecha);
        params.put("numrondas", Integer.toString(numrondas));
        params.put("numjugadores", "0");
        params.put("numjugadoresmax", Integer.toString(numjugadoresmax));
        params.put("nombreganador", "");
        params.put("puntuacionganador", "0");
        params.put("idEvento", "0");
        params.put("temporizador", "0");
        params.put("estado", "0");

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, activity.getResources().getString(R.string.url_crear_partida_online), parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO: handle success
                        Log.d("Response: ", response.toString());

                        Toast toast = Toast.makeText(activity.getApplicationContext(), "Partida online creada", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
                        toast.show();

                        activity.irPartidaInicializada();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
                Log.d("ERROR Response:", error.toString());

                Toast toast = Toast.makeText(activity.getApplicationContext(), "Error al crear partida", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
                toast.show();
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
        VolleyApplication.getInstance().getRequestQueue().add(jsonRequest);
    }

    public void setEstadoPartidaOnline(final String nuevoEstado, final Activity activity) {
        Map<String, String> params = new HashMap();
        params.put("estado", nuevoEstado);
        params.put("idPartidaOnline", Integer.toString(AlmacenSesionImpl.getInstance(activity).getIdPartdaOnlineAlmacenado()));

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, activity.getResources().getString(R.string.url_cambiar_estado_partida), parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO: handle success
                        Log.d("Response: ", response.toString());

                        Toast toast = Toast.makeText(activity.getApplicationContext(), "Estado partida online cambiado a "+nuevoEstado, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
                        toast.show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
                Log.d("ERROR Response:", error.toString());

                Toast toast = Toast.makeText(activity.getApplicationContext(), "Error al cambiar estado", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
                toast.show();
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
        VolleyApplication.getInstance().getRequestQueue().add(jsonRequest);
    }

    public void setNumeroJugadores(int numJugadores, final Activity activity) {
        Map<String, String> params = new HashMap();
        params.put("numJugadores", Integer.toString(numJugadores));
        params.put("idPartidaOnline", Integer.toString(AlmacenSesionImpl.getInstance(activity).getIdPartdaOnlineAlmacenado()));

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, activity.getResources().getString(R.string.url_cambiar_numero_jugadores), parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO: handle success
                        Log.d("Response: ", response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
                Log.d("ERROR Response:", error.toString());

                Toast toast = Toast.makeText(activity.getApplicationContext(), "Error al cambiar estado", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
                toast.show();
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
        VolleyApplication.getInstance().getRequestQueue().add(jsonRequest);
    }

    public void eliminarPartidaOnline(final Activity activity) {
        JsonObjectRequest deleteRequest = new JsonObjectRequest(Request.Method.DELETE, activity.getResources().getString(R.string.url_borrar_partida_online), null,
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
                }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();

                params.put("authorization",AlmacenSesionImpl.getInstance(activity.getApplicationContext()).obtenerUsuarioLogeado().getClaveapi());

                return params;
            }
        };

        // Add it to the RequestQueue
        VolleyApplication.getInstance().getRequestQueue().add(deleteRequest);
    }

    /** Metodos para parsear la informacion de partida **/

    // Obtenemos las partidas del servidor correspondientes al usuario logeado, es un array. Debido a la estructura del
    // JSON, debemos indicar que cogemos los valores del objeto datos del JSON.
    private ArrayList<PartidaOnline> parseJson(JSONObject jsonObject){
        // Variables locales
        ArrayList<PartidaOnline> partidas = new ArrayList();
        // Creamos un array para el resultado devuelto.
        JSONArray jsonArray = null;

        try {
            jsonArray = jsonObject.getJSONArray("datos");

            for(int i=0; i<jsonArray.length(); i++) {

                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);

                    PartidaOnline partida = new PartidaOnline(
                            objeto.getInt("idPartidaOnline"),
                            objeto.getString("nombreanfitrion"),
                            objeto.getString("fecha"),
                            objeto.getInt("numrondas"),
                            objeto.getInt("numjugadores"),
                            objeto.getInt("numjugadoresmax"),
                            objeto.getString("nombreganador"),
                            objeto.getInt("puntuacionganador"),
                            objeto.getInt("idEvento"),
                            objeto.getInt("temporizador"),
                            objeto.getInt("estado"));

                    partidas.add(partida);

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

