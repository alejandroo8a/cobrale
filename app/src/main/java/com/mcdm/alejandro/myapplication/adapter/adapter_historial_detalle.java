package com.mcdm.alejandro.myapplication.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mcdm.alejandro.myapplication.R;
import com.mcdm.alejandro.myapplication.clases.HISTORIAL;
import com.mcdm.alejandro.myapplication.clases.prendas;

import java.util.ArrayList;

/**
 * Created by AlejandroMissael on 25/03/2017.
 */

public class adapter_historial_detalle extends ArrayAdapter<prendas> {

    static private final String TAG ="adapter_historial_det";

    public adapter_historial_detalle(Context context, ArrayList<prendas> resource) {
        super(context, 0,resource);
    }

    static class ViewHolder{
        EditText edtTipoPrendaCard;
        EditText edtDescripcionCard;
        EditText edtCantidadCard;
        EditText edtCostoCard;
        Button btnModificarHistorial, btnEliminarPrenda;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        adapter_historial_detalle.ViewHolder holder;
        final prendas prenda = getItem(position);
        convertView = null;
        if(null==convertView){
            convertView =inflater.inflate(R.layout.item_detalle_historial,parent,false);
            holder = new adapter_historial_detalle.ViewHolder();
            holder.edtTipoPrendaCard = (EditText) convertView.findViewById(R.id.edtTipoPrendaCard);
            holder.edtDescripcionCard = (EditText) convertView.findViewById(R.id.edtDescripcionCard);
            holder.edtCantidadCard = (EditText) convertView.findViewById(R.id.edtCantidadCard);
            holder.edtCostoCard = (EditText) convertView.findViewById(R.id.edtCostoCard);
            holder.btnModificarHistorial = (Button) convertView.findViewById(R.id.btnModificarHistorial);
            holder.btnEliminarPrenda = (Button) convertView.findViewById(R.id.btnEliminarPrenda);
            convertView.setTag(holder);
        }else
            holder = (adapter_historial_detalle.ViewHolder)convertView.getTag();
        holder.edtTipoPrendaCard.setText(prenda.getTipoPrenda().toString());
        holder.edtDescripcionCard.setText(prenda.getDescripccion().toString());
        holder.edtCantidadCard.setText(prenda.getCantidad().toString());
        holder.edtCostoCard.setText(String.valueOf(prenda.getCosto()));
        holder.btnModificarHistorial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: "+prenda.getIdNube());
            }
        });

        holder.btnEliminarPrenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        return convertView;
    }
}
