package com.dam.financialgame.servicesImpl;

import android.content.Context;
import android.content.SharedPreferences;

import com.dam.financialgame.services.AlmacenSesion;

// La clase hará uso de un singleton para no tener que instanciarla cada vez que hagamos uso de ella.
// Por eso, cuando queremos obtener la instancia, comprobamos antes que no se haya instanciado ya.
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

    public void salvarDatos(String nombre, String correo, String claveapi) {
        //Nos ponemos a editar las preferencias.
        SharedPreferences preferencias = context.getSharedPreferences(PREFERENCIAS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        editor.putString("nombre", nombre);
        editor.putString("correo", correo);
        editor.putString("claveapi", claveapi);

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
}
