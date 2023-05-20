package com.example.sameinfarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void ir_registro(View view){
        Intent intregistro=new Intent(this,registro.class);
        startActivity(intregistro);
    }

    public void ir_inicioSesion(View view){
        Intent intinicioSesion=new Intent(this,inicioSesion.class);
        startActivity(intinicioSesion);
    }


}