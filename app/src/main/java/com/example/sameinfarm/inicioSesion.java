package com.example.sameinfarm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class inicioSesion extends AppCompatActivity {
    EditText correoVal, passVal;
    Button btnIniciar;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_sesion);
        mAuth = FirebaseAuth.getInstance();

        correoVal = findViewById(R.id.correoVal);
        passVal = findViewById(R.id.passVal);
        btnIniciar = findViewById(R.id.btnIniciar);

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String correo = correoVal.getText().toString();
                String contraseña = passVal.getText().toString();
                signIn(correo, contraseña);
            }
        });
    }

    public void signIn(String correo, String contraseña) {
        mAuth.signInWithEmailAndPassword(correo, contraseña).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // El inicio de sesión fue exitoso, redirigir al usuario a la pantalla principal
                    Intent intmedicamentos=new Intent(inicioSesion.this, medicamentos.class);
                    startActivity(intmedicamentos);
                    Toast.makeText(inicioSesion.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    // Error en el inicio de sesión, mostrar mensaje de error
                    Toast.makeText(inicioSesion.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
