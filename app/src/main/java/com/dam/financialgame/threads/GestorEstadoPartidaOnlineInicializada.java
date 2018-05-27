package com.dam.financialgame.threads;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.dam.financialgame.controllers.PartidaOnlineInicializada;
import com.dam.financialgame.controllers.PrincipalPartidasOnline;
import com.dam.financialgame.servicesImpl.JugadorOnlineServiceImpl;
import com.dam.financialgame.servicesImpl.PartidaOnlineServiceImpl;

/* Clase que gestiona el estado de la partida y jugadores online. Hace peticiones periodicas sobre los jugadores online unidos.
    El proceso principal los gestiona, en caso de que todos los jugadores esten preparados, se inicia la partida. Cuando avandonamos
    la actividad, cancelamos este hilo. El hilo, si detecta que es cancelado, manda peticion de cambiar el estado de la partida a iniciada.
 */

public class GestorEstadoPartidaOnlineInicializada extends AsyncTask <Integer, Void, Integer> {

    PartidaOnlineInicializada mActivity;

    public GestorEstadoPartidaOnlineInicializada (PartidaOnlineInicializada activity)
    {
        super();
        mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        Toast toast = Toast.makeText(mActivity, "Recarga automática de jugadores online unidos", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
        toast.show();
    }

    @Override
    protected Integer doInBackground(Integer... n) {
        int res = 0;

        while(!isCancelled()) {
            SystemClock.sleep(1000);
            JugadorOnlineServiceImpl.getInstance().getJugadoresUnidos(mActivity);
        }
        return res;
    }

    /*
    @Override
    protected void onProgressUpdate(Integer... porc) {
        while(!isCancelled()) {
            PartidaOnlineServiceImpl.getInstance().getPartidasOnline();
        }
    }
    */

    @Override
    protected void onPostExecute(Integer res) {

    }

    @Override
    protected void onCancelled() {
        Log.d("AsyncTask", "EstadoPartidasOnlineInicializada: cancelado");
        // Llamamos al servicio de partid online para indicar que la partida esta iniciada.

    }
}
