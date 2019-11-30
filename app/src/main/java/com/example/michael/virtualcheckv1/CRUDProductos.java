package com.example.michael.virtualcheckv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CRUDProductos extends AppCompatActivity {
    ImageButton imbCamara, imbCrear, imbLeer, imbModificar, imbEliminar, imbAtras, imbCancelar;
    EditText edtCodBarras, edtIdMarca, edtMarca, edtNomProd, edtCant, edtPrecio;
    RequestQueue requestQueue;
    String IP = "192.168.0.10";
    //String IP = " 192.168.43.127";
    //mipaginaprueba.vzpla.net
    String cargo = "";
    Spinner opciones;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudproductos);

        opciones = (Spinner) findViewById(R.id.spnTipoProducto);
        items = getResources().getStringArray(R.array.opciones);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.opciones, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        opciones.setAdapter(adapter);


        imbCamara = (ImageButton) findViewById(R.id.imbCamara);
        imbCrear = (ImageButton) findViewById(R.id.imbCrear);
        imbLeer = (ImageButton) findViewById(R.id.imbLeer);
        imbModificar = (ImageButton) findViewById(R.id.imbModificar);
        imbEliminar = (ImageButton) findViewById(R.id.imbEliminar);
        imbAtras = (ImageButton) findViewById(R.id.imbAtras);
        imbCancelar = (ImageButton) findViewById(R.id.imbCancelar);

        edtCodBarras = (EditText) findViewById(R.id.edtCodBar);
        edtMarca = (EditText) findViewById(R.id.edtMarca);
        edtIdMarca = (EditText) findViewById(R.id.edtIdMarca);
        edtNomProd = (EditText) findViewById(R.id.edtNomProd);
        edtCant = (EditText) findViewById(R.id.edtCant);
        edtPrecio = (EditText) findViewById(R.id.edtPrecio);
        try {
            Bundle bundle = getIntent().getExtras();
            cargo = bundle.getString("idCargo");
        } catch (Exception e) {
        }
        iniciarProductos();
    }


    public void iniciarProductos() {
        try {

            //mipaginaprueba.vzpla.net
            String URL = "http://" + IP + "/VirtualCheckV1BD/iniciar_Productos.php";
            //String URL ="http://mipaginaprueba.vzpla.net/VirtualCheckV1BD/iniciar_Productos.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                }
            }) {
            };
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        } catch (Exception e) {
            Toast.makeText(this, "La base de datos no responde!", Toast.LENGTH_SHORT).show();
        }
    }

    public void tipoProducto(View v) {

        String select = opciones.getSelectedItem().toString();
        if (select.equals("Electrodomesticos")) {
            edtMarca.setText("Electrodomesticos");
            edtIdMarca.setText("1");
        }
        if (select.equals("Muebles")) {
            edtMarca.setText("Muebles");
            edtIdMarca.setText("2");
        }
        if (select.equals("Frutas")) {
            edtMarca.setText("Frutas");
            edtIdMarca.setText("3");
        }
        if (select.equals("Verduras")) {
            edtMarca.setText("Verduras");
            edtIdMarca.setText("4");
        }
        if (select.equals("Mercado")) {
            edtMarca.setText("Mercado");
            edtIdMarca.setText("5");
        }
        if (select.equals("Aseo")) {
            edtMarca.setText("Aseo");
            edtIdMarca.setText("6");
        }
        if (select.equals("Moda")) {
            edtMarca.setText("Moda");
            edtIdMarca.setText("7");
        }
        if (select.equals("Deporte")) {
            edtMarca.setText("Deporte");
            edtIdMarca.setText("8");
        }
        if (select.equals("Licores")) {
            edtMarca.setText("Licores");
            edtIdMarca.setText("9");
        }
        if (select.equals("Confiteria")) {
            edtMarca.setText("Confiteria");
            edtIdMarca.setText("10");
        }
    }

    public void insertarProducto(View v) {
        try {
            if (edtCodBarras.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite todos los campos", Toast.LENGTH_SHORT).show();
            } else {


                String URL = "http://" + IP + "/VirtualCheckV1BD/insertar_producto.php";
                //String URL = "http://mipaginaprueba.vzpla.net/VirtualCheckV1BD/insertar_producto.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Producto registrado", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<String, String>();
                        parametros.put("codigoBarras", edtCodBarras.getText().toString());
                        parametros.put("nombreProd", edtNomProd.getText().toString());
                        parametros.put("cantidad", edtCant.getText().toString());
                        parametros.put("precio", edtPrecio.getText().toString());
                        parametros.put("Cargo_idCargo", cargo);
                        parametros.put("Marca_idMarca", edtIdMarca.getText().toString());
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

    public void eliminarProducto(View v) {
        try {
            if (edtCodBarras.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite el codigo de barras del producto!", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/eliminar_producto.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Producto eliminado!", Toast.LENGTH_SHORT).show();
                        limpiar();

                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<String, String>();
                        parametros.put("codigoBarras", edtCodBarras.getText().toString());
                        return parametros;
                    }
                };
                requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);

            }
        } catch (Exception e) {

        }
    }

    public void modificarProducto(View v) {
        try {
            if (edtCodBarras.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite el codigo de barras del producto!", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/modificar_producto.php?";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Producto modificado!", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<String, String>();
                        parametros.put("codigoBarras", edtCodBarras.getText().toString());
                        parametros.put("nombreProd", edtNomProd.getText().toString());
                        parametros.put("cantidad", edtCant.getText().toString());
                        parametros.put("precio", edtPrecio.getText().toString());
                        parametros.put("Cargo_idCargo", cargo);
                        parametros.put("Marca_idMarca", edtIdMarca.getText().toString());

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

    public void consultarProducto(View v) {
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
                                edtNomProd.setText(jsonObject.getString("nombreProd"));
                                edtCant.setText(jsonObject.getString("cantidad"));
                                edtPrecio.setText(jsonObject.getString("precio"));
                                edtIdMarca.setText(jsonObject.getString("Marca_idMarca"));
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

    public void limpiar() {
        edtCodBarras.setText("");
        edtMarca.setText("");
        edtIdMarca.setText("");
        edtNomProd.setText("");
        edtCant.setText("");
        edtPrecio.setText("");
    }

    public void cancelarProducto(View v) {
        edtCodBarras.setText("");
        edtMarca.setText("");
        edtIdMarca.setText("");
        ;
        edtNomProd.setText("");
        edtCant.setText("");
        edtPrecio.setText("");

    }

    public void atras(View v) {
        try {
            Intent intent = new Intent(v.getContext(), Login.class);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Algo ocurrio!", Toast.LENGTH_SHORT).show();
        }
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
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
