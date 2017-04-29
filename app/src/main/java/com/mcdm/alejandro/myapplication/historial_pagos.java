package com.mcdm.alejandro.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.mcdm.alejandro.myapplication.SQLite.SQLCobrale;
import com.mcdm.alejandro.myapplication.adapter.adapter_cardHistorial;
import com.mcdm.alejandro.myapplication.clases.historial_card;

import java.util.ArrayList;
import java.util.List;

public class historial_pagos extends AppCompatActivity {
    private static final String TAG = "historial_pagos";

    private SQLCobrale db;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager lMangager;
    private List<historial_card> aVentas;
    private List<historial_card> aHistorial;

    private TextView txtNombreHistorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial_pagos);
        txtNombreHistorial = (TextView)findViewById(R.id.txtNombreHistorial);
        db = new SQLCobrale(getApplicationContext());
        int idCliente = getIntent().getIntExtra("ID", 0);
        String nombre = getIntent().getStringExtra("NOMBRE");
        txtNombreHistorial.setText(nombre);
        aVentas = obtenerVentas(idCliente);
        obtenerPagos();
        crearRecycler();
        crearAdminsitrador();
        crearAdapter();
    }

    private List<historial_card> obtenerVentas(int idCliente){
        aVentas = new ArrayList<>();
        aHistorial = new ArrayList<>();
        return db.getVentasHistorial(idCliente);
    }

    private void crearRecycler(){
        recycler = (RecyclerView)findViewById(R.id.reciclador);
        recycler.setHasFixedSize(true);
    }
    private void crearAdminsitrador(){
        lMangager = new LinearLayoutManager(this);
        recycler.setLayoutManager(lMangager);
    }

    private void crearAdapter(){
        adapter = new adapter_cardHistorial(aHistorial);
        recycler.setAdapter(adapter);
    }

    private void obtenerPagos(){
        for (int i = 0 ; i<aVentas.size() ; i++){
            aHistorial.add(aVentas.get(i));
            aHistorial = db.getPagosHistorial(aHistorial, aVentas.get(i).getId());
        }

    }
}
