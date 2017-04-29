package com.mcdm.alejandro.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.mcdm.alejandro.myapplication.SQLite.SQLCobrale;
import com.mcdm.alejandro.myapplication.adapter.adapter_historial_detalle;
import com.mcdm.alejandro.myapplication.clases.prendas;

import java.util.ArrayList;

public class historial_detalles extends AppCompatActivity {

    static private final String TAG ="historial_detalles";

    private GridView grdHistorialDetalle;

    private ArrayList<prendas> listaPrendas;
    private SQLCobrale db;
    private adapter_historial_detalle adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_detalles);
        int idVenta = getIntent().getIntExtra("IDVENTA", 0);
        db = new SQLCobrale(getApplicationContext());
        grdHistorialDetalle = (GridView)findViewById(R.id.grdHistorialDetalle);
        int idPrenda = getIdPrendas(idVenta);
        llenarListaPrendas(idPrenda);
        llenarGridHistorial();
    }

    private int getIdPrendas(int idVenta){
        return db.getIdPrenda(idVenta);
    }

    private void llenarListaPrendas(int idPrenda){
        listaPrendas = db.getPrendasHistorialDetalle(idPrenda);
    }

    private void llenarGridHistorial(){
        if(!listaPrendas.isEmpty()){
            adapter = new adapter_historial_detalle(getApplicationContext(), listaPrendas);
            grdHistorialDetalle.setAdapter(adapter);
        }

    }
}
