package com.dam.financialgame.controllers.activities;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dam.financialgame.R;
import com.dam.financialgame.model.JugadorOnline;
import com.dam.financialgame.servicesImpl.AlmacenSesionImpl;
import com.dam.financialgame.servicesImpl.JugadorOnlineServiceImpl;
import com.dam.financialgame.servicesImpl.PartidaOnlineServiceImpl;

import java.util.ArrayList;

public class EscenarioOnline extends AppCompatActivity {

    ListView rankingEscenarioOnline;
    TextView textoNumRondasMax;
    ArrayAdapter<String> adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escenario_online);

        rankingEscenarioOnline = (ListView)findViewById(R.id.rankingEscenarioOnline);
        textoNumRondasMax = (TextView)findViewById(R.id.textoNumRondasOnline);

        // Llamamos al servicio que pide los jugadoresOnline
        JugadorOnlineServiceImpl.getInstance().getJugadoresUnidos(this);

        textoNumRondasMax.setText(Integer.toString(getIntent().getExtras().getInt("numRondas")));
    }

    public void muestraDatosJugadoresOnline(ArrayList<JugadorOnline> jugadoresOnline) {
        ArrayList<String> infoJugadores = new ArrayList<String>();

        for(JugadorOnline jugadorAux: jugadoresOnline) {
            infoJugadores.add(jugadorAux.toString());
        }

        adaptador = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, infoJugadores);
        rankingEscenarioOnline.setAdapter(adaptador);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
