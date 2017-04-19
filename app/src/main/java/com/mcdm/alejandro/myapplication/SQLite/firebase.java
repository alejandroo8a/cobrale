package com.mcdm.alejandro.myapplication.SQLite;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mcdm.alejandro.myapplication.R;
import com.mcdm.alejandro.myapplication.clases.cliente;
import com.mcdm.alejandro.myapplication.clases.conexion;
import com.mcdm.alejandro.myapplication.clases.lugarRopa;
import com.mcdm.alejandro.myapplication.clases.pagos;
import com.mcdm.alejandro.myapplication.clases.prendas;
import com.mcdm.alejandro.myapplication.clases.ventas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

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
    ProgressDialog anillo = null;

    public firebase(){
    }

    public firebase(Context context){
        fb=FirebaseDatabase.getInstance();
        db= new SQLCobrale(context);
        this.context=context;
    }

    public void respaldo(){
        conexion conexion = new conexion();
        if(conexion.isAvaliable(context)){
            if(conexion.isOnline()){
                guardarClientes();
                guardarLugar();
                guardarPrendas();
                guardarRopa();
                guardarVentas();
                guardarPagos();
                avisoResultadosRespaldo();
            }else
                avisoNoConexion();

        }else
            avisoNoRed();

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


    //Obtencion de firebase
    public void obtenerBaseNube(){
        mostrarCargandoAnillo();
        db.borrarDatos();
        obtenerClientes();
    }

    private void obtenerClientes(){
        myRef = fb.getReference("cliente");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<cliente> clientes = new ArrayList<cliente>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    cliente cliente = ds.getValue(cliente.class);
                    clientes.add(cliente);
                }
                db.borrarClientes();
                db.insertarClientesNube(clientes, context);
                obtenerLugar();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    private void obtenerLugar(){
        myRef = fb.getReference("lugar");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<lugarRopa> lugares = new ArrayList<lugarRopa>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    lugarRopa lugar = ds.getValue(lugarRopa.class);
                    lugares.add(lugar);
                }
                db.insertarLugaresNube(lugares, context);
                obtenerPagos();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void obtenerPagos(){
        myRef = fb.getReference("pagos");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<pagos> pagos = new ArrayList<pagos>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    pagos pago = ds.getValue(pagos.class);
                    pagos.add(pago);
                }
                db.insertarPagosNube(pagos, context);
                obtenerPrendas();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void obtenerPrendas(){
        myRef = fb.getReference("prendas");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<prendas> clothes = new ArrayList<prendas>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    prendas pre = ds.getValue(prendas.class);
                    clothes.add(pre);
                }
                db.insertarPrendasNube(clothes, context);
                obtenerRopa();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void obtenerRopa(){
        myRef = fb.getReference("ropa");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<lugarRopa> clothes = new ArrayList<lugarRopa>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    lugarRopa pre = ds.getValue(lugarRopa.class);
                    clothes.add(pre);
                }
                db.insertarRopaNube(clothes, context);
                obtenerVentas();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void obtenerVentas(){
        myRef = fb.getReference("ventas");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<ventas> aVentas = new ArrayList<ventas>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    ventas pre = ds.getValue(ventas.class);
                    aVentas.add(pre);
                }
                db.insertarVentasNube(aVentas, context);
                ocultarCargandoAnillo();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
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


    private void avisoNoRed(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("AVISO")
                .setIcon(R.drawable.ic_wifi_off)
                .setMessage("Encienda el Wi-Fi o los datos móviles.")
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void avisoNoConexion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("AVISO")
                .setIcon(R.drawable.ic_cloud_off)
                .setMessage("No hay conexión a internet.")
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
    }

    private void mostrarCargandoAnillo(){
        this.anillo = ProgressDialog.show(context, "Obteniendo datos", "Cargando...", true, true);
    }

    private void ocultarCargandoAnillo(){
        this.anillo.dismiss();
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