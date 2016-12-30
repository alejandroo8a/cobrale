package com.mcdm.alejandro.myapplication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.mcdm.alejandro.myapplication.SQLite.SQLCobrale;
import com.mcdm.alejandro.myapplication.adapter.adapter_deben;
import com.mcdm.alejandro.myapplication.clases.DEBEN;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link deben.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class deben extends Fragment {

    static private final String TAG ="deben";

    private OnFragmentInteractionListener mListener;
    private GridView grdDeben;
    private TextView txtDeben;

    private ArrayList<DEBEN> listaDeben;
    private adapter_deben adapter_deben;
    private SQLCobrale db;

    public deben() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_deben, container, false);
        grdDeben = (GridView)v.findViewById(R.id.grdDeben);
        txtDeben = (TextView)v.findViewById(R.id.txtDeben);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listaDeben = new ArrayList<>();
        db = new SQLCobrale(getContext());
        llenarListaDeben();
        llenarDatagridDeben();

    }

    private void llenarListaDeben(){
        listaDeben = db.getDeben();
    }

    private void llenarDatagridDeben(){
        if(listaDeben.size()>0){
            txtDeben.setVisibility(View.INVISIBLE);
            adapter_deben = new adapter_deben(getContext(),listaDeben);
            grdDeben.setAdapter(adapter_deben);
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
    /*adapterProducto = new adapter_producto(getApplicationContext(), listaPrendas);
    params.height += 100;
    grdProductos.setLayoutParams(params);
    grdProductos.setAdapter(adapterProducto);*/
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
