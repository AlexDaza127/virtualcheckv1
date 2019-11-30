package com.example.michael.virtualcheckv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
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

public class CRUDVendedor extends AppCompatActivity {
    EditText edtId, edtNombre, edtCodVen,edtApellido;
    ImageButton imbCrear, imbEliminar, imbModificar, imbLeer, imbAtras, imbCancelar;
    Button btnVerificar;
    RequestQueue requestQueue;
    String IP = "192.168.0.10"; // mi pc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudvendedor);

        imbCrear = (ImageButton) findViewById(R.id.imbCrear);
        imbEliminar = (ImageButton) findViewById(R.id.imbEliminar);
        imbModificar = (ImageButton) findViewById(R.id.imbModificar);
        imbLeer = (ImageButton) findViewById(R.id.imbConsultar);
        imbAtras = (ImageButton) findViewById(R.id.imbAtras);
        imbCancelar = (ImageButton) findViewById(R.id.imbCancelar);

        edtCodVen = (EditText) findViewById(R.id.edtCodVen);
        edtId = (EditText) findViewById(R.id.edtId);
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtApellido = (EditText) findViewById(R.id.edtApellido);

        btnVerificar = (Button) findViewById(R.id.btnVerificar);
    }


    void insertarVendedor(View v){
        try {
            if (edtCodVen.getText().toString().isEmpty() ||
                    edtId.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite todos los campos", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/insertar_vendedor.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Vendedor registrado", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<String, String>();
                        parametros.put("idVendedor", edtCodVen.getText().toString());
                        parametros.put("Usuario_idUsuario", edtId.getText().toString());
                        parametros.put("idCargo", "3");
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
    void consultarVendedor(View v){
        try {
            if (edtCodVen.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite el codigo del vendedor!", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/consultar_vendedor.php?idVendedor=" + edtCodVen.getText() + "";

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonObject = response.getJSONObject(i);
                                edtCodVen.setText(jsonObject.getString("idVendedor"));
                                edtId.setText(jsonObject.getString("Usuario_idUsuario"));
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
    void modificarVendedor(View v){
        try {
            if (edtCodVen.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite el codigo de vendedor!", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/modificar_vendedor.php";
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
                        parametros.put("idVendedor", edtCodVen.getText().toString());
                        parametros.put("Usuario_idUsuario", edtId.getText().toString());

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
    void eliminarVendedor(View v){
        try {
            if (edtCodVen.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite el codigo del vendedor!", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/eliminar_vendedor.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Vendedor eliminado!", Toast.LENGTH_SHORT).show();
                        limpiar();

                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<String, String>();
                        parametros.put("idVendedor", edtCodVen.getText().toString());
                        return parametros;
                    }
                };
                requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);

            }
        } catch (Exception e) {

        }
    }
    void verficarUsuario(View v) {
        try {
            if (edtId.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite la identificación y su usuario!", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/consultar_usuario.php?idUsuario=" + edtId.getText() + "";
                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonObject = response.getJSONObject(i);
                                edtNombre.setText(jsonObject.getString("nombre"));
                                edtApellido.setText(jsonObject.getString("apellido"));
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

    void borrarCampos(View v){
        edtCodVen.setText("");
        edtId.setText("");
        edtNombre.setText("");
        edtApellido.setText("");
    }
    void limpiar(){
        edtCodVen.setText("");
        edtId.setText("");
        edtNombre.setText("");
        edtApellido.setText("");
    }
    void atras(View v){
        try {
            Intent intent = new Intent(v.getContext(), CRUDUsuarios.class);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Algo ocurrio mal, no se puede cerrar sesión!", Toast.LENGTH_SHORT).show();
        }
    }
}
