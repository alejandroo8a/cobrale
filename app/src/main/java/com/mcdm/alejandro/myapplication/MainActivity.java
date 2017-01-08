package com.mcdm.alejandro.myapplication;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mcdm.alejandro.myapplication.SQLite.SQLCobrale;

public class MainActivity extends AppCompatActivity {
    SQLCobrale db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        verificarLlamadas();


        FragmentTransaction tx =getSupportFragmentManager().beginTransaction();
        db = new SQLCobrale(getApplicationContext());
        tx.replace(R.id.activity_main,new cobroHoy());
        tx.commit();

    }

    //1 VERIFICAR EL PERMISO
    private void verificarLlamadas(){
        int permiso = checkSelfPermission(Manifest.permission.CALL_PHONE);
        if(permiso != PackageManager.PERMISSION_GRANTED)
            solicitarPermiso();

    }

    //2 SOLICITAR EL PERMISO
    private void solicitarPermiso(){
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CALL_PHONE)){
            abrirConfiguracion();
        }else{
            requestPermissions(new String[]{Manifest.permission.CALL_PHONE},0);
        }
    }

    //3 PROCESAR LA RESPUESTA

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 0)
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                SQLCobrale db = new SQLCobrale(getApplicationContext());
                db.insertRazonSocial("Escuela",getApplicationContext());
                db.insertRopa("Pantalon",getApplicationContext());
                db.insertRopa("Blusa",getApplicationContext());
            }
            else
                abrirConfiguracion();
                
    }

    private void abrirConfiguracion(){
        Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + getPackageName()));
        startActivity(intent);
    }
}
