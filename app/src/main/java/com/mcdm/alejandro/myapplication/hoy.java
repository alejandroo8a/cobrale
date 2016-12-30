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
import android.widget.ImageView;
import android.widget.TextView;

import com.mcdm.alejandro.myapplication.SQLite.SQLCobrale;
import com.mcdm.alejandro.myapplication.adapter.adapter_deben;
import com.mcdm.alejandro.myapplication.clases.DEBEN;

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

    private ArrayList<DEBEN> listaDeben;
    private adapter_deben adapter_deben;
    private SQLCobrale db;
    private String fecha="";

    public hoy() {
        // Required empty public constructor
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
        listaDeben = new ArrayList<>();
        db = new SQLCobrale(getContext());
        llenarListaDeben();
        llenarGrid();
    }

    private void llenarListaDeben(){
        fechaHoy();
        listaDeben = db.getDebenHoy(fecha);
    }

    private void llenarGrid(){
        if(listaDeben.size()>0){
            imgDescanso.setVisibility(View.INVISIBLE);
            txtNoHayCobrar.setVisibility(View.INVISIBLE);
            adapter_deben = new adapter_deben(getContext(),listaDeben);
            grdCobrarHoy.setAdapter(adapter_deben);
        }
    }

    private void fechaHoy(){
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Calendar calendar = Calendar.getInstance();
        fecha=format.format(calendar.getTime());
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
