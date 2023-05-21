package com.example.sameinfarm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class formula extends AppCompatActivity {
    EditText inp_medicamento;
    Button btn_solicitar;
    DatabaseReference medicamentoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula);

        inp_medicamento = findViewById(R.id.inp_medicamento);
        btn_solicitar = findViewById(R.id.btn_solicitar);

        medicamentoRef = FirebaseDatabase.getInstance().getReference("Medicamento");

        btn_solicitar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String medicamentoId = inp_medicamento.getText().toString().trim();
                if (!medicamentoId.isEmpty()) {
                    checkMedicamentoExistence(medicamentoId);
                } else {
                    Toast.makeText(formula.this, "Ingrese el ID del medicamento", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkMedicamentoExistence(final String medicamentoId) {
        medicamentoRef.child(medicamentoId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    Toast.makeText(formula.this, "¡La fórmula del medicamento está en el sistema!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(formula.this, "El medicamento no existe en la base de datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(formula.this, "Error al consultar la base de datos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
