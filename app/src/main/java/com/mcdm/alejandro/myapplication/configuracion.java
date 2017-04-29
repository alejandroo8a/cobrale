package com.mcdm.alejandro.myapplication;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mcdm.alejandro.myapplication.SQLite.firebase;


public class configuracion extends Fragment {

    private static final String TAG = "configuracion";

    private Button btnSincronizar, btnRespaldar;

    private firebase fb;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_configuracion, container, false);
        btnRespaldar = (Button)v.findViewById(R.id.btnRespaldar);
        btnSincronizar = (Button)v.findViewById(R.id.btnSincronizar);
        fb = new firebase(getContext());
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        btnRespaldar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fb.respaldo();
            }
        });

        btnSincronizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertaRespaldo();
            }
        });

    }

    private void alertaRespaldo(){
        AlertDialog.Builder alerta = new AlertDialog.Builder(getContext());
        alerta.setTitle("ATENCIÓN")
                .setMessage("Se borrarán todos los datos, ¿está seguro de sincronizar?")
                .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fb.obtenerBaseNube();
                    }
                })
                .setNegativeButton("CANCELAR", null)
                .show();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
