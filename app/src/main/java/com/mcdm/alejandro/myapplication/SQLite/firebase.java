package com.mcdm.alejandro.myapplication.SQLite;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mcdm.alejandro.myapplication.R;
import com.mcdm.alejandro.myapplication.clases.cliente;
import com.mcdm.alejandro.myapplication.clases.lugarRopa;
import com.mcdm.alejandro.myapplication.clases.pagos;
import com.mcdm.alejandro.myapplication.clases.prendas;
import com.mcdm.alejandro.myapplication.clases.ventas;

import java.util.List;

/**
 * Created by alejandro on 14/01/17.
 */

public class firebase {
    static private final String TAG ="firebase";
    FirebaseDatabase fb;
    SQLCobrale db;
    DatabaseReference myRef;
    Context context;
    String avisoRespaldo = "Se respaldó:\n ";

    public firebase(){
    }

    public firebase(Context context){
        fb=FirebaseDatabase.getInstance();
        db= new SQLCobrale(context);
        this.context=context;
    }

    public void respaldo(){
        guardarClientes();
        guardarLugar();
        guardarPrendas();
        guardarRopa();
        guardarVentas();
        guardarPagos();
        avisoResultadosRespaldo();
    }

    private void guardarClientes(){
        List<cliente> clientes = db.respaldoCliente(context);
        if(!clientes.isEmpty()){
            myRef = fb.getReference("cliente");
            for(final cliente cl : clientes){
               // myRef.child(cl.getId().toString()).setValue(new cliente(cl.getNombre(),cl.getCalle(),cl.getColonia(),cl.getTelefono1(),cl.getTelefono2(),cl.isActivo(),cl.getRazonSocial(),cl.isSincronizado())  );
                myRef.child(cl.getId().toString()).setValue(new cliente(cl.getNombre(), cl.getCalle(), cl.getColonia(), cl.getTelefono1(), cl.getTelefono2(), cl.isActivo(), cl.getRazonSocial(), cl.isSincronizado()), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        db.actualizarSincronizado(cl.getId(), "cliente", context);
                    }
                });
            }
            avisoRespaldo+="\t-Clientes\n";
        }
    }

    private void guardarPrendas(){
        List<prendas> aprendas = db.respaldoPrendas(context);
        if(!aprendas.isEmpty()){
            myRef = fb.getReference("prendas");
            for(final prendas pr : aprendas){
                myRef.child(pr.getIdNube().toString()).setValue(new prendas(pr.getIdPrenda(),pr.getDescripccion(), pr.getTipoPrenda(), pr.getCantidad(), pr.getCosto(), pr.isSincronizado()), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        db.actualizarSincronizado(pr.getIdNube(), "prendas", context);
                    }
                });
            }
            avisoRespaldo+="\t-Prendas\n";
        }
    }

    private void guardarLugar(){
        List<lugarRopa> aLugar = db.respaldoLugares(context);
        if(!aLugar.isEmpty()){
            myRef = fb.getReference("lugar");
            for(final lugarRopa place : aLugar){
                myRef.child(place.getId().toString()).setValue(new lugarRopa(place.getId(), place.getNombre(),place.isSincronizado()), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                db.actualizarSincronizado(place.getId(), "lugar", context);
                            }
                        });
            }
            avisoRespaldo+="\t-Lugares\n";
        }
    }

    private void guardarPagos(){
        List<pagos> aPagos = db.respaldoPagos(context);
        if(!aPagos.isEmpty()){
            myRef = fb.getReference("pagos");
            for(final pagos pay : aPagos){
                myRef.child(myRef.push().getKey()).setValue(new pagos(pay.getId(), pay.getMonto(),pay.getResto(), pay.getTotal(), pay.getFechaCobro(), pay.getFechaPago(), pay.isActivo(), pay.isSincronizado()), new DatabaseReference.CompletionListener() {
                            @Override
                            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                                db.actualizarSincronizado(pay.getId(),"pagos",context);
                            }
                        });
            }
            avisoRespaldo+="\t-Pagos";
        }
    }

    private void guardarRopa(){
        List<lugarRopa> aRopa = db.respaldoRopa(context);
        if(!aRopa.isEmpty()){
            myRef = fb.getReference("ropa");
            for(final lugarRopa ropa : aRopa){
                myRef.child(ropa.getId().toString()).setValue(new lugarRopa(ropa.getId(), ropa.getNombre(), ropa.isSincronizado()), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        db.actualizarSincronizado(ropa.getId(), "ropa", context);

                    }
                });
            }
            avisoRespaldo+="\t-Ropa\n";
        }
    }

    private void guardarVentas(){
        List<ventas> aVentas = db.respaldoVentas(context);
        if(!aVentas.isEmpty()){
            myRef = fb.getReference("ventas");
            for (final ventas venta : aVentas){
                myRef.child(venta.getIdVenta().toString()).setValue(new ventas(venta.getIdVenta(), venta.getIdCliente(), venta.getIdProductos(), venta.getIdPagos(), venta.getFechaVenta(), venta.getPrendasTotal(), venta.getPlazo(), venta.getDiaSemana(), venta.getTotal(), venta.isPagado(), venta.isSincronizado()), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            db.actualizarSincronizado(venta.getIdVenta(), "ventas", context);

                    }
                });
            }
            avisoRespaldo+="\t-Ventas\n";
        }
    }

    private void avisoResultadosRespaldo(){
        if(avisoRespaldo.equals("Se respaldó:\n "))
            avisoRespaldo = "Sus datos estan totalmente respaldados.";
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle("Resultado de respaldo")
                .setMessage(avisoRespaldo)
                .setIcon(R.drawable.ic_backup)
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }
}

    /*private void anillo(){
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
        progreso.setTitle("Respaldando datos...");
        progreso.setProgressStyle(progreso.STYLE_HORIZONTAL);
        progreso.setProgress(0);
        progreso.setMax(100);
        progreso.show();

    }

    private void cerrarBarra(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    // Here you should write your time consuming task...
                        Thread.sleep(1000);
                        if (progreso.getProgress() == progreso.getMax())
                            progreso.dismiss();

                } catch (Exception e) {
                }
            }
        }).start();
    }*/