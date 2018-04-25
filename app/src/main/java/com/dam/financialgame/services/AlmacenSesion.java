package com.dam.financialgame.services;

// Clase que gestiona la informacion almacenada sobre la sesion del usuario.
// Hacemos uso de mantenimiento de sesion por Tokens (el nuestro sera la claveapi).
// Guardamos la informacion en un archivo de preferencia.
public interface AlmacenSesion {

    // Guardamos en el archivo de preferencias la informacion de sesion.
    public void salvarDatos(String nombre, String correo, String claveapi);

    // Borramos del archivo de preferencia la informacion de sesion, equivale a cerrar sesion.
    public void borrarDatos();

    // Comprobamos si sesion iniciada, si o no.
    public boolean comprobarLogeado();
}