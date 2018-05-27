package com.dam.financialgame.servicesImpl;

import android.content.Context;
import android.content.SharedPreferences;

import com.dam.financialgame.model.Usuario;
import com.dam.financialgame.services.AlmacenSesion;

// La clase hará uso de un singleton para no tener que instanciarla cada vez que hagamos uso de ella.
// Por eso, cuando queremos obtener la instancia, comprobamos antes que no se haya instanciado ya.
// El archivo de preferencias está alojado en /data/data/com.dam.financialgame/shared_prefs/sesion.xml
public class AlmacenSesionImpl implements AlmacenSesion {

    private static AlmacenSesionImpl almacenSesion;
    private static String PREFERENCIAS = "sesion";
    Context context;

    public synchronized static AlmacenSesionImpl getInstance(Context context) {
        if (almacenSesion == null) {
            synchronized(UsuarioServiceImpl.class) {
                if (almacenSesion == null)
                    almacenSesion = new AlmacenSesionImpl(context);
            }
        }

        // Return the instance
        return almacenSesion;
    }

    public AlmacenSesionImpl(Context context) {
        this.context = context;
    }

    public void salvarDatos(String nombre, String correo, String claveapi, String ultimaconexion) {
        //Nos ponemos a editar las preferencias.
        SharedPreferences preferencias = context.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        editor.putString("nombre", nombre);
        editor.putString("correo", correo);
        editor.putString("claveapi", claveapi);
        editor.putString("ultimaconexion", ultimaconexion);
        editor.putInt("idSemilla", 0);

        // Commit la edición
        editor.commit();
    }

    public void borrarDatos() {
        // Editamos nuevamente las preferencias
        SharedPreferences preferencias = context.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        // Removemos los datos agregados
        editor.remove("nombre");
        editor.remove("correo");
        editor.remove("claveapi");
        editor.remove("ultimaconexion");
        editor.remove("idSemilla");

        // Commit la edición
        editor.commit();
    }

    public boolean comprobarLogeado() {
        boolean sesionIniciada = false;
        SharedPreferences preferencias = context.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);

        // Le pedimos a la configuración que nos de la claveapi.
        // Si no lo encuentra, por omisión "".
        String clave = preferencias.getString("claveapi", "");
        if(!clave.equals("")){
            sesionIniciada = true;
        }

        return sesionIniciada;
    }

    public Usuario obtenerUsuarioLogeado() {
        SharedPreferences preferencias = context.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);
        Usuario usuario = new Usuario(preferencias.getString("nombre", ""),
                                      preferencias.getString("correo", ""),
                                      preferencias.getString("claveapi", ""),
                                      preferencias.getString("ultimaconexion", ""));

        return usuario;
    }

    public void guardarSemillaId(int idSemilla) {
        SharedPreferences preferencias = context.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        editor.putInt("idSemilla", idSemilla);

        // Commit la edición
        editor.commit();

    }

    public int obtenerSemillaId() {
        SharedPreferences preferencias = context.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);

        return preferencias.getInt("idSemilla", 0);
    }

    // En realidad no la elimino, solo la inicializo a 0.
    public void borrarSemilla() {
        // Editamos nuevamente las preferencias
        SharedPreferences preferencias = context.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        // Removemos los datos agregados
        editor.putInt("idSemilla", 0);

        // Commit la edición
        editor.commit();
    }

    // Almacenamos los ids online
    public void guardarIdsOnline(int idJugadorOnline, int idPartidaOnline) {
        SharedPreferences preferencias = context.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        editor.putInt("idJugadorOnline", idJugadorOnline);
        editor.putInt("idPartidaOnline", idPartidaOnline);

        // Commit la edición
        editor.commit();
    }

    public int getIdJugadorOnlineAlmacenado() {
        SharedPreferences preferencias = context.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);

        return preferencias.getInt("idJugadorOnline", 0);
    }

    public int getIdPartdaOnlineAlmacenado() {
        SharedPreferences preferencias = context.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);

        return preferencias.getInt("idPartidaOnline", 0);
    }

    public void borrarIdsOnline() {
        // Editamos nuevamente las preferencias
        SharedPreferences preferencias = context.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        // Removemos los datos agregados
        editor.remove("idJugadorOnline");
        editor.remove("idPartidaOnline");

        // Commit la edición
        editor.commit();
    }
}
