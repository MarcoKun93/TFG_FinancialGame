package com.dam.financialgame.threads;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

// La clase hará uso de un singleton para no tener que instanciarla cada vez que hagamos una petición, además
// así sólo tenemos una cola de peticiones por aplicación. Heredamos de Application con este objetivo.
public class VolleyApplication extends Application {
    private static VolleyApplication volleyApplication;
    private RequestQueue requestQueue; // Objeto que procesa las peticiones.

    @Override
    public void onCreate() {
        super.onCreate();
        requestQueue = Volley.newRequestQueue(this);
        volleyApplication = this;
    }

    public synchronized static VolleyApplication getInstance() {
        return volleyApplication;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
