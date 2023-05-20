package com.example.sameinfarm;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class medicamentos extends AppCompatActivity {
    EditText codMedicina, nombreMedicina, dosisMedicina, mgMedicina, mesMedicina, cantidadMedicina;
    ImageButton btn_delete, btn_update, btn_plus;
    ListView lvDatos;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);

        codMedicina = findViewById(R.id.codMedicina);
        nombreMedicina = findViewById(R.id.nombreMedicina);
        dosisMedicina = findViewById(R.id.dosisMedicina);
        mgMedicina = findViewById(R.id.mgMedicina);
        mesMedicina = findViewById(R.id.mesMedicina);
        cantidadMedicina = findViewById(R.id.cantidadMedicina);
        btn_delete = findViewById(R.id.btn_delete);
        btn_update = findViewById(R.id.btn_update);
        btn_plus = findViewById(R.id.btn_plus);
        lvDatos = findViewById(R.id.lvDatos);


        modificar();
        registrar();
        eliminar();
    } //cierre Oncreate

    private void registrar() {
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codMedicina.getText().toString().trim().isEmpty() ||
                        nombreMedicina.getText().toString().trim().isEmpty() ||
                        dosisMedicina.getText().toString().trim().isEmpty() ||
                        mgMedicina.getText().toString().trim().isEmpty() ||
                        mesMedicina.getText().toString().trim().isEmpty() ||
                        cantidadMedicina.getText().toString().trim().isEmpty())
                {
                    ocultarTeclado();
                    Toast.makeText(medicamentos.this, "complete los campos faltantes", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String cod = codMedicina.getText().toString();
                    String nombre = nombreMedicina.getText().toString();
                    String dosis = dosisMedicina.getText().toString();
                    String mg = mgMedicina.getText().toString();
                    String mes = mesMedicina.getText().toString();
                    String cantidad = cantidadMedicina.getText().toString();

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference dbref = db.getReference("Medicamento");

                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            Medicamento med = new Medicamento(cod,nombre,dosis,mg,mes,cantidad);
                            dbref.push().setValue(med);
                            ocultarTeclado();
                            Toast.makeText(medicamentos.this, "El medicamento se ingresó correctamente", Toast.LENGTH_SHORT).show();
                            limpiar();
                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }//cierra if/else inicial

            }
        });
    }
    //cierre registro

    private void eliminar()
    {
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    //cierre eliminar

    private void modificar()
    {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String medicamentoId = codMedicina.getText().toString();
                String nombre = nombreMedicina.getText().toString();
                String dosis = dosisMedicina.getText().toString();
                String mg = mgMedicina.getText().toString();
                String mes = mesMedicina.getText().toString();
                String cantidad = cantidadMedicina.getText().toString();

            }
        });
    }
    //cierre modificar

    private void ocultarTeclado(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    } // Cierra el método ocultarTeclado.

    private void limpiar()
    {
        codMedicina.setText("");
        nombreMedicina.setText("");
        dosisMedicina.setText("");
        mgMedicina.setText("");
        mesMedicina.setText("");
        cantidadMedicina.setText("");
    }



}//Cierra la clase

