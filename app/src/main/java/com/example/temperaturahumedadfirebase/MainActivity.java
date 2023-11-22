package com.example.temperaturahumedadfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    DatabaseReference mydb; // Objeto DatabaseReference que representa la referencia de la ubicación de la base de datos Firebase
    TextView Temperatura, Humedad; // Objetos TextView que se utilizan para mostrar la temperatura y la humedad en la interfaz de usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("FirebaseData", "onCreate called"); // Mensaje de registro (log) que se imprime en la consola de Android
        Temperatura = findViewById(R.id.Temp);
        Humedad = findViewById(R.id.Hum);
        mydb = FirebaseDatabase.getInstance().getReference().child("Sensor"); // Se inicia mydb con la referencia a la ubicación "Sensor" de la base de datos Firebase

        try {
            mydb.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("FirebaseData", "TextViews initialized"); // Mensaje de registro (log) indicando que los TextView se han inicializado correctamente

                    if (dataSnapshot.exists()) {
                        if (dataSnapshot.child("temp").exists() && dataSnapshot.child("hum").exists()) {
                            String tempdata = dataSnapshot.child("temp").getValue(String.class);
                            String humdata = dataSnapshot.child("hum").getValue(String.class);

                            if (tempdata != null && humdata != null) {
                                // Verificar si los valores no son nulos antes de asignarlos a los TextView
                                Temperatura.setText(tempdata);
                                Humedad.setText(humdata);
                            } else {
                                // Manejar el caso en que los valores sean nulos
                            }
                        } else {
                            // Manejar el caso en que los valores sean nulos
                        }
                    } else {
                        Log.d("FireBaseData", "DataSnapshot does not exist"); // Imprimir un mensaje de log o realizar alguna acción de manejo de errores
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Log.e("FireBaseData", "OnCancelled: " + error.getMessage()); // Mensaje de registro (log) para mensajes de error
                }
            });
        } catch (Exception e) {
            // Esta línea está diseñada para registrar cualquier excepción
            Log.e("FireBaseData", "Error: " + e.getMessage());
        }
    }
}