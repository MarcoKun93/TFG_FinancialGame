package com.dam.financialgame;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.dam.financialgame.R;

public class MyAlertDialogFragment extends DialogFragment {

    Button positivaClick;
    Button negativeClick;
    TextView mensajeMostrado;
    public static final String TAG = "AlertFragment";

    public static MyAlertDialogFragment newInstance(String mensaje) {
        MyAlertDialogFragment frag = new MyAlertDialogFragment();
        Bundle args = new Bundle();
        args.putString("mensaje", mensaje);
        frag.setArguments(args);

        return frag;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialogFragment);
    }

    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_alert_dialog, contenedor, false);

        // Buscamos los view corespondientes generados en la vista
        mensajeMostrado = (TextView) vista.findViewById(R.id.mensajeMostrado);
        positivaClick = (Button) vista.findViewById(R.id.confirmar);
        negativeClick = (Button) vista.findViewById(R.id.cancelar);

        mensajeMostrado.setText(getArguments().getString("mensaje"));

        inicializarAlert();

        return vista;
    }

    private void inicializarAlert() {
        // Por si hace falta
    }
}