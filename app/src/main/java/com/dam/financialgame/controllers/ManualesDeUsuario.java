package com.dam.financialgame.controllers;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TabHost;
import com.dam.financialgame.R;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

public class ManualesDeUsuario extends AppCompatActivity implements OnPageChangeListener,OnLoadCompleteListener {

    TabHost manualesDeUsuario;
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manuales_de_usuario);

        // Inicializamos el TabHost.
        manualesDeUsuario = (TabHost) findViewById(R.id.manualesTabHost);
        manualesDeUsuario.setup();
        TabHost.TabSpec tab1 = manualesDeUsuario.newTabSpec("tab1");  //aspectos de cada Tab (pestaña)
        TabHost.TabSpec tab2 = manualesDeUsuario.newTabSpec("tab2");
        tab1.setIndicator("INTRUCCIONES");    //qué queremos que aparezca en las pestañas
        tab1.setContent(R.id.instruccionesPartida); //definimos el id de cada Tab (pestaña)
        tab2.setIndicator("MANUAL DE USUARIO");
        tab2.setContent(R.id.manualDeUsuario);
        manualesDeUsuario.addTab(tab1); //añadimos los tabs ya programados
        manualesDeUsuario.addTab(tab2);

        // Enlazamos los pdf view.
        pdfView = (PDFView)findViewById(R.id.pdfViewInstrucciones);
        displayFromAsset(getResources().getString(R.string.pdf_instrucciones));
    }

    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;

        pdfView.fromAsset(pdfFileName)
                .defaultPage(pageNumber)
                .enableSwipe(true)
                .swipeHorizontal(false)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .load();
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {
            Log.d("Manuales", String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    // El manual de usuario se mostrará en una actividad aparte.
    public void irManualDeUsuario(View view) {
        // Start the next activity
        Intent mainIntent = new Intent().setClass(view.getContext(), ManualDeUsuario.class);
        startActivity(mainIntent);
    }
}
