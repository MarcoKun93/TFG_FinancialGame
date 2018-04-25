package com.dam.financialgame.services;

import android.app.Activity;

import com.dam.financialgame.controllers.MenuDeInicio;
import com.dam.financialgame.model.Usuario;

public interface UsuarioService {

    // Metodo que hace un post de la información introducida y crea un nuevo usuario en el servidor.
    public void registrarUsuario(String nombre, String contrasenia, String correo, MenuDeInicio activity);

    // Metodo que hace un post de la informacion introducida y me devuelve el usuario correspondiente.
    public void logearUsuario(String correo, String contrasenia, MenuDeInicio activity);
}
