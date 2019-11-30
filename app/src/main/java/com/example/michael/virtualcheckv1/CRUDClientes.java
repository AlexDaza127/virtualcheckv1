package com.example.michael.virtualcheckv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CRUDClientes extends AppCompatActivity {
    ImageButton imbInsertar, imbConsultar, imbModificar, imbEliminar, imbAtras, imbCancelar;
    EditText edtIdCliente, edtNomCliente, edtApeCliente, edtDirCliente, edtTelCliente, edtCodVendedor;
    RequestQueue requestQueue;

    String IP = "192.168.0.10"; // mi pc

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudclientes);
        imbInsertar = (ImageButton) findViewById(R.id.imbInsertar);
        imbConsultar = (ImageButton) findViewById(R.id.imbConsultar);
        imbModificar = (ImageButton) findViewById(R.id.imbModificar);
        imbEliminar = (ImageButton) findViewById(R.id.imbEliminar);
        imbAtras = (ImageButton) findViewById(R.id.imbAtras);
        imbCancelar = (ImageButton) findViewById(R.id.imbCancelar);
        edtIdCliente = (EditText)findViewById(R.id.edtIdCliente);
        edtNomCliente = (EditText)findViewById(R.id.edtNomCliente);
        edtApeCliente = (EditText)findViewById(R.id.edtApeCliente);
        edtDirCliente = (EditText)findViewById(R.id.edtDirCliente);
        edtTelCliente = (EditText)findViewById(R.id.edtTelCliente);
        edtCodVendedor = (EditText)findViewById(R.id.edtCodVendedor);

    }

    void insertarCliente(View v){
        try {
            if (edtIdCliente.getText().toString().isEmpty() ||
                    edtNomCliente.getText().toString().isEmpty() ||
                    edtApeCliente.getText().toString().isEmpty() ||
                    edtDirCliente.getText().toString().isEmpty() ||
                    edtTelCliente.getText().toString().isEmpty()||
                    edtCodVendedor.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite todos los campos", Toast.LENGTH_SHORT).show();
            } else {


                String URL = "http://" + IP + "/VirtualCheckV1BD/insertar_cliente.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Cliente registrado", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<String, String>();
                        parametros.put("idCliente", edtIdCliente.getText().toString());
                        parametros.put("nombre", edtNomCliente.getText().toString());
                        parametros.put("apellido", edtApeCliente.getText().toString());
                        parametros.put("dirreccion", edtDirCliente.getText().toString());
                        parametros.put("telefono", edtTelCliente.getText().toString());
                        parametros.put("Vendedor_idVendedor", edtCodVendedor.getText().toString());
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

    void consultarCliente(View v){
        try {
            if (edtIdCliente.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite al identificacion del cliente!", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/consultar_cliente.php?idCliente=" + edtIdCliente.getText() + "";

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonObject = response.getJSONObject(i);
                                edtNomCliente.setText(jsonObject.getString("nombre"));
                                edtApeCliente.setText(jsonObject.getString("apellido"));
                                edtDirCliente.setText(jsonObject.getString("dirreccion"));
                                edtTelCliente.setText(jsonObject.getString("telefono"));
                                edtCodVendedor.setText(jsonObject.getString("Vendedor_idVendedor"));
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

    void modificarCliente(View v){
        try {
            if (edtIdCliente.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite la identificación del cliente!", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/modificar_cliente.php?";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Cliente modificado!", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<String, String>();
                        parametros.put("idCliente", edtIdCliente.getText().toString());
                        parametros.put("nombre", edtNomCliente.getText().toString());
                        parametros.put("apellido", edtApeCliente.getText().toString());
                        parametros.put("dirreccion", edtDirCliente.getText().toString());
                        parametros.put("telefono", edtTelCliente.getText().toString());
                        parametros.put("Vendedor_idVendedor", edtCodVendedor.getText().toString());

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

    void eliminarCliente(View v){
        try {
            if (edtIdCliente.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite la identificacion del cliente!", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/eliminar_cliente.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Cliente eliminado!", Toast.LENGTH_SHORT).show();
                        limpiar();

                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<String, String>();
                        parametros.put("idCliente", edtIdCliente.getText().toString());
                        return parametros;
                    }
                };
                requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);

            }
        } catch (Exception e) {

        }
    }

    void atras(View v){
        try {
            Intent intent = new Intent(v.getContext(), Usuarios.class);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Algo ocurrio mal, no se puede cerrar sesión!", Toast.LENGTH_SHORT).show();
        }
    }

    void borrarCampos(View v){
        edtIdCliente.setText("");
        edtNomCliente.setText("");
        edtApeCliente.setText("");
        edtDirCliente.setText("");
        edtTelCliente.setText("");
        edtCodVendedor.setText("");
    }

    void limpiar(){
        edtIdCliente.setText("");
        edtNomCliente.setText("");
        edtApeCliente.setText("");
        edtDirCliente.setText("");
        edtTelCliente.setText("");
        edtCodVendedor.setText("");
    }
}
