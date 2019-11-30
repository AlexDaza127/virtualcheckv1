package com.example.michael.virtualcheckv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class VerProducto extends AppCompatActivity {
    EditText edtCodBarras, edtProd, edtPrecio;
    ImageButton imbCamara;
    RequestQueue requestQueue;
    String IP = "192.168.0.10";// mi pc
    //String IP = "192.168.43.44";
    //String IP = "192.168.43.99";
    //String IP = "192.168.43.99";
    //String IP = "192.168.43.151";
    //String IP = "192.168.43.1";
    //String IP = "192.168.0.5";// pc m
    //String IP = "virtualcheck.000webhostapp.com";

    //String IP = " 192.168.43.127";
    //String URL ="http://mipaginaprueba.vzpla.net
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_producto);
        edtCodBarras = (EditText) findViewById(R.id.edtApeCliente);
        edtProd = (EditText) findViewById(R.id.edtProd);
        edtPrecio = (EditText) findViewById(R.id.edtCodBarras);
        imbCamara = (ImageButton) findViewById(R.id.imbCamara);

    }

    public void escanear(View view) {
        IntentIntegrator intent = new IntentIntegrator(this);
        intent.setDesiredBarcodeFormats(IntentIntegrator.PRODUCT_CODE_TYPES);
        intent.setPrompt("Escaner Código");
        intent.setBeepEnabled(false);
        intent.setBarcodeImageEnabled(false);
        intent.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "cancelaste el escaneo", Toast.LENGTH_SHORT).show();
            } else {
                edtCodBarras.setText(result.getContents().toString());
                consultarProducto();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void consultarProducto() {
        try {
            if (edtCodBarras.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite el codigo de barras del producto!", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/consultar_producto.php?codigoBarras=" + edtCodBarras.getText() + "";

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonObject = response.getJSONObject(i);
                                edtProd.setText(jsonObject.getString("nombreProd"));
                                edtPrecio.setText(jsonObject.getString("precio"));
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
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

