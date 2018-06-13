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
import com.dam.financialgame.controllers.activities.Comunidad;
import com.dam.financialgame.model.Logro;
import com.dam.financialgame.services.LogroService;
import com.dam.financialgame.threads.VolleyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// La clase hará uso de un singleton para no tener que instanciarla cada vez que hagamos uso de ella.
// Por eso, cuando queremos obtener la instancia, comprobamos antes que no se haya instanciado ya.
// IMPORTANTE: Para acceder a los servicios de logro, debemos ofrecer la claveapi del usuario.
public class LogroServiceImpl implements LogroService {

    private static LogroServiceImpl logroService;

    public synchronized static LogroServiceImpl getInstance() {
        if (logroService == null) {
            synchronized(LogroServiceImpl.class) {
                if (logroService == null)
                    logroService = new LogroServiceImpl();
            }
        }

        // Return the instance
        return logroService;
    }

    // Metodo que obtiene los logros de un usuario. GET
    public void obtenerLogros(final Comunidad activity) {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, activity.getResources().getString(R.string.url_obtener_logros), null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display response
                        Log.d("Response: ", response.toString());

                        // Debemos parsear el contenido recibido del JSON.
                        if(!parseJson(response).isEmpty())
                            activity.callbackListadoLogros(parseJson(response));
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

    // Metodo que comprueba si cumple el logro TuPrimeraPartida. POST
    public void compruebaLogroPrimeraPartida(final Activity activity) {
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, activity.getResources().getString(R.string.url_logro_primera_partida), null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display response
                        Log.d("Response: ", response.toString());

                        // Debemos parsear el contenido recibido del JSON.
                        if(!parseJsonLogro(response).isEmpty()) {
                            Toast toast = Toast.makeText(activity.getApplicationContext(), parseJsonLogro(response), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);  // Indicamos que aparezca la notificacion en el centro
                            toast.show();
                        }
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

    // Metodo que comprueba si cumple el logro Millonario. POST
    public void compruebaLogroMillonario(int puntuacionganador, final Activity activity) {
        Map<String, String> params = new HashMap();
        params.put("puntuacionganador", Integer.toString(puntuacionganador));

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, activity.getResources().getString(R.string.url_logro_millonario), parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display response
                        Log.d("Response: ", response.toString());

                        // Debemos parsear el contenido recibido del JSON.
                        if(parseJsonLogro(response) != null && !parseJsonLogro(response).equals("")) {
                            Toast toast = Toast.makeText(activity.getApplicationContext(), parseJsonLogro(response), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);  // Indicamos que aparezca la notificacion en el centro
                            toast.show();
                        }
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

    // Metodo que comprueba si cumple el logro PartidaLarga. POST
    public void compruebaLogroPartidaLarga(int numrondas, final Activity activity) {
        Map<String, String> params = new HashMap();
        params.put("puntuacionganador", Integer.toString(numrondas));

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, activity.getResources().getString(R.string.url_logro_partida_larga), parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display response
                        Log.d("Response: ", response.toString());

                        // Debemos parsear el contenido recibido del JSON.
                        if(parseJsonLogro(response) != null && !parseJsonLogro(response).equals("")) {
                            Toast toast = Toast.makeText(activity.getApplicationContext(), parseJsonLogro(response), Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);  // Indicamos que aparezca la notificacion en el centro
                            toast.show();
                        }
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

    /** Metodos para parsear la informacion de partida **/

    // Obtenemos las partidas del servidor correspondientes al usuario logeado, es un array. Debido a la estructura del
    // JSON, debemos indicar que cogemos los valores del objeto datos del JSON.
    private ArrayList<Logro> parseJson(JSONObject jsonObject){
        // Variables locales
        ArrayList<Logro> logros = new ArrayList();
        // Creamos un array para el resultado devuelto.
        JSONArray jsonArray = null;

        try {
            jsonArray = jsonObject.getJSONArray("datos");

            for(int i=0; i<jsonArray.length(); i++) {

                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);

                    Logro logro = new Logro(
                            objeto.getInt("idLogro"),
                            objeto.getString("nombre"),
                            objeto.getString("descripcion"),
                            objeto.getString("tipo"),
                            objeto.getString("variableafectada"),
                            objeto.getInt("valor"));

                    logros.add(logro);

                } catch (JSONException e) {
                    Log.d("Parseando:", "Error de parsing: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return logros;
    }

    // Obtenemos el mensaje parseado del logro.
    // Debemos distinguir entre logro conseguido o no, segun el valor del atributo "estado".
    // Si estado = 1 es que es exitoso, con otro cualquier valor no se cumple logro.
    private String parseJsonLogro(JSONObject jsonObject) {
        String textoLogro = null;
        int estado;

        try {
            estado = jsonObject.getInt("estado");

            if(estado == 1) {
                textoLogro = jsonObject.getString("mensaje");
            } else {
                textoLogro = "";
            }

        } catch (JSONException e) {
            Log.d("ERROR parseJson", "Error de parsing: "+ e.getMessage());
        }

        return  textoLogro;
    }
}
