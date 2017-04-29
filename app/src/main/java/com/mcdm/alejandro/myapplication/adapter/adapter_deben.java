package com.mcdm.alejandro.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mcdm.alejandro.myapplication.R;
import com.mcdm.alejandro.myapplication.clases.DEBEN;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alejandro on 29/12/16.
 */

public class adapter_deben extends ArrayAdapter<DEBEN> {
    static private final String TAG ="adapter_deben";


    public adapter_deben(Context context, ArrayList<DEBEN> resource) {
        super(context, 0,resource);
    }

    static class ViewHolder{
        TextView txtNombreDeben;
        TextView txtFechaDeben;
        TextView txtResto;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder holder;
        DEBEN deben = getItem(position);
        convertView = null;
        if(!deben.getResto().toString().equals("0")){
            if(null == convertView){
                convertView = inflater.inflate(R.layout.item_deben,parent,false);
                holder = new ViewHolder();
                holder.txtNombreDeben = (TextView)convertView.findViewById(R.id.txtNombreDeben);
                holder.txtFechaDeben = (TextView)convertView.findViewById(R.id.txtFechaDeben);
                holder.txtResto = (TextView)convertView.findViewById(R.id.txtResto);
                convertView.setTag(holder);

            }else{
                holder = (ViewHolder) convertView.getTag();
            }

            holder.txtNombreDeben.setText(deben.getNombre());
            holder.txtFechaDeben.setText(deben.getFecha());
            holder.txtResto.setText("$ "+deben.getResto());
            return convertView;
        }
        return convertView;
    }
}
