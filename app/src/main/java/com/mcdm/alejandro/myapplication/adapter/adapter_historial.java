package com.mcdm.alejandro.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mcdm.alejandro.myapplication.R;
import com.mcdm.alejandro.myapplication.clases.HISTORIAL;

import java.util.ArrayList;

/**
 * Created by alejandro on 10/01/17.
 */

public class adapter_historial extends ArrayAdapter<HISTORIAL>{
    static private final String TAG ="adapter_historial";

    public adapter_historial(Context context, ArrayList<HISTORIAL> resource) {
        super(context, 0,resource);
    }

    static class ViewHolder{
        TextView txtNombreCliente;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        HISTORIAL cliente = getItem(position);
        convertView = null;
        if(null==convertView){
            convertView =inflater.inflate(R.layout.item_cliente,parent,false);
            holder = new ViewHolder();
            holder.txtNombreCliente = (TextView)convertView.findViewById(R.id.txtNombreCliente);
            convertView.setTag(holder);
        }else
            holder = (ViewHolder)convertView.getTag();
        holder.txtNombreCliente.setText(cliente.getNombre().toString());
        return convertView;
    }
}
