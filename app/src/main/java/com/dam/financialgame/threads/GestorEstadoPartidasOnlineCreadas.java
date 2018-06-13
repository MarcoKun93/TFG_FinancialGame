package com.dam.financialgame.threads;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.dam.financialgame.controllers.activities.PrincipalPartidasOnline;
import com.dam.financialgame.servicesImpl.PartidaOnlineServiceImpl;

/* Clase que gestionara, en segundo plano, los estados de la partida. En este caso, las partidas online con estado creadas.
   Para ello, la clase sera llamada con un parametor que indica el estado esperado por el cliente.
   Hara peticiones continuas, en un hilo aparte, sobre el estado de la aprtida online, hasta que se devuelva el estado deseado.
 */

public class GestorEstadoPartidasOnlineCreadas extends AsyncTask <Integer, Void, Integer> {
    PrincipalPartidasOnline mActivity;

    public GestorEstadoPartidasOnlineCreadas (PrincipalPartidasOnline activity)
    {
        super();
        mActivity = activity;
    }

    @Override
    protected void onPreExecute() {
        Toast toast = Toast.makeText(mActivity, "Recarga automática de partidas creadas", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);  // Indicamos que aparezca la notificacion en el centro
        toast.show();
    }

    @Override
    protected Integer doInBackground(Integer... n) {
        int res = 0;

        while(!isCancelled()) {
            PartidaOnlineServiceImpl.getInstance().getPartidasOnline(mActivity);
            SystemClock.sleep(3000);
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
        Log.d("AsyncTask", "EstadoPartidasOnlineCreadas: cancelado");
    }
}
