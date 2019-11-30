package com.example.michael.virtualcheckv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InformeFecha extends AppCompatActivity {

    EditText edtFechaIni, edtFechaFinal;
    Button btnGenerar;
    BarChart graficaBarras;
    RequestQueue requestQueue;
    String IP = "192.168.0.10";
    String cantExist;
    int cantNumExist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informe_fecha);
        edtFechaIni = (EditText)findViewById(R.id.edtFechaIni);
        edtFechaFinal = (EditText)findViewById(R.id.edtFechaFinal);
        btnGenerar = (Button)findViewById(R.id.btnGenerar);
        graficaBarras = findViewById(R.id.graficaBarras);


    }

   public void mostrarFecha(View v){
        try {
            if (edtFechaIni.getText().toString().isEmpty() || edtFechaFinal.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite el codigo de la venta!", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/consultar_fecha.php?fechaInicio=" + edtFechaIni.getText() + "&fechaFinal=" + edtFechaFinal.getText();

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        List<BarEntry> entrada =new ArrayList<>();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonObject = response.getJSONObject(i);
                                cantExist = jsonObject.getString("cantidad");
                                cantNumExist = Integer.parseInt(cantExist);
                                entrada.add(new BarEntry(i, cantNumExist));
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        //mandamos los datos para crear la grafica
                        BarDataSet datos = new BarDataSet(entrada, "GRAFICAS DE BARRAS");
                        BarData data = new BarData(datos);


                        //ponemos color a cada barra
                        datos.setColors(ColorTemplate.COLORFUL_COLORS);
                        //separacion entre las barras
                        data.setBarWidth(0.2f);
                        graficaBarras.setData(data);
                        //pone las barras centradas
                        graficaBarras.setFitBars(true);
                        //hacer refresh
                        graficaBarras.invalidate();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();

                    }
                });

                requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(jsonArrayRequest);
            }
        } catch (Exception e) {
            Toast.makeText(this, "La base de datos no responde!", Toast.LENGTH_SHORT).show();
        }
    }

}
