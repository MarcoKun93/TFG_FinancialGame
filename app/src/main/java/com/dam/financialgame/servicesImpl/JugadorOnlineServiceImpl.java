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
import com.dam.financialgame.controllers.activities.EscenarioOnline;
import com.dam.financialgame.controllers.activities.PartidaOnlineInicializada;
import com.dam.financialgame.controllers.activities.PrincipalPartidasOnline;
import com.dam.financialgame.model.JugadorOnline;
import com.dam.financialgame.services.JugadorOnlineService;
import com.dam.financialgame.threads.VolleyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JugadorOnlineServiceImpl implements JugadorOnlineService {

    private static JugadorOnlineServiceImpl jugadorOnlineService;

    public synchronized static JugadorOnlineServiceImpl getInstance() {
        if (jugadorOnlineService == null) {
            synchronized(PartidaOnlineServiceImpl.class) {
                if (jugadorOnlineService == null)
                    jugadorOnlineService = new JugadorOnlineServiceImpl();
            }
        }

        // Return the instance
        return jugadorOnlineService;
    }

    public void postJugadorOnline(int idPartidaOnline, final PrincipalPartidasOnline activity) {
        Map<String, String> params = new HashMap();
        params.put("estado", "0");
        params.put("isAnfitrion", "0");
        params.put("idPartidaOnline", Integer.toString(idPartidaOnline));

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, activity.getResources().getString(R.string.url_crear_jugador_online), parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO: handle success
                        Log.d("Response: ", response.toString());

                        Toast toast = Toast.makeText(activity.getApplicationContext(), "Te has unido a la partida", Toast.LENGTH_LONG);
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

                Toast toast = Toast.makeText(activity.getApplicationContext(), "Error", Toast.LENGTH_LONG);
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

    public void getJugadoresUnidos(final PartidaOnlineInicializada activity) {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, activity.getResources().getString(R.string.url_obtener_jugadores_unidos), null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display response
                        Log.d("Response: ", response.toString());

                        // Pasamos al metodo correspondiente el array de jugadores online de la partida
                        activity.muestraDatosJugadoresOnline(parseJson(response));
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

    // Sobreescribimos el metodo anterior
    public void getJugadoresUnidos(final EscenarioOnline activity) {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, activity.getResources().getString(R.string.url_obtener_jugadores_unidos), null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display response
                        Log.d("Response: ", response.toString());

                        // Pasamos al metodo correspondiente el array de jugadores online de la partida
                        activity.muestraDatosJugadoresOnline(parseJson(response));
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

    public void getIdsOnline(final Activity activity) {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, activity.getResources().getString(R.string.url_obtener_ids_online), null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display response
                        Log.d("Response: ", response.toString());

                        // Guardamos en preferencias los ids para poder acceder a ellos ams adelante
                        int[] idsOnline = parseJsonIdsOnline(response);
                        AlmacenSesionImpl.getInstance(activity).guardarIdsOnline(idsOnline[0], idsOnline[1]);
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

    public void modificaEstadoJugadorOnline(int idJugadorOnline, int estado, final Activity activity) {
        Map<String, String> params = new HashMap();
        params.put("idJugadorOnline", Integer.toString(idJugadorOnline));
        params.put("estado", Integer.toString(estado));
        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, activity.getResources().getString(R.string.url_cambiar_estado_jugador), parameters,
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

    public void eliminarJugadorOnline(final Activity activity) {
        JsonObjectRequest deleteRequest = new JsonObjectRequest(Request.Method.DELETE, activity.getResources().getString(R.string.url_borrar_jugador_online), null,
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
    private ArrayList<JugadorOnline> parseJson(JSONObject jsonObject){
        // Variables locales
        ArrayList<JugadorOnline> jugadoresOnline = new ArrayList();
        // Creamos un array para el resultado devuelto.
        JSONArray jsonArray = null;

        try {
            jsonArray = jsonObject.getJSONArray("datos");

            for(int i=0; i<jsonArray.length(); i++) {

                boolean isAnfitrion = false;

                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);

                    if (objeto.getString("isAnfitrion").equals("0")) {
                        isAnfitrion = false;
                    } else {
                        isAnfitrion = true;
                    }

                    JugadorOnline jugadorOnline = new JugadorOnline(
                            objeto.getInt("idJugadorOnline"),
                            objeto.getString("nombreUsuario"),
                            objeto.getInt("puntuacion"),
                            objeto.getInt("numAccionesEuropeas"),
                            objeto.getInt("numAccionesAmericanas"),
                            objeto.getInt("numAccionesAsiaticas"),
                            objeto.getInt("numBonosDelEstado"),
                            objeto.getInt("numPlanDePensiones"),
                            objeto.getInt("liquidez"),
                            objeto.getInt("financiacion"),
                            isAnfitrion,
                            objeto.getInt("estado"));

                    jugadoresOnline.add(jugadorOnline);

                } catch (JSONException e) {
                    Log.d("Parseando:", "Error de parsing: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jugadoresOnline;
    }

    // Obtenemos los identificadores online del usuario (idJugadorOnline y el idPartidaOnline de la aprtida a la que s eha unido)
    private int[] parseJsonIdsOnline(JSONObject jsonObject){

        int idJugadorOnline;
        int idPartidaOnline;
        int[] ids = new int[2];
        // Creamos un array para el resultado devuelto.
        JSONArray jsonArray = null;

        try {
            jsonArray = jsonObject.getJSONArray("datos");

            for(int i=0; i<jsonArray.length(); i++) {

                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);

                    idJugadorOnline = objeto.getInt("idJugadorOnline");
                    idPartidaOnline = objeto.getInt("idPartidaOnline");

                    ids[0] = idJugadorOnline;
                    ids[1] = idPartidaOnline;
                } catch (JSONException e) {
                    Log.d("Parseando:", "Error de parsing: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ids;
    }
}