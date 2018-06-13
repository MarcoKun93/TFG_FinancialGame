package com.dam.financialgame.services;

import android.app.Activity;

import com.dam.financialgame.controllers.activities.MenuDeInicio;

public interface UsuarioService {

    // Metodo que hace un post de la información introducida y crea un nuevo usuario en el servidor.
    public void registrarUsuario(String nombre, String contrasenia, String correo, MenuDeInicio activity);

    // Metodo que hace un post de la informacion introducida y me devuelve el usuario correspondiente.
    // Tambien me actualiza la fecha de la ultima conexion realizada en el sistema.
    public void logearUsuario(String correo, String contrasenia, String fecha, MenuDeInicio activity);

    // Metodo que actualiza la fecha de la ultima vez que se inicio sesion en el sistema.
    public void actualizarUltimaConexion(String fecha, MenuDeInicio activity);

    // Metodo que cambia la contraseña.
    public void cambiarContrasenia(Activity activity, String nuevaContrasenia);
}
