package com.mcdm.alejandro.myapplication.SQLite;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mcdm.alejandro.myapplication.clases.cliente;

import java.util.List;

/**
 * Created by alejandro on 14/01/17.
 */

public class firebase {
    FirebaseDatabase fb;
    SQLCobrale db;
    DatabaseReference myRef;
    Context context;

    public firebase(){
    }

    public firebase(Context context){
        fb=FirebaseDatabase.getInstance();
        db= new SQLCobrale(context);
        this.context=context;
    }

    public void guardarClientes(){
        List<cliente> clientes = db.getCliente(context);
        if(!clientes.isEmpty()){
            myRef = fb.getReference("cliente");
            for(cliente cl : clientes){
               // myRef.child(cl.getId().toString()).setValue(new cliente(cl.getNombre(),cl.getCalle(),cl.getColonia(),cl.getTelefono1(),cl.getTelefono2(),cl.isActivo(),cl.getRazonSocial(),cl.isSincronizado())  );
                myRef.child(cl.getId().toString()).setValue(new cliente(cl.getNombre(), cl.getCalle(), cl.getColonia(), cl.getTelefono1(), cl.getTelefono2(), cl.isActivo(), cl.getRazonSocial(), cl.isSincronizado()), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        //AQUI SE LLENARA LA BARRA DE PROGRESO
                    }
                });

            }
            Toast.makeText(context, "Clientes respaldados", Toast.LENGTH_SHORT).show();
        }else
            Toast.makeText(context, "No hay nada que respaldar", Toast.LENGTH_SHORT).show();
    }


}
