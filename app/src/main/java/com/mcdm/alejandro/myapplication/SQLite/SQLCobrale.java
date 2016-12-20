package com.mcdm.alejandro.myapplication.SQLite;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatatypeMismatchException;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.mcdm.alejandro.myapplication.clases.cliente;

import java.util.ArrayList;
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
    private static String tablaVentas = "CREATE TABLE ventas(idVenta INTEGER PRIMARY KEY AUTOINCREMENT, idCliente INTEGER, idProductos INTEGER, idPagos INTEGER, fechaVenta TEXT, prendasTotal INTEGER, total REAL, abono REAL, diaSemana TEXT, plazo TEXT, sincronizado BOOLEAN)";
    private static String tablaPagos = "CREATE TABLE pagos(idPago INTEGER PRIMARY KEY AUTOINCREMENT, monto REAL, resto REAL, total REAL, fechaCobro TEXT, fechaPago TEXT, sincronizado BOOLEAN)";
    private static String tablaPrendas = "CREATE TABLE prendas(idProducto INTEGER, descripccion TEXT, tipoPrenda TEXT, costo REAL, sincronizado BOOLEAN)";
    private static String tablaLugares =  "CREATE TABLE lugar(idLugar INTEGER PRIMARY KEY AUTOINCREMENT, nombre TEXT)";

    //NOMBRE DE TABLAS
    final String clienteT = "cliente";
    final String ventas = "ventas";
    final String pagos = "pagos";
    final String prendas = "prendas";
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
        sqLiteDatabase.execSQL(tablaLugares);
        Log.d(TAG, "EXITOSAMENTE CREADAS LAS TABLAS ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+clienteT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ventas);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+pagos);
        sqLiteDatabase.execSQL("DROP TABLE I EXISTS "+prendas);
        sqLiteDatabase.execSQL("DROP TABLE I EXISTS "+lugares);
        sqLiteDatabase.execSQL(tablaCliente);
        sqLiteDatabase.execSQL(tablaVentas);
        sqLiteDatabase.execSQL(tablaPagos);
        sqLiteDatabase.execSQL(tablaPrendas);
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
            Toast.makeText(context, "Se insertó la razón social", Toast.LENGTH_SHORT).show();
        }catch (SQLiteException ex){
            Log.d(TAG, "ERROR AL INSERTAR "+ex.getMessage());
            Toast.makeText(context, "No se insertó la razón social, intentelo de nuevo", Toast.LENGTH_SHORT).show();
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
}
