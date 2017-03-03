package com.mcdm.alejandro.myapplication.SQLite;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mcdm.alejandro.myapplication.clases.cliente;
import com.mcdm.alejandro.myapplication.clases.prendas;

import java.util.List;
import java.util.logging.Handler;

/**
 * Created by alejandro on 14/01/17.
 */

public class firebase {
    FirebaseDatabase fb;
    SQLCobrale db;
    DatabaseReference myRef;
    Context context;

    public firebase(){
    }

    public firebase(Context context){
        fb=FirebaseDatabase.getInstance();
        db= new SQLCobrale(context);
        this.context=context;
    }

    public void respaldo(){
        guardarPrendas();
        guardarClientes();

    }

    private void guardarClientes(){
        List<cliente> clientes = db.respaldoCliente(context);
        if(!clientes.isEmpty()){
            myRef = fb.getReference("cliente");
            for(cliente cl : clientes){
               // myRef.child(cl.getId().toString()).setValue(new cliente(cl.getNombre(),cl.getCalle(),cl.getColonia(),cl.getTelefono1(),cl.getTelefono2(),cl.isActivo(),cl.getRazonSocial(),cl.isSincronizado())  );
                myRef.child(cl.getId().toString()).setValue(new cliente(cl.getNombre(), cl.getCalle(), cl.getColonia(), cl.getTelefono1(), cl.getTelefono2(), cl.isActivo(), cl.getRazonSocial(), cl.isSincronizado()), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        //AQUI SE LLENARA LA BARRA DE PROGRESO
                    }
                });
            }
            Toast.makeText(context, "Clientes respaldados", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(context, "No hay nada que respaldar2", Toast.LENGTH_SHORT).show();
    }

    private void guardarPrendas(){
        List<prendas> aprendas = db.respaldoPrendas(context);
        if(!aprendas.isEmpty()){
            myRef = fb.getReference("prendas");
            for(prendas pr : aprendas){
                myRef.child(pr.getIdNube().toString()).setValue(new prendas(pr.getIdPrenda(),pr.getDescripccion(), pr.getTipoPrenda(), pr.getCantidad(), pr.getCosto(), pr.isSincronizado()), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        //AQUI SE LLENARA LA BARRA DE PROGRESO
                    }
                });
            }
            Toast.makeText(context, "Prendas respaldadas", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(context, "No hay nada que respaldar1", Toast.LENGTH_SHORT).show();
    }


    private void anillo(){
        final ProgressDialog anillo = ProgressDialog.show(context,"Respaldando datos","Cargando...",true,true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    Thread.sleep(10000);
                }catch (Exception ex){

                }
                anillo.dismiss();
            }
        }).start();
    }

    private void barra(){
        final ProgressDialog barra = new ProgressDialog(context);
        barra.setTitle("Respaldando datos...");
        barra.setProgressStyle(barra.STYLE_HORIZONTAL);
        barra.setProgress(0);
        barra.setMax(20);
        barra.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // Here you should write your time consuming task...
                    while (barra.getProgress() <= barra.getMax()) {

                        Thread.sleep(2000);
                        barra.incrementProgressBy(2);



                        if (barra.getProgress() == barra.getMax()) {

                            barra.dismiss();

                        }
                    }
                } catch (Exception e) {
                }
            }
        }).start();
    }


}
