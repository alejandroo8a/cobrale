package com.mcdm.alejandro.myapplication;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mcdm.alejandro.myapplication.SQLite.SQLCobrale;
import com.mcdm.alejandro.myapplication.adapter.adapter_producto;
import com.mcdm.alejandro.myapplication.clases.pagos;
import com.mcdm.alejandro.myapplication.clases.prendas;
import com.mcdm.alejandro.myapplication.clases.ventas;

import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class venta extends AppCompatActivity {
    private static final String TAG = "venta";

    private Spinner spPlazo, spDia;
    private Button btnAgregarProducto, btnVender;
    private GridView grdProductos;
    private adapter_producto adapterProducto;
    private ViewGroup.LayoutParams params;
    private TextView txtTotalPrendas, txtSubTotal, txtTotal, txtClienteVenta, txtFecha;
    private EditText edtAbono;
    private Spinner spTipoPrenda;

    private ArrayList<prendas> listaPrendas;
    private List<String> listaRopa;
    private String tipo, descripcion, fechaCobro="";
    private double costo,subTotal=0.0, total= 0.0, abono=0.0;
    private Integer cantidad=0, idProducto=0, idPago=0, totalPrendas=0, id=0;
    private SharedPreferences sharedPreferences;
    private SQLCobrale db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venta);
        db = new SQLCobrale(this);
        spPlazo = (Spinner)findViewById(R.id.spPlazo);
        spDia = (Spinner)findViewById(R.id.spDia);
        btnAgregarProducto = (Button)findViewById(R.id.btnAgregarProducto);
        btnVender = (Button)findViewById(R.id.btnVender);
        grdProductos = (GridView)findViewById(R.id.grdProductos);
        txtTotalPrendas = (TextView)findViewById(R.id.txtTotalPrendas);
        txtSubTotal = (TextView)findViewById(R.id.txtSubTotal);
        txtTotal = (TextView)findViewById(R.id.txtTotal);
        txtClienteVenta = (TextView)findViewById(R.id.txtClienteVenta);
        txtFecha = (TextView)findViewById(R.id.txtFecha);
        edtAbono = (EditText)findViewById(R.id.edtAbono);
        txtClienteVenta.setText(getIntent().getStringExtra("NOMBRE"));
        id=getIntent().getIntExtra("IDCLIENTE",0);
        fechaHoy();
        listaPrendas = new ArrayList<>();
        poblarSpinner();
        createProducto(null);
        edtAbono.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Total();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        txtFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogFecha();
            }
        });
        
        grdProductos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                eliminarPrenda(i);
            }
        });
    }

    private void poblarSpinner(){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Plazo, R.layout.spinner_item);
        spPlazo.setAdapter(adapter);
        adapter=null;
        adapter = ArrayAdapter.createFromResource(this, R.array.Semana, R.layout.spinner_item);
        spDia.setAdapter(adapter);
    }

    //AQUI VAN LAS FUNCIONES DEL DIALOG QUE SE CREA PARA AGREGAR PRODUCTOS
    public void createProducto(View x){
        params = grdProductos.getLayoutParams();
        final AlertDialog.Builder builder = new AlertDialog.Builder(venta.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View v = inflater.inflate(R.layout.dialog_agregar_producto,null);
        spTipoPrenda = (Spinner)v.findViewById(R.id.spTipoPrenda);
        final EditText edtDescripcion = (EditText)v.findViewById(R.id.edtDescripcion);
        final EditText edtPrecio = (EditText)v.findViewById(R.id.edtPrecio);
        final EditText edtCantidad = (EditText)v.findViewById(R.id.edtCantidad);
        edtPrecio.setFocusable(true);
        llenarSpinnerRopa();
        builder.setView(v);
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setNeutralButton("Agregar tipo Prenda", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        //SE UTILIZA PARA QUE NO SE CANCELE AL MOMENTO DE PRECIONAR ALGÚN BOTON
        final AlertDialog dialog = builder.create();
        dialog.show();
        //NO SE PUEDE UN CLIC LARGO EN EL SPINNER
        /*spTipoPrenda.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                final String prendaEliminar = spTipoPrenda.getItemAtPosition(1).toString();
                AlertDialog.Builder alert = new AlertDialog.Builder(venta.this);
                alert.setTitle("Eliminar")
                        .setMessage("¿Desea eliminar "+spTipoPrenda.getItemAtPosition(1)+" ?")
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                db.deleteRopa(prendaEliminar,getApplicationContext());
                                llenarSpinnerRopa();
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                return true;
            }
        });*/
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Boolean wantToCloseDialog = false;
                if(edtPrecio.length() ==0){
                    Toast.makeText(venta.this, "Agregue precio al producto", Toast.LENGTH_SHORT).show();
                }
                else {
                    wantToCloseDialog=true;
                    tipo = spTipoPrenda.getSelectedItem().toString();
                    descripcion = edtDescripcion.getText().toString();
                    cantidad = Integer.parseInt(edtCantidad.getText().toString());
                    costo = Double.parseDouble(edtPrecio.getText().toString());
                    prendas prenda = new prendas();
                    prenda.setTipoPrenda(tipo);
                    prenda.setDescripccion(descripcion);
                    prenda.setCantidad(cantidad);
                    prenda.setCosto(costo*cantidad);//es el total
                    prenda.setSincronizado(false);
                    listaPrendas.add(prenda);
                    adapterProducto = new adapter_producto(getApplicationContext(), listaPrendas);
                    params.height += 100;
                    grdProductos.setLayoutParams(params);
                    grdProductos.setAdapter(adapterProducto);
                }
                if(wantToCloseDialog){
                    saberTotalPrendas();
                    txtTotalPrendas.setText(String.valueOf(totalPrendas));
                    SubTotal(costo, cantidad,true);
                    dialog.dismiss();
                }
            }
        });
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogRopa();
            }
        });
    }

    private void dialogRopa(){
        final AlertDialog.Builder builder = new AlertDialog.Builder(venta.this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View  view = inflater.inflate(R.layout.dialog_razon_social,null);
        TextView txtRazonSocial = (TextView)view.findViewById(R.id.txtRazonSocial);
        final EditText edtRazon = (EditText)view.findViewById(R.id.edtRazon);
        final ImageView ivRazon = (ImageView)view.findViewById(R.id.iv_imagenRazon);
        ivRazon.setImageDrawable(getDrawable(R.drawable.ic_ropa));
        txtRazonSocial.setText("Agregar tipo de prenda");
        edtRazon.setHint("Tipo prenda");
        builder.setView(view);
        builder.setPositiveButton("Agregar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.insertRopa(edtRazon.getText().toString(),getApplicationContext());
                llenarSpinnerRopa();
            }
        })
        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
        .show();
    }

    private void llenarSpinnerRopa(){
        listaRopa = null;
        listaRopa = db.getRopa();
        //PARA CREAR SPINNERS DESDE LOS RECURSOS DEL SISTEMA EN STRING
        //ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(),listaRopa, R.layout.spinner_item);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item,listaRopa);
        spTipoPrenda.setAdapter(adapter);
    }

    public void hacerVenta(View v){
        if(edtAbono.getText().toString().length()==0)
            edtAbono.setText("0.0");
        if(Double.parseDouble(edtAbono.getText().toString()) > subTotal)
            Toast.makeText(this, "El abono no puede ser mayor al total", Toast.LENGTH_SHORT).show();
        else if(listaPrendas.size()==0) {
            Toast.makeText(this, "Agrega una prenda a vender", Toast.LENGTH_SHORT).show();
            edtAbono.setText("");
        }
        else{
            obtenerIdProducto();
            obtenerIdPago();
            insertarPrendas();
            sumarFecha();
            insertarPago();
            insertarVenta();
            finish();
        }
    }

    private void SubTotal(double sub,int cantidad, boolean operacion){
        if(operacion)
            subTotal+=sub*cantidad;
        else
            subTotal-=sub*cantidad;
        txtSubTotal.setText(String.valueOf(subTotal));
        txtTotal.setText(String.valueOf(subTotal));
    }

    private void Total(){
        if(edtAbono.length()==0)
            abono=0.0;
        else
            abono = Double.parseDouble(edtAbono.getText().toString());
        total=subTotal-abono;
        txtTotal.setText(String.valueOf(total));
    }

    private void dialogFecha(){
        final Calendar c = Calendar.getInstance();
        Integer mDay = c.get(Calendar.DAY_OF_MONTH);
        Integer mMonth = c.get(Calendar.MONTH);
        Integer mYear = c.get(Calendar.YEAR);
        DatePickerDialog dpd = new DatePickerDialog(venta.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        String fechaFormateada=dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
                        try {
                            Date fecha = simpleDateFormat.parse(fechaFormateada);
                            fechaFormateada = simpleDateFormat.format(fecha);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        txtFecha.setText(fechaFormateada);

                    }
                },mYear , mMonth, mDay );
        dpd.show();
    }

    private void sumarFecha(){
        int entrar = spPlazo.getSelectedItemPosition();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        Date fecha = null;
        try{
            fecha = format.parse(txtFecha.getText().toString());
            calendar.setTime(fecha);
        }catch (ParseException ex){
            Toast.makeText(this, "Error al parsear la fecha", Toast.LENGTH_SHORT).show();
        }
        switch (entrar){
            case 0:
                //1 semana
                calendar.add(Calendar.DAY_OF_YEAR, 7);
                break;
            case 1:
                //2 semanas
                calendar.add(Calendar.DAY_OF_YEAR, 14);
                break;
            case 2:
                // 3 semanas
                calendar.add(Calendar.DAY_OF_YEAR, 21);
                break;
            case 3:
                // 4 semanas;
                calendar.add(Calendar.MONTH, 1);
                break;
            default:
                //al contado
                break;
        }

        fechaCobro = format.format(calendar.getTime());
        Log.d(TAG, "FECHA A COBRAR "+fechaCobro);
    }

    private void fechaHoy(){
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
        txtFecha.setText(format.format(calendar.getTime()));
    }

    private void eliminarPrenda(final int pos){
        AlertDialog.Builder alert = new AlertDialog.Builder(venta.this);
        alert.setTitle("Eliminar")
                .setIcon(R.drawable.ic_close)
                .setMessage("¿Desea eliminar la prenda?")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        costo=listaPrendas.get(pos).getCosto();
                        cantidad = listaPrendas.get(pos).getCantidad();
                        totalPrendas -= listaPrendas.get(pos).getCantidad();
                        listaPrendas.remove(pos);
                        adapterProducto = new adapter_producto(getApplicationContext(), listaPrendas);
                        params.height -= 100;
                        grdProductos.setLayoutParams(params);
                        grdProductos.setAdapter(adapterProducto);
                        Toast.makeText(venta.this, "Prenda eliminada", Toast.LENGTH_SHORT).show();
                        txtTotalPrendas.setText(String.valueOf(totalPrendas));
                        SubTotal(costo, cantidad, false);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }

    private void obtenerIdProducto(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(venta.this);
        idProducto = sharedPreferences.getInt("IDPRODUCTO",0);
        idProducto++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("IDPRODUCTO",idProducto);
        editor.commit();
    }

    private void obtenerIdPago(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(venta.this);
        idPago = sharedPreferences.getInt("IDPAGO",0);
        idPago++;
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("IDPAGO",idPago);
        editor.commit();

    }

    private void insertarPrendas(){
        db.insertPrendas(idProducto, listaPrendas,this);
    }

    private void insertarPago() {
        double resto = db.getPagoExiste(id, getApplicationContext(), true);
        pagos p = new pagos();
        p.setMonto(abono);
        p.setTotal(subTotal);
        p.setFechaPago(txtFecha.getText().toString());
        p.setFechaCobro(fechaCobro);
        p.setActivo(true);
        p.setSincronizado(false);
        if(resto!=0.0)
            p.setResto(Double.valueOf(txtTotal.getText().toString())+resto);
        else
            p.setResto(Double.valueOf(txtTotal.getText().toString()));
        db.insertPago(idPago,p,venta.this);
    }

    private void insertarVenta(){
        ventas sell = new ventas();
        sell.setIdCliente(id);
        sell.setIdProductos(idProducto);
        sell.setIdPagos(idPago);
        sell.setFechaVenta(txtFecha.getText().toString());
        sell.setPrendasTotal(String.valueOf(totalPrendas));
        sell.setTotal(subTotal);
        sell.setDiaSemana(spDia.getSelectedItem().toString());
        sell.setPlazo(spPlazo.getSelectedItem().toString());
        if(spPlazo.getSelectedItemPosition() != 4)
            sell.setPagado(false);
        else
            sell.setPagado(true);
        sell.setSincronizado(false);
        db.insertVenta(sell,this);
    }

    private void saberTotalPrendas(){
        totalPrendas=0;
        for (int i = 0 ; i<listaPrendas.size() ; i++)
            totalPrendas+=listaPrendas.get(i).getCantidad();
    }



}

