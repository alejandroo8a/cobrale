package com.mcdm.alejandro.myapplication.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcdm.alejandro.myapplication.R;
import com.mcdm.alejandro.myapplication.clases.historial_card;

import java.util.List;

/**
 * Created by AlejandroMissael on 11/03/2017.
 */

public class adapter_cardHistorial extends RecyclerView.Adapter<adapter_cardHistorial.historialViewHolder> {

    public List<historial_card> aItems;

    public static class historialViewHolder extends RecyclerView.ViewHolder {
        // Campos respectivos de un item
        public TextView txtFechaHistorial;
        public TextView txtTotaHistorial;
        public TextView txtCantidadHistorial;
        public RelativeLayout rlHistorialCard;

        public historialViewHolder(View v) {
            super(v);
            txtFechaHistorial = (TextView) v.findViewById(R.id.txtFechaHistorial);
            txtTotaHistorial = (TextView) v.findViewById(R.id.txtTotaHistorial);
            txtCantidadHistorial = (TextView) v.findViewById(R.id.txtCantidadHistorial);
            rlHistorialCard = (RelativeLayout)v.findViewById(R.id.rlHistorialCard);
        }
    }


    public adapter_cardHistorial(List<historial_card> items){
        this.aItems = items;

    }
    @Override
    public adapter_cardHistorial.historialViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_historial, parent, false);
        return new historialViewHolder(v);
    }

    @Override
    public void onBindViewHolder(adapter_cardHistorial.historialViewHolder holder, int position) {
        holder.txtFechaHistorial.setText("Fecha: "+ aItems.get(position).getFecha());
        holder.txtTotaHistorial.setText(String.valueOf(aItems.get(position).getTotal()));
        holder.txtCantidadHistorial.setText(String.valueOf(aItems.get(position).getResto()));
        if(aItems.get(position).getTipo())
            holder.rlHistorialCard.setBackgroundColor(Color.parseColor("#71dc64"));
        else
            holder.rlHistorialCard.setBackgroundColor(Color.parseColor("#e04040"));
    }

    @Override
    public int getItemCount() {
        return aItems.size();
    }
}
