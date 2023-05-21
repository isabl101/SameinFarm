package com.example.sameinfarm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class emergencia extends AppCompatActivity {
    Button btn_consejos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencia);

        btn_consejos = findViewById(R.id.btn_consejos);
        consejos();
    }

    public void consejos() {
        btn_consejos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intcontact = new Intent(emergencia.this,consejos.class);
                startActivity(intcontact);
            }
        });
    }
}