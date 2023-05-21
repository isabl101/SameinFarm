package com.example.sameinfarm;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class medicamentos extends AppCompatActivity {
    EditText codMedicina, nombreMedicina, dosisMedicina, mgMedicina, mesMedicina, cantidadMedicina;
    ImageButton btn_delete, btn_update, btn_plus, btn_buscar;
    Button btn_form;
    ListView lvDatos;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicamentos);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        codMedicina = findViewById(R.id.codMedicina);
        nombreMedicina = findViewById(R.id.nombreMedicina);
        dosisMedicina = findViewById(R.id.dosisMedicina);
        mgMedicina = findViewById(R.id.mgMedicina);
        mesMedicina = findViewById(R.id.mesMedicina);
        cantidadMedicina = findViewById(R.id.cantidadMedicina);
        btn_delete = findViewById(R.id.btn_delete);
        btn_update = findViewById(R.id.btn_update);
        btn_plus = findViewById(R.id.btn_plus);
        btn_buscar = findViewById(R.id.btn_buscar);
        btn_form = findViewById(R.id.btn_form);
        lvDatos = findViewById(R.id.lvDatos);


        modificar();
        registrar();
        eliminar();
        buscar();
        listarMedicamento();
        generarFormula();
        solFormula();
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

                            boolean res = false;
                            for (DataSnapshot x : snapshot.getChildren()){
                                if (x.child("cod").getValue().toString().equals(cod)){
                                    res = true;
                                    ocultarTeclado();
                                    Toast.makeText(medicamentos.this, "El medicamento que intentas ingresar ya existe", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            if (res == false)
                            {
                                Medicamento med = new Medicamento(cod,nombre,dosis,mg,mes,cantidad);
                                dbref.push().setValue(med);
                                ocultarTeclado();
                                Toast.makeText(medicamentos.this, "El medicamento se ingresó correctamente", Toast.LENGTH_SHORT).show();
                                limpiar();
                            }

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

    private void buscar() {
        btn_buscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cod = codMedicina.getText().toString();

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference dbref = db.getReference("Medicamento");

                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean encontrado = false;
                        for (DataSnapshot x : snapshot.getChildren()) {
                            if (x.child("cod").getValue().toString().equals(cod)) {
                                encontrado = true;
                                nombreMedicina.setText(x.child("nombre").getValue().toString());
                                dosisMedicina.setText(x.child("dosis").getValue().toString());
                                mgMedicina.setText(x.child("mg").getValue().toString());
                                mesMedicina.setText(x.child("mes").getValue().toString());
                                cantidadMedicina.setText(x.child("cantidad").getValue().toString());
                                break;
                            }
                        }
                        if (!encontrado) {
                            ocultarTeclado();
                            Toast.makeText(medicamentos.this, "Medicamento no encontrado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    }
//fin buscar

    private void listarMedicamento()
    {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbref = db.getReference(Medicamento.class.getSimpleName());
        ArrayList <Medicamento> listmed = new ArrayList<Medicamento>();
        ArrayAdapter <Medicamento> ada = new ArrayAdapter<Medicamento>(medicamentos.this, android.R.layout.simple_list_item_1,listmed);
        lvDatos.setAdapter(ada);

        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Medicamento med = snapshot.getValue(Medicamento.class);
                listmed.add(med);
                ada.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                ada.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        lvDatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Medicamento med = listmed.get(i);
                AlertDialog.Builder a = new AlertDialog.Builder(medicamentos.this);
                a.setCancelable(true);
                a.setTitle("Medicamento seleccionado");
                String msg = "Codigo: " + med.getCod() + "\n\n";
                msg += "Nombre: " + med.getNombre() + "\n\n";
                msg += "Dosis: " + med.getDosis() + "\n\n";
                msg += "Mg: " + med.getMg() + "\n\n";
                msg += "Mes: " + med.getMes() + "\n\n";
                msg += "Cantidad: " + med.getCantidad();

                a.setMessage(msg);
                a.show();
            }
        });
    }

    //cierre listar medicamento

    private void eliminar()
    {
        btn_delete.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (codMedicina.getText().toString().trim().isEmpty()){
                    ocultarTeclado();
                    Toast.makeText(medicamentos.this, "Digite el codigo del medicamento a eliminar", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    codMedicina.getText().toString();
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference dbref = db.getReference(Medicamento.class.getSimpleName());

                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final boolean[] res = {false};
                            for(DataSnapshot x : snapshot.getChildren()){
                                if (codMedicina.getText().toString().equals(x.child("cod").getValue().toString())) {

                                    AlertDialog.Builder a = new AlertDialog.Builder(medicamentos.this);
                                    a.setCancelable(false);
                                    a.setTitle("Confirma");
                                    a.setMessage("¿Está seguro de eliminar el medicamento del registro?");
                                    a.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {

                                        }
                                    });
                                    a.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            res[0] = true;
                                            ocultarTeclado();
                                            x.getRef().removeValue();
                                            listarMedicamento();
                                        }
                                    });
                                    a.show();
                                    break;
                                }
                            }
                            if (res[0] == false){
                                ocultarTeclado();
                                Toast.makeText(medicamentos.this, "Medicamento no encontrado", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    }
    //cierre eliminar

    private void modificar() {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                        boolean res = false;
                        for (DataSnapshot x : snapshot.getChildren()) {
                            if (x.child("cod").getValue().toString().equals(codMedicina.getText().toString())) {
                                res = true;
                                x.child("nombre").getRef().setValue(nombre);
                                x.child("dosis").getRef().setValue(dosis);
                                x.child("mg").getRef().setValue(mg);
                                x.child("mes").getRef().setValue(mes);
                                x.child("cantidad").getRef().setValue(cantidad);

                                ocultarTeclado();
                                Toast.makeText(medicamentos.this, "El medicamento se modificó correctamente", Toast.LENGTH_SHORT).show();
                                limpiar();
                                listarMedicamento();
                                break;
                            }
                        }
                        if (!res) {
                            ocultarTeclado();
                            Toast.makeText(medicamentos.this, "Medicamento no encontrado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
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

    private void generarFormula() {
        btn_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codMedicina.getText().toString().trim().isEmpty()) {
                    ocultarTeclado();
                    Toast.makeText(medicamentos.this, "Ingrese el código del medicamento para generar la fórmula", Toast.LENGTH_SHORT).show();
                } else {
                    String cod = codMedicina.getText().toString();

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference dbref = db.getReference("Medicamento");

                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean res = false;
                            for (DataSnapshot x : snapshot.getChildren()) {
                                if (x.child("cod").getValue().toString().equals(cod)) {
                                    res = true;
                                    String nombre = x.child("nombre").getValue().toString();
                                    String dosis = x.child("dosis").getValue().toString();
                                    String mg = x.child("mg").getValue().toString();
                                    String mes = x.child("mes").getValue().toString();

                                    String formula = "Receta Médica" + "\n\n" +
                                            "Medicamento: " + nombre + "\n" +
                                            "Dosis: " + dosis + "\n" +
                                            "Mg: " + mg + "\n" +
                                            "Mes: " + mes + "\n";

                                    ocultarTeclado();
                                    Toast.makeText(medicamentos.this, "Fórmula generada", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("text/plain");
                                    intent.putExtra(Intent.EXTRA_TEXT, formula);
                                    startActivity(Intent.createChooser(intent, "Compartir fórmula"));
                                    break;
                                }
                            }
                            if (!res) {
                                ocultarTeclado();
                                Toast.makeText(medicamentos.this, "El medicamento que intentas generar la fórmula no existe", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(medicamentos.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    } //cierre generar formula

    private void solFormula() {
        btn_form.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (codMedicina.getText().toString().trim().isEmpty()) {
                    ocultarTeclado();
                    Toast.makeText(medicamentos.this, "Ingrese el código del medicamento para generar la fórmula", Toast.LENGTH_SHORT).show();
                } else {
                    String cod = codMedicina.getText().toString();

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference dbref = db.getReference("Medicamento");

                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            boolean res = false;
                            for (DataSnapshot x : snapshot.getChildren()) {
                                if (x.child("cod").getValue().toString().equals(cod)) {
                                    res = true;
                                    String nombre = x.child("nombre").getValue().toString();
                                    String dosis = x.child("dosis").getValue().toString();
                                    String mg = x.child("mg").getValue().toString();
                                    String mes = x.child("mes").getValue().toString();

                                    String formula = "Receta Médica" + "\n\n" +
                                            "Medicamento: " + nombre + "\n" +
                                            "Dosis: " + dosis + "\n" +
                                            "Mg: " + mg + "\n" +
                                            "Mes: " + mes + "\n";

                                    ocultarTeclado();
                                    Toast.makeText(medicamentos.this, "Fórmula generada", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Intent.ACTION_SEND);
                                    intent.setType("text/plain");
                                    intent.putExtra(Intent.EXTRA_TEXT, formula);
                                    startActivity(Intent.createChooser(intent, "Compartir fórmula"));
                                    break;
                                }
                            }
                            if (!res) {
                                ocultarTeclado();
                                Toast.makeText(medicamentos.this, "El medicamento que intentas generar la fórmula no existe", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(medicamentos.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    } //cierre solicitar


}//Cierra la clase

