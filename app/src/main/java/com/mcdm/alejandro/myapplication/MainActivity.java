package com.mcdm.alejandro.myapplication;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mcdm.alejandro.myapplication.SQLite.SQLCobrale;

public class MainActivity extends AppCompatActivity {
    SQLCobrale db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentTransaction tx =getSupportFragmentManager().beginTransaction();
        db = new SQLCobrale(getApplicationContext());
        tx.replace(R.id.activity_main,new cobroHoy());
        tx.commit();
    }
}
