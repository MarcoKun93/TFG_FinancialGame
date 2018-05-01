package com.dam.financialgame.controllers;

import android.app.Fragment;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.dam.financialgame.R;
import com.dam.financialgame.servicesImpl.AlmacenSesionImpl;
import com.dam.financialgame.servicesImpl.PartidaServiceImpl;

import java.util.ArrayList;

public class Comunidad extends AppCompatActivity {

    Toolbar toolbar;
    Fragment fragmentMenuComunidad;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunidad);

        toolbar = (Toolbar)findViewById(R.id.toolbar);

        // Indicamos a la actividad que muestre el toolbar.
        setSupportActionBar(toolbar);
        // Ponemos el titulo deseado del toolbar.
        getSupportActionBar().setTitle("Comunidad: " + AlmacenSesionImpl.getInstance(this).obtenerUsuarioLogeado().getNombre());

        // DE PRUEBA: Llamamos al servicio que muestra todas las partidas subidas en el servidor.
        PartidaServiceImpl.getInstance().obtenerPartidasSubidas(this);
    }

    // Sobreescribimos el metodo de creacion del menu que aparecera en el toolbal con uno personalizado.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_comunidad, menu);
        return true;
    }

    // Procesos llamados desde las distintas opciones del menu del toolbar

    public void mostrarPerfil (MenuItem item) {

    }

    public void mostrarRanking (MenuItem item) {

    }

    public void mostrarMisPartidas (MenuItem item) {

    }

    public void mostrarSemilla (MenuItem item) {

    }

    public void mostrarLogros (MenuItem item) {

    }

    // Salimos de la actividad y además cerramos sesión.
    public void salirDeComunidad (MenuItem item) {
        AlmacenSesionImpl.getInstance(this).borrarDatos();
        finish();
    }
}
