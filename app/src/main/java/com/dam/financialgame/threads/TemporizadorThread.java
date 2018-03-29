package com.dam.financialgame.threads;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import com.dam.financialgame.R;
import com.dam.financialgame.controllers.Escenario;

public class TemporizadorThread extends AsyncTask<Integer, Integer, Integer> {

    private ProgressDialog progreso;
    private boolean entrado;    // Variable de control para activar el reproductorTimeOut

    // Objeto que reproducirá el audio
    private MediaPlayer reproductorStart;
    private MediaPlayer reproductorEnd;
    private MediaPlayer reproductorTimeOut;

    private static final String TAG = TemporizadorThread.class.getSimpleName();  // Para depurar

    private Escenario mActivity;

    public TemporizadorThread (Escenario activity)
    {
        super();
        mActivity = activity;
    }


    @Override
    protected void onPreExecute() {
        progreso = new ProgressDialog(mActivity);
        progreso.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progreso.setProgressNumberFormat("");
        progreso.setMessage("Tiempo restante...");
        progreso.setCancelable(false);
        progreso.setMax(100);
        progreso.setProgress(0);
        progreso.show();

        // Le pasamos al reproductor el audio que queremos que reproduzca
        reproductorStart = MediaPlayer.create(mActivity, R.raw.start_ring);
        reproductorEnd = MediaPlayer.create(mActivity, R.raw.end_turn);
        reproductorTimeOut = MediaPlayer.create(mActivity, R.raw.time_running_out);
        reproductorTimeOut.setLooping(true);    //Indico que quiero que el audio se reproduzca en bucle

        reproductorStart.start();

        entrado = false;
    }

    @Override
    protected Integer doInBackground(Integer... n) {
        // Implementamos el temporizador aquí
        // n[0] corresponde a la variable segundosIntervalo
        int i;
        for (i = 1; i <= n[0]; i++) {
            SystemClock.sleep(1000);
            Log.d(TAG, "iniciarTemporizador() hilo valor "+i); // Para depurar
            publishProgress(i, n[0]);   // Indicamos lo que llevamos, y el maximo
        }

        return i;
    }

    @Override
    protected void onProgressUpdate(Integer... n) {
        if((n[0] > n[1]/2) && !entrado) {   // Activamos el TimeOut si falta menos de la mitad del tiempo total
            reproductorTimeOut.start();
            entrado = true;
        }

        progreso.setProgress((100 * n[0])/n[1]);
    }

    @Override
    protected void onPostExecute(Integer res) {
        progreso.dismiss();

        // Efectos de sonido
        reproductorTimeOut.stop();
        reproductorTimeOut.release();
        reproductorEnd.start();

        mActivity.finalizarTemporizador();
    }
}
