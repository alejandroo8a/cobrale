package com.mcdm.alejandro.myapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.mcdm.alejandro.myapplication.SQLite.SQLCobrale;
import com.mcdm.alejandro.myapplication.adapter.adapter_historial;
import com.mcdm.alejandro.myapplication.clases.HISTORIAL;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link historial.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class historial extends Fragment {

    private OnFragmentInteractionListener mListener;
    static private final String TAG ="historial";

    private GridView grdDebenHistorial;
    private TextView txtDebenHistorial;


    private ArrayList<HISTORIAL> listaClientes;
    private adapter_historial adapter;
    private SQLCobrale db;


    public historial() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_historial, container, false);
        grdDebenHistorial = (GridView)v.findViewById(R.id.grdDebenHistorial);
        txtDebenHistorial = (TextView)v.findViewById(R.id.txtDebenHistorial);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new SQLCobrale(getContext());
        llenarLista();
        llenarDatagrid();

        grdDebenHistorial.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(),historial_pagos.class);
                intent.putExtra("ID",listaClientes.get(i).getId());
                intent.putExtra("NOMBRE",listaClientes.get(i).getNombre());
                startActivity(intent);
            }
        });
    }

    private void llenarLista(){
        listaClientes = db.getHistorial();
    }

    private void llenarDatagrid(){
        if(!listaClientes.isEmpty()){
            txtDebenHistorial.setVisibility(View.INVISIBLE);
            adapter = new adapter_historial(getContext(),listaClientes);
            grdDebenHistorial.setAdapter(adapter);
        }
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
