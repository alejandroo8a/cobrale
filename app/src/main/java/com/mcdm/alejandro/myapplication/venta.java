package com.mcdm.alejandro.myapplication;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class venta extends AppCompatActivity {

    private Spinner spPlazo;
    private Button btnAgregarProducto;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);
        spPlazo = (Spinner)findViewById(R.id.spPlazo);
        btnAgregarProducto = (Button)findViewById(R.id.btnAgregarProducto);
        poblarSpinner();


    }

    private void poblarSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Plazo, R.layout.spinner_item);
        spPlazo.setAdapter(adapter);
    }

    //AQUI VAN LAS FUNCIONES DEL DIALOG QUE SE CREA
    public void createProducto(View x){
        AlertDialog.Builder builder = new AlertDialog.Builder(venta.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_agregar_producto,null);
        Spinner spTipoPrenda = (Spinner)v.findViewById(R.id.spTipoPrenda);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),R.array.Ropa, R.layout.spinner_item);
        spTipoPrenda.setAdapter(adapter);
        builder.setView(v);
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).show();

    }
}
