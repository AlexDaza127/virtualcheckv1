package com.example.michael.virtualcheckv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class Reportes extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);



    }

    public void gestionProductosExistentes(View v) {
        try {
            Intent intent = new Intent(this, GraficaProductosExistentes.class);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Algo ocurrio con la base de datos!", Toast.LENGTH_SHORT).show();
        }
    }

    public void gestionFechas(View v) {
        try {
            Intent intent = new Intent(this, InformeFecha.class);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Algo ocurrio con la base de datos!", Toast.LENGTH_SHORT).show();
        }
    }


}
