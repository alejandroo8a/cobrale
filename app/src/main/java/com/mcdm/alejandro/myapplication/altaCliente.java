package com.mcdm.alejandro.myapplication;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.mcdm.alejandro.myapplication.SQLite.SQLCobrale;
import com.mcdm.alejandro.myapplication.clases.cliente;

import java.util.List;

public class altaCliente extends AppCompatActivity {

    private static String TAG = "altaCliente";
    SQLCobrale db;
    cliente persona;
    Boolean actualizar;
    ArrayAdapter<String> adapter;


    EditText edtNombre, edtCalle, edtColonia, edtTelefono1, edtTelefono2;
    Button btnGuardar, btnAgregarRazon, btnVenta;
    Spinner spRazonSocial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_cliente);
        db = new SQLCobrale(getApplicationContext());
        edtNombre = (EditText)findViewById(R.id.edtNombre);
        edtCalle = (EditText)findViewById(R.id.edtCalle);
        edtColonia = (EditText)findViewById(R.id.edtColonia);
        edtTelefono1 = (EditText)findViewById(R.id.edtTelefono1);
        edtTelefono2 = (EditText)findViewById(R.id.edtTelefono2);
        btnGuardar = (Button)findViewById(R.id.btnGuardar);
        btnAgregarRazon = (Button)findViewById(R.id.btnAgregarRazon);
        btnVenta = (Button)findViewById(R.id.btnVenta);
        spRazonSocial = (Spinner)findViewById(R.id.spRazonSocial);
        persona= new cliente();
        poblarSpinner();
        actualizar = getIntent().getBooleanExtra("ACTUALIZAR",false);
        btnVenta.setVisibility(View.INVISIBLE);
        if(actualizar){
            habilitarControles(false);
            btnGuardar.setText("Actualizar");
            agregarCliente(getIntent().getStringExtra("NOMBRE"));
            btnVenta.setVisibility(View.VISIBLE);
        }



    }
    //EVENTO QUE USA EL BOTON GUARDAR
    public void guardarCliente(View v){
        if(btnGuardar.getText().toString().equals("GUARDAR")) {
            if (edtNombre.length() == 0)
                Toast.makeText(this, "Agregue un nombre al cliente", Toast.LENGTH_SHORT).show();
            else {
                persona.setNombre(edtNombre.getText().toString());
                persona.setCalle(edtCalle.getText().toString());
                persona.setColonia(edtColonia.getText().toString());
                persona.setTelefono1(edtTelefono1.getText().toString());
                persona.setTelefono2(edtTelefono2.getText().toString());
                persona.setRazonSocial(spRazonSocial.getSelectedItem().toString());
                persona.setActivo(true);
                persona.setSincronizado(false);
                if (!actualizar)
                    db.insertCliente(persona, getApplicationContext());
                else
                    db.updateCliente(persona, getApplicationContext());
                cleanScreen();
                actualizar = false;
                this.finish();
            }
        }
        else {
            habilitarControles(true);
            btnGuardar.setText("GUARDAR");
        }


    }
    //EVENTO QUE USA EL BOTON AGREGAR
    public void agregarRazon(View v){
        createRazon();
    }
    //EVENTO QUE USA EL BOTON VENDER
    public void hacerVenta(View v){
        Intent intent = new Intent(this, venta.class);
        intent.putExtra("NOMBRE",edtNombre.getText().toString());
        startActivity(intent);
    }

    private void cleanScreen(){
        edtNombre.setText("");
        edtCalle.setText("");
        edtColonia.setText("");
        edtTelefono1.setText("");
        edtTelefono2.setText("");
    }
    //CREA EL DIALOG QUE PERMITE AGREGAR LAS RAZONES SOCIALES
    private void createRazon(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(altaCliente.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_razon_social, null);
        builder.setView(v);
        builder.setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        EditText edtRazon = (EditText)v.findViewById(R.id.edtRazon);
                        db.insertRazonSocial(edtRazon.getText().toString(),getApplicationContext());
                        poblarSpinner();

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

        builder.show();
    }
    //LLENA EL SPINNER CON LAS RAZONES SOCIALES
    private void poblarSpinner(){
        List<String> razones = db.getRazonSocial();
        adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,razones);
        spRazonSocial.setAdapter(adapter);

    }

    //PINTA CLIENTE EN PANTALLA
    private void agregarCliente(String nombre){
        persona=db.getClienteDatos(nombre);
        int position = adapter.getPosition(persona.getRazonSocial());
        edtNombre.setText(persona.getNombre());
        edtCalle.setText(persona.getCalle());
        edtColonia.setText(persona.getColonia());
        edtTelefono1.setText(persona.getTelefono1());
        edtTelefono2.setText(persona.getTelefono2());
        spRazonSocial.setSelection(position);
        btnGuardar.setText("ACTUALIZAR");
    }

    private void habilitarControles(Boolean ac){
        edtNombre.setEnabled(ac);
        edtCalle.setEnabled(ac);
        edtColonia.setEnabled(ac);
        edtTelefono1.setEnabled(ac);
        edtTelefono2.setEnabled(ac);
        spRazonSocial.setEnabled(ac);
        btnAgregarRazon.setEnabled(ac);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        actualizar=false;
    }
}
