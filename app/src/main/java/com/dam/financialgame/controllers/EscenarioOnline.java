package com.dam.financialgame.controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.dam.financialgame.R;
import com.dam.financialgame.servicesImpl.AlmacenSesionImpl;
import com.dam.financialgame.servicesImpl.JugadorOnlineServiceImpl;
import com.dam.financialgame.servicesImpl.PartidaOnlineServiceImpl;

public class EscenarioOnline extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escenario_online);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
