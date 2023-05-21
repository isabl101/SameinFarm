package com.example.sameinfarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class consejos extends AppCompatActivity {
    Button btn_regreso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consejos);

        btn_regreso = findViewById(R.id.btn_regreso);
        regreso();
    }

    public void regreso() {
        btn_regreso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intcontact = new Intent(consejos.this, inicioSesion.class);
                startActivity(intcontact);
            }
        });
    }
}