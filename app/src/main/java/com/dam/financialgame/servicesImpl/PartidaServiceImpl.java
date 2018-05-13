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
import com.android.volley.toolbox.StringRequest;
import com.dam.financialgame.R;
import com.dam.financialgame.controllers.Comunidad;
import com.dam.financialgame.model.Partida;
import com.dam.financialgame.services.PartidaService;
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

// La clase hará uso de un singleton para no tener que instanciarla cada vez que hagamos uso de ella.
// Por eso, cuando queremos obtener la instancia, comprobamos antes que no se haya instanciado ya.
// IMPORTANTE: Para acceder a los servicios de partida, debemos ofrecer la claveapi del usuario.
public class PartidaServiceImpl implements PartidaService {

    private Comunidad comunidad;
    private static PartidaServiceImpl partidaService;

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

    // Una petición GET, obtenemos la url del archivo de recursos strings.xml
    public void obtenerPartidasSubidas(final Comunidad activity) {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, activity.getResources().getString(R.string.url_obtener_partidas_subidas), null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display response
                        Log.d("Response: ", response.toString());

                        // Debemos parsear el contenido recibido del JSON.
                        if(!parseJson(response).isEmpty())
                            activity.callbackMisPartidasFragment(parseJson(response));
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

    // Una petición POST, obtenemos la url del archivo de recursos strings.xml
    public void subirPartida(int numrondas, int numjugadores, String nombreganador, int puntuacionganador, final Activity activity) {
        final Date date = new Date();
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        final String fecha = dateFormat.format(date);
        int idSemillaAux = AlmacenSesionImpl.getInstance(activity).obtenerSemillaId();

        // Si el idSemilla es 0, quiere decir que no hay ninguna seleccionada.
        // En mi servidor, la semilla vacia es el id 3.
        if(idSemillaAux == 0)
            idSemillaAux = 3;

        Map<String, String> params = new HashMap();
        params.put("fecha", fecha);
        params.put("numrondas", Integer.toString(numrondas));
        params.put("numjugadores", Integer.toString(numjugadores));
        params.put("nombreganador", nombreganador);
        params.put("puntuacionganador", Integer.toString(puntuacionganador));
        params.put("idSemilla", Integer.toString(idSemillaAux));

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, activity.getResources().getString(R.string.url_subir_Partida), parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO: handle success
                        Log.d("Response: ", response.toString());

                        Toast toast = Toast.makeText(activity.getApplicationContext(), "Partida subida", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
                        toast.show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
                Log.d("ERROR Response:", error.toString());

                Toast toast = Toast.makeText(activity.getApplicationContext(), "Error al registrar usuario", Toast.LENGTH_LONG);
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

    // Metodo GET para obtener el ranking de las partidas. Recibiremos un maximo de 20 elementos.
    public void obtenerRanking(final Comunidad activity) {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, activity.getResources().getString(R.string.url_ranking), null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display response
                        Log.d("Response: ", response.toString());

                        // Debemos parsear el contenido recibido del JSON.
                        if(!parseJson(response).isEmpty())
                            activity.callbackRanking(parseJson(response));
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

    public void borrarPartida(int idPartida, final Activity activity) {
        String urlCompleta = activity.getResources().getString(R.string.url_eliminar_partida)+"/"+Integer.toString(idPartida);

        JsonObjectRequest deleteRequest = new JsonObjectRequest(Request.Method.DELETE, urlCompleta, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display response
                        Log.d("Response: ", response.toString());

                        Toast toast = Toast.makeText(activity.getApplicationContext(), "Partida eliminada", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
                        toast.show();
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
    private ArrayList<Partida> parseJson(JSONObject jsonObject){
        // Variables locales
        ArrayList<Partida> partidas = new ArrayList();
        // Creamos un array para el resultado devuelto.
        JSONArray jsonArray = null;

        try {
            jsonArray = jsonObject.getJSONArray("datos");

            for(int i=0; i<jsonArray.length(); i++) {

                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);

                    Partida partida = new Partida(
                            objeto.getInt("idPartida"),
                            objeto.getString("fecha"),
                            objeto.getInt("numrondas"),
                            objeto.getInt("numjugadores"),
                            objeto.getString("nombreganador"),
                            objeto.getInt("puntuacionganador"),
                            objeto.getInt("idUsuario"),
                            objeto.getInt("idSemilla"));

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
