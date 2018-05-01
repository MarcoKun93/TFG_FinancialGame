package com.dam.financialgame.servicesImpl;

import android.app.Activity;
import android.util.JsonReader;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.dam.financialgame.R;
import com.dam.financialgame.controllers.MenuDeInicio;
import com.dam.financialgame.model.Usuario;
import com.dam.financialgame.services.UsuarioService;
import com.dam.financialgame.threads.VolleyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// La clase hará uso de un singleton para no tener que instanciarla cada vez que hagamos uso de ella.
// Por eso, cuando queremos obtener la instancia, comprobamos antes que no se haya instanciado ya.
public class UsuarioServiceImpl implements UsuarioService {

    private static UsuarioServiceImpl usuarioService;

    public synchronized static UsuarioServiceImpl getInstance() {
        if (usuarioService == null) {
            synchronized(UsuarioServiceImpl.class) {
                if (usuarioService == null)
                    usuarioService = new UsuarioServiceImpl();
            }
        }

        // Return the instance
        return usuarioService;
    }

    // Enviamos post con formato JSON
    public void registrarUsuario(String nombre, String contrasenia, String correo, final MenuDeInicio activity) {
        Map<String, String> params = new HashMap();
        params.put("nombre", nombre);
        params.put("contrasenia", contrasenia);
        params.put("correo", correo);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, activity.getResources().getString(R.string.url_registrar_usuario), parameters,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //TODO: handle success
                Log.d("Response: ", response.toString());

                Toast toast = Toast.makeText(activity.getApplicationContext(), "Usuario registrado correctamente", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
                toast.show();

                activity.closeDialog();
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
        });

        // Add it to the RequestQueue
        VolleyApplication.getInstance().getRequestQueue().add(jsonRequest);
    }

    // Enviamos post con formato JSON, recibimos la informacion de usuario y su claveApi necesaria para acceder al resto de servicios.
    public void logearUsuario(String correo, String contrasenia, final String fecha, final MenuDeInicio activity) {

        Map<String, String> params = new HashMap();
        params.put("correo", correo);
        params.put("contrasenia", contrasenia);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, activity.getResources().getString(R.string.url_logear_usuario), parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //TODO: handle success
                        Log.d("Response: ", response.toString());

                        Toast toast = Toast.makeText(activity.getApplicationContext(), "Sesión iniciada", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
                        toast.show();

                        // Debemos parsear el contenido recibido del JSON, cogemos el primer elemento ya que solo devuelve un usuario.
                        if(!parseJson(response).isEmpty()) {
                            activity.usuarioParseado(parseJson(response).get(0));

                            // A continuacion, actualizamos la fecha de la ultima vez que inicio sesion.
                            actualizarUltimaConexion(fecha, activity);
                        }

                        activity.closeDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                //TODO: handle failure
                Log.d("ERROR Response:", error.toString());

                Toast toast = Toast.makeText(activity.getApplicationContext(), "Error al iniciar sesión", Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
                toast.show();
            }
        });

        // Add it to the RequestQueue
        VolleyApplication.getInstance().getRequestQueue().add(jsonRequest);
    }

    // PUT, necesito identificarme, envio la clave api del usuario.
    public void actualizarUltimaConexion(String fecha, final MenuDeInicio activity) {
        final String claveApi = AlmacenSesionImpl.getInstance(activity.getApplicationContext()).obtenerUsuarioLogeado().getClaveapi();

        Map<String, String> params = new HashMap();
        params.put("ultimaconexion", fecha);

        JSONObject parameters = new JSONObject(params);

        JsonObjectRequest jsonRequest = new JsonObjectRequest(Request.Method.POST, activity.getResources().getString(R.string.url_actualizar_conexion), parameters,
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

                params.put("content-type", "application/json");
                //params.put("authorization", "2da7461a0f3011bb3f436d450cf5bb53");
                params.put("authorization", claveApi.toString());

                return params;
            }
        };

        // Add it to the RequestQueue
        VolleyApplication.getInstance().getRequestQueue().add(jsonRequest);
    }

    /** Metodos para parsear la informacion de usuario **/

    // Obtenemos el usuario logeado, es un array de tamaño 1. Debido a la estructura del
    // JSON, debemos indicar que cogemos los valores del objeto usuario.
    private ArrayList<Usuario> parseJson(JSONObject jsonObject){
        // Variables locales
        ArrayList<Usuario> usuarios = new ArrayList();
        // Obtener el objeto
        JSONObject objeto = jsonObject;

        try {
            Usuario usuario = new Usuario(
                    objeto.getJSONObject("usuario").getString("nombre"),
                    objeto.getJSONObject("usuario").getString("correo"),
                    objeto.getJSONObject("usuario").getString("claveapi"),
                    objeto.getJSONObject("usuario").getString("ultimaconexion"));


            usuarios.add(usuario);

        } catch (JSONException e) {
            Log.d("ERROR parseJson", "Error de parsing: "+ e.getMessage());
        }

        return usuarios;
    }
}


