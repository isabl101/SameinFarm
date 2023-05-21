package com.example.sameinfarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class menu extends AppCompatActivity {
Button btn_contactos, btn_regisCognitivo, btn_Medicamentos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btn_contactos = findViewById(R.id.btn_contactos);
        btn_regisCognitivo = findViewById(R.id.btn_regisCognitivo);
        btn_Medicamentos = findViewById(R.id.btn_Medicamentos);
    }

    public void ir_contactos(View view){
        Intent intcontactos=new Intent(menu.this, emergencia.class);
        startActivity(intcontactos);
    }

    public void ir_registros(View view){
        Intent intregistro=new Intent(menu.this, registroCognitivo.class);
        startActivity(intregistro);
    }

    public void ir_medicina(View view){
        Intent intmedicina=new Intent(menu.this, medicamentos.class);
        startActivity(intmedicina);
    }
}