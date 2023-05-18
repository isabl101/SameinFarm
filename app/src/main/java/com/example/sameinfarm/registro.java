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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.ktx.Firebase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class registro extends AppCompatActivity {

    EditText idUsuario, inpNombres, inpApellidos, inpCorreo,inpMedico, inpDiagnostico, inpContrasena, inpContrasena2;
    Button btn_registro;
    FirebaseAuth mAuth;
    FirebaseFirestore nFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        idUsuario = findViewById(R.id.idUsuario);
        inpNombres = findViewById(R.id.inpNombres);
        inpApellidos =findViewById(R.id.inpApellidos);
        inpCorreo = findViewById(R.id.inpCorreo);
        inpMedico = findViewById(R.id.inpMedico);
        inpDiagnostico = findViewById(R.id.inpDiagnostico);
        inpContrasena = findViewById(R.id.inpContrasena);
        inpContrasena2 = findViewById(R.id.inpContrasena2);
        btn_registro = findViewById(R.id.btn_registro);
        mAuth = FirebaseAuth.getInstance();
        nFirestore = FirebaseFirestore.getInstance();

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrar();
            }
        });

    }

    private void registrar()
    {
        String document = idUsuario.getText().toString();
        String name = inpNombres.getText().toString();
        String lastName = inpApellidos.getText().toString();
        String email = inpCorreo.getText().toString();
        String doctor = inpMedico.getText().toString();
        String diagnostic = inpDiagnostico.getText().toString();
        String password = inpContrasena.getText().toString();
        String confirmPassword = inpContrasena2.getText().toString();

        if (!document.isEmpty() && !name.isEmpty() && !lastName.isEmpty() && !email.isEmpty() && !doctor.isEmpty() && !diagnostic.isEmpty() && !password.isEmpty() && !confirmPassword.isEmpty())
        {

            if (isEmailValid(email))
            {
                if (password.equals(confirmPassword))
                {
                    if(password.length() >=6)
                    {
                        createUser(name, lastName, email, doctor, diagnostic, password);
                    }
                    else
                    {
                        Toast.makeText(registro.this, "La contraseña debe contener al menos 6 caracteres", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(registro.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(registro.this, "El correo no es válido", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(registro.this, "Es necesario llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void createUser(String name, String lastName, String email, String doctor, String diagnostico, String password)
    {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    String id = mAuth.getCurrentUser().getUid();
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("lastName", lastName);
                    map.put("doctor", doctor);
                    map.put("diagnostico", diagnostico);
                    map.put("email", email);
                    nFirestore.collection("Users").document(id).set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Intent i = new Intent(registro.this, inicioSesion.class);
                                startActivity(i);
                                Toast.makeText(registro.this, "El usuario ha sido registrado", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(registro.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private boolean isEmailValid(String email) {
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}