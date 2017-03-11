package com.mcdm.alejandro.myapplication.SQLite;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.mcdm.alejandro.myapplication.R;
import com.mcdm.alejandro.myapplication.clases.DEBEN;
import com.mcdm.alejandro.myapplication.clases.HISTORIAL;
import com.mcdm.alejandro.myapplication.clases.cliente;
import com.mcdm.alejandro.myapplication.clases.lugarRopa;
import com.mcdm.alejandro.myapplication.clases.pagos;
import com.mcdm.alejandro.myapplication.clases.prendas;
import com.mcdm.alejandro.myapplication.clases.ventas;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by alejandro on 7/12/16.
 */

//TODO
    //EL ID DE PAGO SE DEBE DE CAMBIAR A UNO QUE SE ESTE GENERANDO UN FOLIO DISTINTO, YA QUE ES IMPOSIBLE HACER EL RESPALDO DE ESA FORMA
public class SQLCobrale  extends SQLiteOpenHelper{

    //NOMBRE DE LA BD
    private static String nombreDB="cobrale.sqlite";
    private static String TAG="SQLCobrale";
    private static final int version=1;

    //TABLAS A GENERAR
    private static String tablaCliente = "CREATE TABLE cliente(idCliente INTEGER PRIMARY KEY  AUTOINCREMENT, nombre TEXT, calle TEXT, colonia TEXT, telefono1 TEXT, telefono2 TEXT, activo BOOLEAN, razonSocial TEXT, sincronizado BOOLEAN)";
    private static String tablaVentas = "CREATE TABLE ventas(idVenta INTEGER PRIMARY KEY AUTOINCREMENT, idCliente INTEGER, idProductos INTEGER, idPagos INTEGER, fechaVenta TEXT, prendasTotal INTEGER, total REAL, diaSemana TEXT, plazo TEXT, pagado BOOLEAN, sincronizado BOOLEAN)";
    private static String tablaPagos = "CREATE TABLE pagos(idPago INTEGER, monto REAL, resto REAL, total REAL, fechaCobro TEXT, fechaPago TEXT, activo BOOLEAN, sincronizado BOOLEAN)";
    private static String tablaPrendas = "CREATE TABLE prendas(id INTEGER PRIMARY KEY AUTOINCREMENT,idProducto INTEGER, descripccion TEXT, tipoPrenda TEXT, costo REAL, cantidad REAL, sincronizado BOOLEAN)";
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
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+prenda);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ropa);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+lugares);
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
        nuevaRazon.put("sincronizado",false);
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
            nuevaPrenda.put("sincronizado",false);
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
        String fecha = "";
        p.put("idPago",idPago);
        p.put("monto",pay.getMonto());
        p.put("resto",pay.getResto());
        p.put("total",pay.getTotal());
        fecha = cambiarFechaGuardar(pay.getFechaCobro());
        p.put("fechaCobro",fecha);
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
        String fecha = "";
        sell.put("idCliente",ven.getIdCliente());
        sell.put("idProductos",ven.getIdProductos());
        sell.put("idPagos",ven.getIdPagos());
        fecha = cambiarFechaGuardar(ven.getFechaVenta());
        sell.put("fechaVenta",fecha);
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

    public void deleteRopa(String nombre, Context context){
        SQLiteDatabase db = getWritableDatabase();
        try{
            db.delete(ropa,"nombre='"+nombre+"'", null);
            Toast.makeText(context, nombre+" se eliminó correctamente", Toast.LENGTH_SHORT).show();
        }catch (SQLException ex) {
            Toast.makeText(context, "No se pudo eliminar la ropa: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
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
        actualizarPersona.put("sincronizado",0);
        try{
            db.update(clienteT,actualizarPersona,"idCliente="+persona.getId(),null);
            Toast.makeText(context, "Se actualizó el cliente", Toast.LENGTH_SHORT).show();
        }catch (SQLiteException ex){
            Toast.makeText(context, "No se actualizó el cliente", Toast.LENGTH_SHORT).show();
        }

    }
    //ACTUALIZAR LA TABLA DE VENTAS QUE EL PAGO SE TERMINÓ DE PAGAR
    public void updateVenta(Integer idPago, Context context, boolean compraNueva){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues actualizarPagado = new ContentValues();
        actualizarPagado.put("pagado",true);
        actualizarPagado.put("sincronizado",false);
        try{
            db.update(venta,actualizarPagado,"idPagos="+idPago,null);
            if(!compraNueva)
                alertaTerminoPago(context);
        }catch (SQLiteException ex){
            Toast.makeText(context, "Error al completar el pago: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void alertaTerminoPago(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Status de pago.")
                .setMessage("El cliente terminó su deudad.")
                .setIcon(R.drawable.ic_cash)
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .show();
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

    public List <cliente> getClientes(){
        SQLiteDatabase db = getWritableDatabase();
        List<cliente> clientes = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT idCliente, nombre FROM "+clienteT+" ORDER BY nombre ASC",null);
        if(cursor.moveToFirst()){
            do{
                cliente cl = new cliente();
                cl.setId(cursor.getInt(0));
                cl.setNombre(cursor.getString(1));
                clientes.add(cl);
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
        String fecha = "";
        Cursor cursor = db.rawQuery("SELECT "+clienteT+".nombre, "+pago+".resto, "+pago+".fechaCobro, "+pago+".idPago, "+pago+".total FROM "+pago+
                " INNER JOIN "+ venta +" ON "+pago+ ".idPago = "+venta+".idPagos"+
                " INNER JOIN "+ clienteT +" ON "+ venta + ".idCliente = "+clienteT +".idCliente"+
                " WHERE "+venta+ ".pagado = 0  AND "+pago+".activo = 1 ORDER BY "+pago+".fechaCobro ASC",null);
        if(cursor.moveToFirst()){
            do {
                DEBEN deben = new DEBEN();
                deben.setNombre(cursor.getString(0));
                deben.setResto(cursor.getString(1));
                fecha = cambiarFechaMostrar(cursor.getString(2));
                deben.setFecha(fecha);
                deben.setId(cursor.getInt(3));
                deben.setTotal(cursor.getDouble(4));
                listaDeben.add(deben);
            }while (cursor.moveToNext());
        }
        return listaDeben;
    }

    public ArrayList<DEBEN> getDebenHoy() throws ParseException {
        String fecha ="";
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<DEBEN> listaDeben = new ArrayList<>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        fecha = format.format(calendar.getTime());
        Date fechaHoy = format.parse(fecha);
        Date fechaResta = null;
        long dif = 0;
        Cursor cursor = db.rawQuery("SELECT "+clienteT+".nombre, "+pago+".resto, "+pago+".fechaCobro, "+pago+".idPago,"+pago+".total FROM "+pago+
                " INNER JOIN "+ venta +" ON "+pago+ ".idPago = "+venta+".idPagos"+
                " INNER JOIN "+ clienteT +" ON "+ venta + ".idCliente = "+clienteT +".idCliente"+
                " WHERE pagos.fechaCobro >= '2000/01/01' AND pagos.fechaCobro <= '"+fecha+"' AND "+venta+ ".pagado = 0 AND "+pago+".activo = 1",null);
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

    private String cambiarFechaGuardar(String fecha){
        String[] date = new String[3];
        date[0] = fecha.substring(0,2);
        date[1] = fecha.substring(3,5);
        date[2] = fecha.substring(6);
        fecha = date[2]+"/"+date[1]+"/"+date[0];
        return fecha;
    }

    private String cambiarFechaMostrar(String fecha){
        String[] date = new String[3];
        date[0] = fecha.substring(0,4);
        date[1] = fecha.substring(5,7);
        date[2] = fecha.substring(8);
        fecha = date[2]+"/"+date[1]+"/"+date[0];
        return fecha;
    }

    public ArrayList<HISTORIAL> getHistorial(){
        SQLiteDatabase db = getWritableDatabase();
        ArrayList<HISTORIAL> clientes = new ArrayList<>();
        Map<Integer,String > filtro = new HashMap<>();
        Cursor cursor = db.rawQuery("SELECT ventas.idCliente, cliente.nombre FROM "+venta+
                " INNER JOIN "+clienteT+" ON ventas.idCliente = cliente.idCliente"+
                " ORDER BY cliente.nombre ASC", null);
        if(cursor.moveToFirst()){
            do {
                if(!filtro.containsKey(cursor.getInt(0))){
                    filtro.put(cursor.getInt(0),cursor.getString(1));
                    HISTORIAL cl = new HISTORIAL();
                    cl.setId(cursor.getInt(0));
                    cl.setNombre(cursor.getString(1));
                    clientes.add(cl);
                }

            }while (cursor.moveToNext());
        }
        return clientes;
    }

    public double getPagoExiste(int idCliente, Context context, boolean compraNueva){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT idPagos, idVenta FROM "+venta+ " WHERE idCliente = "+idCliente+" and pagado = 0", null);
        if(cursor.moveToFirst()){
            double resto = getResto(cursor.getInt(0), context);
            updateVenta(cursor.getInt(1),context, compraNueva);
            return resto;
        }
        return 0;
    }

    public double getResto(int idPago, Context context){
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT resto from "+pago+" WHERE idPago = "+idPago+" AND activo = 1", null);
        if(cursor.moveToFirst()){
            updatePago(idPago, context);
            return cursor.getDouble(0);
        }
        return 0.0;
    }



    //RESPALDOS
    public List<cliente> respaldoCliente(Context context){
        SQLiteDatabase db = getWritableDatabase();
        List<cliente> clientes = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+clienteT+" WHERE sincronizado = 0",null);
        if(cursor.moveToFirst())
            do{
                cliente cl = new cliente();
                cl.setId(cursor.getInt(0));
                cl.setNombre(cursor.getString(1));
                cl.setCalle(cursor.getString(2));
                cl.setColonia(cursor.getString(3));
                cl.setTelefono1(cursor.getString(4));
                cl.setTelefono2(cursor.getString(5));
                cl.setActivo(cursor.getInt(6)>0);
                cl.setRazonSocial(cursor.getString(7));
                cl.setSincronizado(true);
                clientes.add(cl);
            }while (cursor.moveToNext());
        return clientes;
    }

    public List<prendas> respaldoPrendas(Context context){
        SQLiteDatabase db = getWritableDatabase();
        List<prendas> aprendas = new ArrayList<>();
        Cursor cursor =db.rawQuery("SELECT * FROM "+prenda+" WHERE sincronizado = 0",null);
        if(cursor.moveToFirst())
            do{
                prendas pr = new prendas();
                pr.setIdNube(cursor.getInt(0));
                pr.setIdPrenda(cursor.getInt(1));
                pr.setDescripccion(cursor.getString(2));
                pr.setTipoPrenda(cursor.getString(3));
                pr.setCosto(cursor.getDouble(4));
                pr.setCantidad(cursor.getInt(5));
                pr.setSincronizado(true);
                aprendas.add(pr);
            }while (cursor.moveToNext());
        return aprendas;
    }

    public List<lugarRopa> respaldoLugares(Context context){
        SQLiteDatabase db = getWritableDatabase();
        List<lugarRopa> aLugar = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+lugares+" WHERE sincronizado = 0",null);
        if(cursor.moveToFirst())
            do{
                lugarRopa place = new lugarRopa();
                place.setId(cursor.getInt(0));
                place.setNombre(cursor.getString(1));
                place.setSincronizado(true);
                aLugar.add(place);
            }while (cursor.moveToNext());
        return aLugar;
    }

    public List <pagos> respaldoPagos(Context context){
        SQLiteDatabase db = getWritableDatabase();
        List<pagos> aPagos = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+pago+" WHERE sincronizado = 0",null);
        if(cursor.moveToFirst())
            do{
                pagos pay = new pagos();
                pay.setId(cursor.getInt(0));
                pay.setMonto(cursor.getDouble(1));
                pay.setResto(cursor.getDouble(2));
                pay.setTotal(cursor.getDouble(3));
                pay.setFechaCobro(cursor.getString(4));
                pay.setFechaPago(cursor.getString(5));
                pay.setActivo(cursor.getInt(6)>0);
                pay.setSincronizado(true);
                aPagos.add(pay);
            }while(cursor.moveToNext());
        return aPagos;
    }

    public List<lugarRopa> respaldoRopa(Context context){
        SQLiteDatabase db = getWritableDatabase();
        List<lugarRopa> aLugar = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+ropa+" WHERE sincronizado = 0",null);
        if(cursor.moveToFirst())
            do{
                lugarRopa place = new lugarRopa();
                place.setId(cursor.getInt(0));
                place.setNombre(cursor.getString(1));
                place.setSincronizado(true);
                aLugar.add(place);
            }while (cursor.moveToNext());
        return aLugar;
    }

    public List<ventas> respaldoVentas(Context context){
        SQLiteDatabase db = getWritableDatabase();
        List<ventas> aVentas = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+venta+" WHERE sincronizado = 0",null);
        if(cursor.moveToFirst())
            do{
                ventas sell = new ventas();
                sell.setIdVenta(cursor.getInt(0));
                sell.setIdCliente(cursor.getInt(1));
                sell.setIdProductos(cursor.getInt(2));
                sell.setIdPagos(cursor.getInt(3));
                sell.setFechaVenta(cursor.getString(4));
                sell.setPrendasTotal(cursor.getString(5));
                sell.setTotal(cursor.getDouble(6));
                sell.setDiaSemana(cursor.getString(7));
                sell.setPlazo(cursor.getString(8));
                sell.setPagado(cursor.getInt(9)>0);
                sell.setSincronizado(true);
                aVentas.add(sell);
            }while (cursor.moveToNext());
        return aVentas;
    }

    public void actualizarSincronizado(Integer id, String tabla,Context context){
        SQLiteDatabase db = getWritableDatabase();
        String campo = obtenerCampo(tabla);
        Log.d(TAG, "actualizarSincronizado: "+campo);
        ContentValues sincronizar = new ContentValues();
        sincronizar.put("sincronizado",1);
        try{
            db.update(tabla,sincronizar,campo + " = " + id,null);
        }catch (SQLiteException ex){
            Toast.makeText(context, "Error al sincronizar: "+ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private String obtenerCampo(String tabla){
        String campo = "";
        switch (tabla){
            case venta:
                campo = "idVenta";
                break;
            case clienteT:
                campo = "idCliente";
                break;
            case pago:
                campo = "idPago";
                break;
            case ropa:
                campo = "idRopa";
                break;
            case lugares:
                campo = "idLugar";
                break;
            case prenda:
                campo = "id";
                break;
        }
        return campo;
    }
}