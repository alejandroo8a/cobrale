package com.mcdm.alejandro.myapplication.SQLite;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatatypeMismatchException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.mcdm.alejandro.myapplication.clases.DEBEN;
import com.mcdm.alejandro.myapplication.clases.cliente;
import com.mcdm.alejandro.myapplication.clases.pagos;
import com.mcdm.alejandro.myapplication.clases.prendas;
import com.mcdm.alejandro.myapplication.clases.ventas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by alejandro on 7/12/16.
 */


public class SQLCobrale  extends SQLiteOpenHelper{

    //NOMBRE DE LA BD
    private static String nombreDB="cobrale.sqlite";
    private static String TAG="SQLCobrale";
    private static final int version=1;

    //TABLAS A GENERAR
    private static String tablaCliente = "CREATE TABLE cliente(idCliente INTEGER PRIMARY KEY  AUTOINCREMENT, nombre TEXT, calle TEXT, colonia TEXT, telefono1 TEXT, telefono2 TEXT, activo BOOLEAN, razonSocial TEXT, sincronizado BOOLEAN)";
    private static String tablaVentas = "CREATE TABLE ventas(idVenta INTEGER PRIMARY KEY AUTOINCREMENT, idCliente INTEGER, idProductos INTEGER, idPagos INTEGER, fechaVenta TEXT, prendasTotal INTEGER, total REAL, diaSemana TEXT, plazo TEXT, pagado BOOLEAN, sincronizado BOOLEAN)";
    private static String tablaPagos = "CREATE TABLE pagos(idPago INTEGER, monto REAL, resto REAL, total REAL, fechaCobro TEXT, fechaPago TEXT, activo BOOLEAN, sincronizado BOOLEAN)";
    private static String tablaPrendas = "CREATE TABLE prendas(idProducto INTEGER, descripccion TEXT, tipoPrenda TEXT, costo REAL, cantidad REAL, sincronizado BOOLEAN)";
    private static String tablaRopa = "CREATE TABLE ropa(idRopa INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, sincronizado BOOLEAN)";
    private static String tablaLugares =  "CREATE TABLE lugar(idLugar INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT, sincronizado BOOLEAN)";

    //NOMBRE DE TABLAS
    final String clienteT = "cliente";
    final String venta = "ventas";
    final String pago = "pagos";
    final String prenda = "prendas";
    final String ropa = "ropa";
    final String lugares = "lugar";

