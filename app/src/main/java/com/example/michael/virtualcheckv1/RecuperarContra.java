package com.example.michael.virtualcheckv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RecuperarContra extends AppCompatActivity {

    private EditText edtUserRecup, edtClaveRecup, edtContrNuevaRecup;
    private Button btnEnviarPassRecuperacion;
    RequestQueue requestQueue;
    String IP = "192.168.0.10";// mi pc
    //String IP = "192.168.43.44";
    //String IP = "192.168.0.5";// pc m
    //String IP = "192.168.43.1";
    //String IP = "192.168.43.99";
    //String IP = "192.168.43.151";
    //String IP = "virtualcheck.000webhostapp.com";
    //String IP = " 192.168.43.127";
    // mipaginaprueba.vzpla.net
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar_contra);

        edtUserRecup = (EditText) findViewById(R.id.edtUserRecup);
        edtClaveRecup = (EditText) findViewById(R.id.edtClaveRecup);
        edtContrNuevaRecup = (EditText) findViewById(R.id.edtContrNuevaRecup);
        btnEnviarPassRecuperacion = (Button) findViewById(R.id.btnEnviarPassRecuperacion);

    }

    public void atras(View v) {
        try {
            Intent intent = new Intent(v.getContext(), Login.class);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Algo ocurrio!", Toast.LENGTH_SHORT).show();
        }
    }

    int validar = 0;

    public void enviarRecuperacion(View v) {

        try {
            if (edtUserRecup.getText().toString().isEmpty() || edtClaveRecup.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite el usuario y la clave!", Toast.LENGTH_SHORT).show();
            } else {

                String URL = "http://" + IP + "/VirtualCheckV1BD/consultar_usuario.php?idUsuario=" + edtUserRecup.getText() + "";
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonObject = response.getJSONObject(i);
                                if (edtUserRecup.getText().toString().compareTo(jsonObject.getString("idUsuario")) == 0 &&
                                        edtClaveRecup.getText().toString().compareTo(jsonObject.getString("palabraClave")) == 0) {
                                    Toast.makeText(getApplicationContext(), "Usuario y clave correctos!", Toast.LENGTH_SHORT).show();
                                    edtContrNuevaRecup.setEnabled(true);
                                    btnEnviarPassRecuperacion.setEnabled(true);
                                } else {
                                    validar++;
                                    if (validar > 3) {
                                        Toast.makeText(getApplicationContext(), "Comuniquese con administracion para recuperar su usuario y clave", Toast.LENGTH_SHORT).show();
                                    } else {
                                        edtContrNuevaRecup.setEnabled(false);
                                        btnEnviarPassRecuperacion.setEnabled(false);
                                        edtUserRecup.setText("");
                                        edtClaveRecup.setText("");
                                        Toast.makeText(getApplicationContext(), "Usuario y clave incorrectos!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        validar++;
                        if (validar > 3) {
                            Toast.makeText(getApplicationContext(), "Comuniquese con administracion para recuperar su usuario y contraseña", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplicationContext(), "Palabra clave no valida", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(jsonArrayRequest);
            }
        } catch (Exception e) {
            Toast.makeText(this, "La base de datos no responde!", Toast.LENGTH_SHORT).show();
        }
    }

    public void EnviarPassRecuperacion(View v) {
        try {
            if (edtContrNuevaRecup.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite la contraseña", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/recuperar_contrasenia.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> parametros = new HashMap<String, String>();

                        parametros.put("idUsuario", edtUserRecup.getText().toString());
                        parametros.put("contrasenia", edtContrNuevaRecup.getText().toString());

                        return parametros;
                    }
                };
                requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }
        } catch (Exception e) {
            Toast.makeText(this, "La base de datos no responde!", Toast.LENGTH_SHORT).show();
        }
    }
}
