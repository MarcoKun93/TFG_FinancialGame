package com.dam.financialgame;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dam.financialgame.R;

// Clase que pertenece a un Dialog fragment, que mostrará el nombre del jugador ganador.
public class FinDelJuegoFragment extends DialogFragment {
    TextView nombreGanadorView;

    public static FinDelJuegoFragment newInstance(String nombreGanador) {
        FinDelJuegoFragment frag = new FinDelJuegoFragment();
        Bundle args = new Bundle();
        args.putString("nombreGanador", nombreGanador);
        frag.setArguments(args);

        return frag;
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int style = DialogFragment.STYLE_NORMAL, theme = 0;
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.dialogFragment);
    }

    public View onCreateView(LayoutInflater inflador, ViewGroup contenedor, Bundle savedInstanceState) {
        View vista = inflador.inflate(R.layout.fragment_fin_del_juego, contenedor, false);

        // Buscamos los view corespondientes generados en la vista
        nombreGanadorView = (TextView) vista.findViewById(R.id.nombreGanador);

        nombreGanadorView.setText(getArguments().getString("nombreGanador"));

        return vista;
    }

    // Cuando este fragment sea destruido, eliminamos la actividad invocadora ya que es el fin del juego
    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().finish();
    }
}