    public SQLCobrale(Context context){
        super(context, nombreDB,null,version);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(tablaCliente);
        sqLiteDatabase.execSQL(tablaVentas);
        sqLiteDatabase.execSQL(tablaPagos);
        sqLiteDatabase.execSQL(tablaPrendas);
        sqLiteDatabase.execSQL(tablaRopa);
        sqLiteDatabase.execSQL(tablaLugares);
        Log.d(TAG, "EXITOSAMENTE CREADAS LAS TABLAS ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+clienteT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+venta);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+pago);
        sqLiteDatabase.execSQL("DROP TABLE I EXISTS "+prenda);
        sqLiteDatabase.execSQL("DROP TABLE I EXISTS "+ropa);
        sqLiteDatabase.execSQL("DROP TABLE I EXISTS "+lugares);
        sqLiteDatabase.execSQL(tablaCliente);
        sqLiteDatabase.execSQL(tablaVentas);
        sqLiteDatabase.execSQL(tablaPagos);
        sqLiteDatabase.execSQL(tablaPrendas);
        sqLiteDatabase.execSQL(tablaRopa);
        sqLiteDatabase.execSQL(tablaLugares);
    }


    //INSERTAR************************************
    public void insertCliente(cliente persona, Context context){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues nuevoCliente = new ContentValues();
        nuevoCliente.put("nombre",persona.getNombre());
        nuevoCliente.put("calle",persona.getCalle());
        nuevoCliente.put("colonia",persona.getColonia());
        nuevoCliente.put("telefono1",persona.getTelefono1());
        nuevoCliente.put("telefono2",persona.getTelefono2());
        nuevoCliente.put("razonSocial",persona.getRazonSocial());
        nuevoCliente.put("activo",persona.isActivo());
        nuevoCliente.put("razonSocial",persona.getRazonSocial());
        nuevoCliente.put("sincronizado",persona.isSincronizado());
        try {
            db.insertOrThrow(clienteT,null,nuevoCliente);
            Log.d(TAG, "SE INSERTO CLIENTE NUEVO ");
            Toast.makeText(context, "Cliente registrado correctamente", Toast.LENGTH_LONG).show();
        }catch (SQLiteException ex){
            Log.d(TAG, "ERROR AL INSERTAR "+ex.getMessage());
            Toast.makeText(context, "No se insertó el cliente, intentelo de nuevo", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void insertRazonSocial(String razon, Context context){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues nuevaRazon = new ContentValues();
        nuevaRazon.put("nombre", razon);
        try {
            db.insertOrThrow(lugares, null, nuevaRazon);
            Log.d(TAG, "SE INSERTO LA RAZON SOCIAL ");
        }catch (SQLiteException ex){
            Log.d(TAG, "ERROR AL INSERTAR "+ex.getMessage());
            Toast.makeText(context, "No se insertó la razón social, intentelo de nuevo", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void insertPrendas(Integer idPrenda, List<prendas> prendas, Context context){
        SQLiteDatabase db = getWritableDatabase();
        for (int i = 0 ; i<prendas.size() ; i++){
            ContentValues nuevaPrenda = new ContentValues();
            nuevaPrenda.put("idProducto", idPrenda);
            nuevaPrenda.put("descripccion", prendas.get(i).getDescripccion());
            nuevaPrenda.put("tipoPrenda",prendas.get(i).getTipoPrenda());
            nuevaPrenda.put("costo",prendas.get(i).getCosto());
            nuevaPrenda.put("cantidad",prendas.get(i).getCantidad());
            nuevaPrenda.put("sincronizado",prendas.get(i).isSincronizado());
            try{
                db.insertOrThrow(prenda,null,nuevaPrenda);
                Log.d(TAG, "SE INSERTO LA PRENDA ");
            }catch (SQLiteException ex){
                Toast.makeText(context, "No se pudo insertar la prenda", Toast.LENGTH_SHORT).show();
            }
        }
        db.close();
    }

    public void insertPago(Integer idPago, pagos pay,Context context){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues p = new ContentValues();
        p.put("idPago",idPago);
        p.put("monto",pay.getMonto());
        p.put("resto",pay.getResto());
        p.put("total",pay.getTotal());
        p.put("fechaCobro",pay.getFechaCobro());
        p.put("fechaPago",pay.getFechaPago());
        p.put("activo",pay.isActivo());
        p.put("sincronizado",pay.isSincronizado());
        try{
            db.insertOrThrow(pago,null,p);
            Log.d(TAG, "PAGO INSERTADO");
        }catch (SQLiteException ex){
            Toast.makeText(context, "No se pudo insertar el pago", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    public void insertVenta(ventas ven, Context context){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues sell = new ContentValues();
        sell.put("idCliente",ven.getIdCliente());
        sell.put("idProductos",ven.getIdProductos());
        sell.put("idPagos",ven.getIdPagos());
        sell.put("fechaVenta",ven.getFechaVenta());
        sell.put("prendasTotal",ven.getPrendasTotal());
        sell.put("total",ven.getTotal());
        sell.put("diaSemana",ven.getDiaSemana());
        sell.put("plazo", ven.getPlazo());
        sell.put("pagado",ven.isPagado());
        sell.put("sincronizado",ven.isSincronizado());
        try{
            db.insertOrThrow(venta,null,sell);
            Log.d(TAG, "VENTA INSERTADA ");
            Toast.makeText(context, "Se guardo la venta exitosamente", Toast.LENGTH_SHORT).show();
        }catch (SQLiteException ex){
            Log.d(TAG, "ERROR AL INSERTAR "+ex.getMessage());
            Toast.makeText(context, "No se insertó la venta, intentelo de nuevo", Toast.LENGTH_SHORT).show();
        }
        db.close();

    }

    public void insertRopa(String nombre, Context context){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues ropas = new ContentValues();
        ropas.put("nombre", nombre);
        ropas.put("sincronizado", false);
        try{
            db.insertOrThrow(ropa,null,ropas);
            Log.d(TAG, "ROPA INSERTADA CORRECTAMENTE ");
        }catch (SQLiteException ex){
            Toast.makeText(context, "Problema al insertar la prenda: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(TAG, "PROBLEMA AL INSERTAR LA ROPA "+ ex.getMessage());
        }
        db.close();
    }



    //ELIMINAR***********************************
    public void deleteCliente(String nombre, Context context){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.delete(clienteT,"nombre='"+nombre+"'",null);
            Toast.makeText(context, nombre+" se eliminó correctamente", Toast.LENGTH_SHORT).show();
        }catch (SQLException ex){
            Toast.makeText(context, "No se pudo eliminar el cliente: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    //ACTUALIZAR*********************************
    public void updateCliente(cliente persona, Context context){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues actualizarPersona = new ContentValues();
        actualizarPersona.put("nombre",persona.getNombre());
        actualizarPersona.put("calle",persona.getCalle());
        actualizarPersona.put("colonia",persona.getColonia());
        actualizarPersona.put("telefono1",persona.getTelefono1());
        actualizarPersona.put("telefono2",persona.getTelefono2());
        actualizarPersona.put("razonSocial",persona.getRazonSocial());
        try{
            db.update(clienteT,actualizarPersona,"idCliente="+persona.getId(),null);
            Toast.makeText(context, "Se actualizó el cliente", Toast.LENGTH_SHORT).show();
        }catch (SQLiteException ex){
            Toast.makeText(context, "No se actualizó el cliente", Toast.LENGTH_SHORT).show();
        }

    }
    //ACTUALIZAR LA TABLA DE VENTAS QUE EL PAGO SE TERMINÓ DE PAGAR
    public void updateVenta(Integer idPago, Context context){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues actualizarPagado = new ContentValues();
        actualizarPagado.put("pagado",true);
        try{
            db.update(venta,actualizarPagado,"idPagos="+idPago,null);
            Toast.makeText(context, "El cliente término su deuda", Toast.LENGTH_SHORT).show();
        }catch (SQLiteException ex){
            Toast.makeText(context, "Error al completar el pago: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //ACTUALIZA EL PAGO ANTESESOR
    public void updatePago(Integer idPago, Context context){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues actualizarPago = new ContentValues();
        actualizarPago.put("activo",false);
        try{
            db.update(pago,actualizarPago,"idPago="+idPago+" AND activo = 1",null);
            Log.d(TAG, "ACTUALIZACION DE PAGO CORRECTAMENTE ");
        }catch (SQLiteException ex){
            Log.d(TAG, "SIN PAGO: "+ex.getMessage());
            Toast.makeText(context, "No se actualizó el pago", Toast.LENGTH_SHORT).show();
        }
    }



    //SELECCIONAR*********************************

    public List<String> getRazonSocial(){
        SQLiteDatabase db = getWritableDatabase();
        List<String> razones = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT nombre FROM "+lugares,null);
        if(cursor.moveToFirst()){
            do {
                razones.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return razones;
    }

    public List <String> getClientes(){
        SQLiteDatabase db = getWritableDatabase();
        List<String> clientes = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT nombre FROM "+clienteT+" ORDER BY nombre ASC",null);
        if(cursor.moveToFirst()){
            do{
                clientes.add(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return clientes;
    }

    public cliente getClienteDatos(String nombre){
        SQLiteDatabase db = getWritableDatabase();
        cliente persona = new cliente();
        Cursor cursor = db.rawQuery("SELECT * FROM "+clienteT +" WHERE nombre='"+nombre+"'",null);
        if(cursor.moveToFirst()){
            do {
                persona.setId(cursor.getInt(0));
                persona.setNombre(cursor.getString(1));
                persona.setCalle(cursor.getString(2));
                persona.setColonia(cursor.getString(3));
                persona.setTelefono1(cursor.getString(4));
                persona.setTelefono2(cursor.getString(5));
                persona.setRazonSocial(cursor.getString(7));
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return persona;
    }

    public ArrayList<DEBEN> getDeben(){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<DEBEN> listaDeben = new ArrayList<>();
        //ArrayList<DEBEN> listaBuena = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT "+clienteT+".nombre, "+pago+".resto, "+pago+".fechaCobro, "+pago+".idPago, "+pago+".total FROM "+pago+
                " INNER JOIN "+ venta +" ON "+pago+ ".idPago = "+venta+".idPagos"+
                " INNER JOIN "+ clienteT +" ON "+ venta + ".idCliente = "+clienteT +".idCliente"+
                " WHERE "+venta+ ".pagado = 0  AND "+pago+".activo = 1 ORDER BY "+pago+".fechaCobro ASC",null);
        if(cursor.moveToFirst()){
            do {
                DEBEN deben = new DEBEN();
                deben.setNombre(cursor.getString(0));
                deben.setResto(cursor.getString(1));
                deben.setFecha(cursor.getString(2));
                deben.setId(cursor.getInt(3));
                deben.setTotal(cursor.getDouble(4));
                listaDeben.add(deben);
            }while (cursor.moveToNext());
        }
        /*for (int i = 0 ; i<listaDeben.size() ; i++) {
            listaBuena.add(listaDeben.get(i));
            for(int n = i+1 ; n < listaDeben.size() ; n++){
                if(listaBuena.get(i).getId() == listaDeben.get(n).getId()) {
                    listaDeben.remove(n);
                    n--;
                }
            }
        }
        return listaBuena;*/
        return listaDeben;
    }

    public ArrayList<DEBEN> getDebenHoy(String fecha) throws ParseException {
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<DEBEN> listaDeben = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date fechaHoy = format.parse(fecha);
        Date fechaResta = null;
        long dif = 0;
        Cursor cursor = db.rawQuery("SELECT "+clienteT+".nombre, "+pago+".resto, "+pago+".fechaCobro, "+pago+".idPago,"+pago+".total FROM "+pago+
                " INNER JOIN "+ venta +" ON "+pago+ ".idPago = "+venta+".idPagos"+
                " INNER JOIN "+ clienteT +" ON "+ venta + ".idCliente = "+clienteT +".idCliente"+
                " WHERE "+venta+ ".pagado = 0 AND "+pago+".fechaCobro <= '"+fecha+"' AND "+pago+".activo = 1",null);
        if(cursor.moveToFirst()){
            do {
                DEBEN deben = new DEBEN();
                deben.setNombre(cursor.getString(0));
                deben.setResto(cursor.getString(1));
                fechaResta = format.parse(cursor.getString(2));
                dif=fechaHoy.getTime() - fechaResta.getTime();
                deben.setFecha("Tiene "+ dif / (1000 * 60 * 60 * 24)+" días de retraso");
                deben.setId(cursor.getInt(3));
                deben.setTotal(cursor.getDouble(4));
                listaDeben.add(deben);
            }while (cursor.moveToNext());
        }
        return listaDeben;
    }

    public List<String> getRopa(){
        SQLiteDatabase db = getWritableDatabase();
        List<String> listaRopa = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT nombre FROM "+ropa,null);
        if(cursor.moveToFirst()){
            do{
                listaRopa.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
        return listaRopa;
    }
}

