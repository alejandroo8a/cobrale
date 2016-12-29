package com.mcdm.alejandro.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcdm.alejandro.myapplication.R;
import com.mcdm.alejandro.myapplication.clases.prendas;

import java.util.List;

/**
 * Created by alejandro on 20/12/16.
 */

public class adapter_producto extends ArrayAdapter<prendas> {
    private static String TAG="adapter_producto";

    public adapter_producto(Context context, List<prendas> resource) {
        super(context, 0,resource);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        prendas prenda = getItem(position);
        if(!prenda.getDescripccion().toString().equals("null")) {
            if (null == convertView) {
                //Si no existe, entonces inflarlo
                convertView = inflater.inflate(R.layout.item_producto, parent, false);
                holder = new ViewHolder();
                holder.txtTipo = (TextView) convertView.findViewById(R.id.txtTipo);
                holder.txtNombreProducto = (TextView) convertView.findViewById(R.id.txtNombreProducto);
                holder.txtCantidad = (TextView) convertView.findViewById(R.id.txtCantidad);
                holder.txtPrecioProducto = (TextView) convertView.findViewById(R.id.txtPrecioProducto);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            // Setup.
            holder.txtTipo.setText(prenda.getTipoPrenda());
            holder.txtNombreProducto.setText(prenda.getDescripccion());
            holder.txtCantidad.setText(String.valueOf(prenda.getCantidad()));
            holder.txtPrecioProducto.setText(String.valueOf(prenda.getCosto()));

            return convertView;
        }
        else{
            Log.d(TAG, "NO ENTRE");
        }
        return null;

    }

    static class ViewHolder {
        TextView txtTipo;
        TextView txtCantidad;
        TextView txtNombreProducto;
        TextView txtPrecioProducto;
    }
}
