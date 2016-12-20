package com.mcdm.alejandro.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mcdm.alejandro.myapplication.SQLite.SQLCobrale;
import com.mcdm.alejandro.myapplication.clases.cliente;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link clientes.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class clientes extends Fragment {

    private final String TAG = "clientes";
    private FloatingActionButton btnAgregarCliente;
    private TextView txtLista, txtCliente;
    private ImageView imgClientes;
    private ListView lsClientes;
    private SQLCobrale db;

    private ArrayAdapter adaptador;
    private OnFragmentInteractionListener mListener;

    public clientes() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_clientes, container, false);
        btnAgregarCliente = (FloatingActionButton)view.findViewById(R.id.btnAgregarCliente);
        txtLista = (TextView)view.findViewById(R.id.txtLista);
        txtCliente = (TextView)view.findViewById(R.id.txtCliente);
        imgClientes = (ImageView)view.findViewById(R.id.imgClientes);
        lsClientes = (ListView)view.findViewById(R.id.lsClientes);
        return view;
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        db = new SQLCobrale(getContext());
        btnAgregarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), altaCliente.class);
                startActivity(intent);
            }
        });

        lsClientes.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), altaCliente.class);
                Log.d(TAG, "onItemClick: "+lsClientes.getItemAtPosition(i));
                intent.putExtra("NOMBRE", adapterView.getItemAtPosition(i).toString());
                intent.putExtra("ACTUALIZAR",true);
                startActivity(intent);
            }
        });

        lsClientes.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final String nombre = lsClientes.getItemAtPosition(i).toString();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Eliminar")
                        .setMessage("¿Desea eliminar a "+lsClientes.getItemAtPosition(i)+" ?")
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                eliminarCliente(nombre);
                                llenarLista();
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
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        llenarLista();
    }

    private void llenarLista(){
        final List<String> clientes = db.getClientes();
        if(clientes.size() ==0)
            txtLista.setVisibility(View.INVISIBLE);
        else{
            imgClientes.setVisibility(View.INVISIBLE);
            txtCliente.setVisibility(View.INVISIBLE);
            adaptador = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_activated_1,clientes);
            lsClientes.setAdapter(adaptador);
        }
    }

    private void eliminarCliente(String nombre){
        db.deleteCliente(nombre, getContext());
    }
}
