package com.mcdm.alejandro.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mcdm.alejandro.myapplication.SQLite.SQLCobrale;
import com.mcdm.alejandro.myapplication.adapter.adapter_deben;
import com.mcdm.alejandro.myapplication.clases.DEBEN;
import com.mcdm.alejandro.myapplication.clases.pagos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link hoy.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class hoy extends Fragment {

    private OnFragmentInteractionListener mListener;
    static private final String TAG ="hoy";

    private GridView grdCobrarHoy;
    private ImageView imgDescanso;
    private TextView txtNoHayCobrar;
    private Spinner spPlazoAbono;

    private ArrayList<DEBEN> listaDeben;
    private adapter_deben adapter_deben;
    private SQLCobrale db;
    private String fechaCobro="", fechaPago="";
    private Double total = 0.0, resto = 0.0;

    public hoy() {
        // Required empty public constructor
        listaDeben = new ArrayList<>();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_hoy, container, false);
        grdCobrarHoy = (GridView)v.findViewById(R.id.grdCobrarHoy);
        imgDescanso = (ImageView)v.findViewById(R.id.imgDescanso);
        txtNoHayCobrar = (TextView)v.findViewById(R.id.txtNoHayCobrar);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new SQLCobrale(getContext());
        listaDeben = new ArrayList<>();
        try {
            llenarListaDeben();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        llenarGrid();
        grdCobrarHoy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                crearDialogPago(i);
            }
        });
    }

    public void llenarListaDeben() throws ParseException {
        listaDeben = db.getDebenHoy();
    }

    public void llenarGrid(){
        if(listaDeben.size()>0){
            imgDescanso.setVisibility(View.INVISIBLE);
            txtNoHayCobrar.setVisibility(View.INVISIBLE);
            adapter_deben = new adapter_deben(getContext(),listaDeben);
            grdCobrarHoy.setAdapter(adapter_deben);
        }
    }

    private void crearDialogPago(final int posicion){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final double comparar =Double.parseDouble(listaDeben.get(posicion).getResto());
        final View view = inflater.inflate(R.layout.dialog_abono,null);
        final EditText edtCantidadAbono = (EditText)view.findViewById(R.id.edtCantidadAbono);
        spPlazoAbono = (Spinner)view.findViewById(R.id.spPlazoAbono);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.Plazo, R.layout.spinner_item);
        spPlazoAbono.setAdapter(adapter);
        builder.setView(view);
        builder.setPositiveButton("Abonar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        })
        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (edtCantidadAbono.getText().toString().length() > 0) {
                    if(Double.parseDouble(edtCantidadAbono.getText().toString())<=comparar) {
                        if(spPlazoAbono.getSelectedItem().equals("Liquidar")) {
                            if(Double.parseDouble(edtCantidadAbono.getText().toString())==comparar){
                                sumarFecha();
                                resto = comparar - Double.parseDouble(edtCantidadAbono.getText().toString());
                                total = listaDeben.get(posicion).getTotal();
                                pagos pago = new pagos();
                                pago.setMonto(Double.parseDouble(edtCantidadAbono.getText().toString()));
                                pago.setResto(resto);
                                pago.setTotal(total);
                                pago.setFechaCobro(fechaCobro);
                                pago.setFechaPago(fechaPago);
                                pago.setActivo(true);
                                pago.setSincronizado(false);
                                alert.dismiss();
                                db.updatePago(listaDeben.get(posicion).getId(), getContext());
                                db.insertPago(listaDeben.get(posicion).getId(), pago, getContext());
                                if (resto == 0.0)
                                    db.updateVenta(listaDeben.get(posicion).getId(), getContext());
                                listaDeben.clear();
                                try {
                                    llenarListaDeben();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if (listaDeben.size() > 0)
                                    llenarGrid();
                                else {
                                    imgDescanso.setVisibility(View.VISIBLE);
                                    txtNoHayCobrar.setVisibility(View.VISIBLE);
                                    grdCobrarHoy.setVisibility(View.INVISIBLE);
                                }
                            }
                                else{
                                Toast.makeText(getContext(), "El abono debe de ser igual a la cantidad a liquidar", Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            sumarFecha();
                            resto = comparar - Double.parseDouble(edtCantidadAbono.getText().toString());
                            total = listaDeben.get(posicion).getTotal();
                            pagos pago = new pagos();
                            pago.setMonto(Double.parseDouble(edtCantidadAbono.getText().toString()));
                            pago.setResto(resto);
                            pago.setTotal(total);
                            pago.setFechaCobro(fechaCobro);
                            pago.setFechaPago(fechaPago);
                            pago.setActivo(true);
                            pago.setSincronizado(false);
                            alert.dismiss();
                            db.updatePago(listaDeben.get(posicion).getId(), getContext());
                            db.insertPago(listaDeben.get(posicion).getId(), pago, getContext());
                            if (resto == 0.0)
                                db.updateVenta(listaDeben.get(posicion).getId(), getContext());
                            listaDeben.clear();
                            try {
                                llenarListaDeben();
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if (listaDeben.size() > 0)
                                llenarGrid();
                            else {
                                imgDescanso.setVisibility(View.VISIBLE);
                                txtNoHayCobrar.setVisibility(View.VISIBLE);
                                grdCobrarHoy.setVisibility(View.INVISIBLE);
                            }
                        }
                    }else
                        Toast.makeText(getContext(), "El abono no puede ser mayor al resto del pago", Toast.LENGTH_SHORT).show();
                }else
                    Toast.makeText(getContext(), "Agregue la cantidad a abonar", Toast.LENGTH_SHORT).show();
            }

        });

    }

    private void sumarFecha(){
        int entrar = spPlazoAbono.getSelectedItemPosition();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Calendar calendar = Calendar.getInstance();
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


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
