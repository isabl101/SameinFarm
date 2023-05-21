package com.example.sameinfarm;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class registroCognitivo extends AppCompatActivity {
    EditText inpFecha, inpSituacion, inpPensamiento, inpEmocion, inpAccion;
    Button btn_agregar, btn_edit;
    ImageButton buttonBuscar, btn_eliminar;
    ListView lvRegistros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cognitivo);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);


        inpFecha= findViewById(R.id.inpFecha);
        inpSituacion= findViewById(R.id.inpSituacion);
        inpPensamiento= findViewById(R.id.inpPensamiento);
        inpEmocion= findViewById(R.id.inpEmocion);
        inpAccion= findViewById(R.id.inpAccion);
        btn_agregar= findViewById(R.id.btn_agregar);
        btn_edit= findViewById(R.id.btn_edit);
        buttonBuscar= findViewById(R.id.buttonBuscar);
        btn_eliminar= findViewById(R.id.btn_eliminar);
        lvRegistros= findViewById(R.id.lvRegistros);

        modificarCog();
        registrarCog();
        eliminarCog();
        buscarCog();
        listarCog();

    }

    private void registrarCog()
    {
        btn_agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inpFecha.getText().toString().trim().isEmpty() ||
                        inpSituacion.getText().toString().trim().isEmpty() ||
                        inpPensamiento.getText().toString().trim().isEmpty() ||
                        inpEmocion.getText().toString().trim().isEmpty() ||
                        inpAccion.getText().toString().trim().isEmpty())
                {
                    ocultarTeclado();
                    Toast.makeText(registroCognitivo.this, "Completa los campos faltantes", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String fecha = inpFecha.getText().toString();
                    String situacion = inpSituacion.getText().toString();
                    String pensamiento = inpPensamiento.getText().toString();
                    String emocion = inpEmocion.getText().toString();
                    String accion = inpAccion.getText().toString();

                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference dbref = db.getReference("Cognitivo");

                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            boolean res = false;
                            for (DataSnapshot x : snapshot.getChildren()){
                                if (x.child("fecha").getValue().toString().equals(fecha)){
                                    res = true;
                                    ocultarTeclado();
                                    Toast.makeText(registroCognitivo.this, "Esta fecha ya esta ingresada", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }
                            if (res == false)
                            {
                                Cognitivo cog = new Cognitivo(fecha,situacion,pensamiento,emocion,accion);
                                dbref.push().setValue(cog);
                                ocultarTeclado();
                                Toast.makeText(registroCognitivo.this, "El registro se ingresó correctamente", Toast.LENGTH_SHORT).show();
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
    }//cierre metodo registrar

    private void modificarCog()
    {
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha = inpFecha.getText().toString();
                String situacion = inpSituacion.getText().toString();
                String pensamiento = inpPensamiento.getText().toString();
                String emocion = inpEmocion.getText().toString();
                String accion = inpAccion.getText().toString();

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference dbref = db.getReference("Cognitivo");

                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean res = false;
                        for (DataSnapshot x : snapshot.getChildren()) {
                            if (x.child("fecha").getValue().toString().equals(inpFecha.getText().toString())) {
                                res = true;
                                x.child("situacion").getRef().setValue(situacion);
                                x.child("pensamiento").getRef().setValue(pensamiento);
                                x.child("emocion").getRef().setValue(emocion);
                                x.child("accion").getRef().setValue(accion);

                                ocultarTeclado();
                                Toast.makeText(registroCognitivo.this, "El registro se modificó correctamente", Toast.LENGTH_SHORT).show();
                                limpiar();
                                listarCog();
                                break;
                            }
                        }
                        if (!res) {
                            ocultarTeclado();
                            Toast.makeText(registroCognitivo.this, "Registro no encontrado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    } //cierre metodo modificar
    private void listarCog() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbref = db.getReference("Cognitivo");
        ArrayList<Cognitivo> listcog = new ArrayList<Cognitivo>();
        ArrayAdapter<Cognitivo> ada = new ArrayAdapter<Cognitivo>(registroCognitivo.this, android.R.layout.simple_list_item_1, listcog);
        lvRegistros.setAdapter(ada);

        dbref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Cognitivo cog = snapshot.getValue(Cognitivo.class);
                listcog.add(cog);
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

        lvRegistros.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cognitivo cog = listcog.get(i);
                AlertDialog.Builder a = new AlertDialog.Builder(registroCognitivo.this);
                a.setCancelable(true);
                a.setTitle("Registro seleccionado");
                String msg = "Fecha: " + cog.getFecha() + "\n\n";
                msg += "Situacion: " + cog.getSituacion() + "\n\n";
                msg += "Pensamiento: " + cog.getPensamiento() + "\n\n";
                msg += "Emocion: " + cog.getEmocion() + "\n\n";
                msg += "Accion: " + cog.getAccion() + "\n";

                a.setMessage(msg);
                a.show();
            }
        });
    }
    //cierre metodo listar

    private void buscarCog()
    {
        buttonBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fecha = inpFecha.getText().toString();

                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference dbref = db.getReference("Cognitivo");

                dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        boolean encontrado = false;
                        for (DataSnapshot x : snapshot.getChildren()) {
                            if (x.child("fecha").getValue().toString().equals(fecha)) {
                                encontrado = true;
                                inpSituacion.setText(x.child("situacion").getValue().toString());
                                inpPensamiento.setText(x.child("pensamiento").getValue().toString());
                                inpEmocion.setText(x.child("emocion").getValue().toString());
                                inpAccion.setText(x.child("accion").getValue().toString());
                                break;
                            }
                        }
                        if (!encontrado) {
                            ocultarTeclado();
                            Toast.makeText(registroCognitivo.this, "Registro no encontrado", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });
    } //cierre metodo buscar

    private void eliminarCog()
    {
        btn_eliminar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (inpFecha.getText().toString().trim().isEmpty()){
                    ocultarTeclado();
                    Toast.makeText(registroCognitivo.this, "Digite la fecha del registro a eliminar", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    inpFecha.getText().toString();
                    FirebaseDatabase db = FirebaseDatabase.getInstance();
                    DatabaseReference dbref = db.getReference(Cognitivo.class.getSimpleName());

                    dbref.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            final boolean[] res = {false};
                            for(DataSnapshot x : snapshot.getChildren()){
                                if (inpFecha.getText().toString().equals(x.child("fecha").getValue().toString())) {

                                    AlertDialog.Builder a = new AlertDialog.Builder(registroCognitivo.this);
                                    a.setCancelable(false);
                                    a.setTitle("Confirma");
                                    a.setMessage("¿Está seguro de eliminar este registro?");
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
                                            listarCog();
                                        }
                                    });
                                    a.show();
                                    break;
                                }
                            }
                            if (res[0] == false){
                                ocultarTeclado();
                                Toast.makeText(registroCognitivo.this, "Registro no encontrado", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
    } //cierre metodo eliminar

    private void ocultarTeclado(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    } // Cierra el método ocultarTeclado.

    private void limpiar()
    {
        inpFecha.setText("");
        inpSituacion.setText("");
        inpAccion.setText("");
    }

}