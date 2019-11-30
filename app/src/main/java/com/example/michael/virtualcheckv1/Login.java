package com.example.michael.virtualcheckv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    ImageButton ver;
    EditText edtUser, edtPass;
    Button btnAcceso, btnRecuperar;
    RequestQueue requestQueue;
    String IP = "192.168.0.10";// mi pc
    //String IP = "192.168.43.44";
    //String IP = "192.168.0.5";// pc m
    //String IP = "192.168.43.1";
    //String IP = "192.168.43.99";
    //String IP = "192.168.43.151";
    //String IP = "virtualcheck.000webhostapp.com";
    //String IP = " 192.168.43.127";
    //String URL = "http://mipaginaprueba.vzpla.net

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUser = (EditText) findViewById(R.id.edtUser);
        edtPass = (EditText) findViewById(R.id.edtPass);
        btnAcceso = (Button) findViewById(R.id.btnAcceso);
        btnRecuperar = (Button) findViewById(R.id.btnRecuperar);
    }

    String nombre = "";
    int validar = 0;

    public void acceso(View v) {

        try {
            if (edtUser.getText().toString().isEmpty() || edtPass.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite el usuario y la contraseña!", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/consultar_usuario.php?idUsuario=" + edtUser.getText() + "";
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonObject = response.getJSONObject(i);
                                if (edtUser.getText().toString().compareTo(jsonObject.getString("idUsuario")) == 0 &&
                                        edtPass.getText().toString().compareTo(jsonObject.getString("contrasenia")) == 0) {
                                    nombre = jsonObject.getString("nombre");
                                    nombre = nombre + " " + jsonObject.getString("apellido");
                                    acceso_hecho();
                                } else {
                                    validar++;
                                    if (validar > 3) {
                                        Toast.makeText(getApplicationContext(), "Comuniquese con administracion para recuperar su usuario y contraseña", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(getApplicationContext(), "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
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

    public void acceso_hecho() {
        Toast.makeText(this, "Bienvenido " + nombre + "!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, Usuarios.class);
        intent.putExtra("Nombre", nombre);
        intent.putExtra("Usuario", edtUser.getText().toString());
        startActivityForResult(intent, 0);

    }

    public void recuperar(View v) {
        try {
            Intent intent = new Intent(v.getContext(), RecuperarContra.class);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Algo ocurrio mal!", Toast.LENGTH_SHORT).show();
        }
    }

    public void ver(View v) {
        try {
            Intent intent = new Intent(v.getContext(), VerProducto.class);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Algo ocurrio mal!", Toast.LENGTH_SHORT).show();
        }
    }

}
