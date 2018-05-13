package com.dam.financialgame.controllers;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.dam.financialgame.R;
import com.dam.financialgame.model.Logro;
import com.dam.financialgame.model.Partida;
import com.dam.financialgame.model.Semilla;
import com.dam.financialgame.servicesImpl.AlmacenSesionImpl;
import com.dam.financialgame.servicesImpl.PartidaServiceImpl;

import java.util.ArrayList;

public class Comunidad extends AppCompatActivity {

    Toolbar toolbar;
    FrameLayout contenedorFragment;

    // El gestor de fragments encargado de mostrar los fragments segun indicamos en el menu.
    //FragmentTransaction ft = getFragmentManager().beginTransaction();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunidad);

        toolbar = (Toolbar)findViewById(R.id.toolbar);
        contenedorFragment = (FrameLayout) findViewById(R.id.contenedorFragment);

        // Indicamos a la actividad que muestre el toolbar.
        setSupportActionBar(toolbar);
        // Ponemos el titulo deseado del toolbar.
        getSupportActionBar().setTitle("Comunidad: " + AlmacenSesionImpl.getInstance(this).obtenerUsuarioLogeado().getNombre());

        // Cargamos en el contenedor el fragment por defecto (en nuestro caso el PerfilFragment).
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment newFragment = new PerfilFragment();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        ft.replace(R.id.contenedorFragment, newFragment);
        // Indicamos una animacion.
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Commit the transaction
        ft.commit();

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
        // Create new fragment and transaction
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment newFragment = new PerfilFragment();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        ft.replace(R.id.contenedorFragment, newFragment, "Perfil");
        // Indicamos que se añada la vista del fragment a la pila, para si volvemos atras, vamos a ella.
        //ft.addToBackStack(null);
        // Indicamos una animacion.
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Commit the transaction
        ft.commit();
    }

    public void mostrarRanking (MenuItem item) {
        // Create new fragment and transaction
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment newFragment = new RankingFragment();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        ft.replace(R.id.contenedorFragment, newFragment, "Ranking");
        // Indicamos una animacion.
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Commit the transaction
        ft.commit();
    }

    public void mostrarMisPartidas (MenuItem item) {
        // Create new fragment and transaction
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment newFragment = new MisPartidasFragment();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        ft.replace(R.id.contenedorFragment, newFragment, "MisPartidas");
        // Indicamos una animacion.
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Commit the transaction
        ft.commit();
    }

    public void mostrarSemilla (MenuItem item) {
        // Create new fragment and transaction
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment newFragment = new SemillaFragment();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        ft.replace(R.id.contenedorFragment, newFragment, "Semilla");
        // Indicamos una animacion.
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Commit the transaction
        ft.commit();
    }

    public void mostrarLogros (MenuItem item) {
        // Create new fragment and transaction
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment newFragment = new LogrosFragment();
        // Replace whatever is in the fragment_container view with this fragment,
        // and add the transaction to the back stack
        ft.replace(R.id.contenedorFragment, newFragment, "Logros");
        // Indicamos una animacion.
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        // Commit the transaction
        ft.commit();
    }

    // Salimos de la actividad y además cerramos sesión.
    public void salirDeComunidad (MenuItem item) {
        AlmacenSesionImpl.getInstance(this).borrarDatos();
        finish();
    }

    // Metodo que se comunica con el fragmento de MisPartidas para ejecutar un metodo suyo.
    public void callbackMisPartidasFragment (ArrayList<Partida> partidas) {
        MisPartidasFragment fragment = (MisPartidasFragment) getFragmentManager().findFragmentByTag("MisPartidas");
        fragment.mostrarDatosObtenidos(partidas);
    }

    // Metodo que se comunica con el fragmento de Ranking para ejecutar un metodo suyo.
    public void callbackRanking (ArrayList<Partida> partidas) {
        RankingFragment fragment = (RankingFragment) getFragmentManager().findFragmentByTag("Ranking");
        fragment.mostrarDatosObtenidos(partidas);
    }

    // Metodo que se comunica con el fragmento de Semilla para ejecutar un metodo suyo.
    public void callbackListadoSemillas (ArrayList<Semilla> semillas) {
        SemillaFragment fragment = (SemillaFragment) getFragmentManager().findFragmentByTag("Semilla");
        fragment.mostrarDatosObtenidos(semillas);
    }

    // Parecido al anterior pero utilazo para cuando obtenemos una semilla en concreto.
    public void callbackSemillaSeleccionada (ArrayList<Semilla> semillas) {
        SemillaFragment fragment = (SemillaFragment) getFragmentManager().findFragmentByTag("Semilla");
        fragment.mostrarSemillaYaSeleccionada(semillas.get(0));
    }

    // Metodo que se comunica con el fragmento de logro para ejecutar un metodo suyo.
    public void callbackListadoLogros (ArrayList<Logro> logros) {
        LogrosFragment fragment = (LogrosFragment) getFragmentManager().findFragmentByTag("Logros");
        fragment.mostrarDatosObtenidos(logros);
    }
}
