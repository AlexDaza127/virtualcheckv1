package com.example.michael.virtualcheckv1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GraficaProductosExistentes extends AppCompatActivity {
    EditText edtIdMarca, edtMarca;
    Button btnGenerarReporte;
    BarChart graficaBarras;
    RequestQueue requestQueue;
    String IP = "192.168.0.10";
    String cantExist;
    int cantNumExist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grafica_productos_existentes);

        graficaBarras = findViewById(R.id.graficaBarras);
        edtIdMarca = (EditText)findViewById(R.id.edtIdMarca);
        edtMarca = (EditText)findViewById(R.id.edtMarca);
        btnGenerarReporte = (Button)findViewById(R.id.btnGenerarReporte);


    }
    public void generarReporte(View v){

        try {
            if (edtIdMarca.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite el id del tipo de producto!", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/consultar_existencia.php?Marca_idMarca=" + edtIdMarca.getText() + "";

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<BarEntry> entrada =new ArrayList<>();
                        JSONObject jsonObject = null;
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

                URL = "http://" + IP + "/VirtualCheckV1BD/consultar_tipo.php?idMarca=" + edtIdMarca.getText() + "";

                JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonObject = response.getJSONObject(i);
                                edtIdMarca.setText(jsonObject.getString("idMarca"));
                                edtMarca.setText(jsonObject.getString("marca"));
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });
                requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(jsonArrayRequest1);


            }
        } catch (Exception e) {
            Toast.makeText(this, "La base de datos no responde!", Toast.LENGTH_SHORT).show();
        }
    }

}
