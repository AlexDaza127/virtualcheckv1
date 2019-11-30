package com.example.michael.virtualcheckv1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class CRUDVentas extends AppCompatActivity {

    EditText edtCodVendedor,edtSucursal,edtCodVenta,edtFecha,edtIdCliente ,
            edtNomCliente,edtApeCliente,edtCodBarras ,edtNomProd,edtPrecioProd,edtCantProd,edtTotal,edtCantDisp;
    Button btnBuscarCliente,btnBuscarProd;
    ImageButton imbCamBusProd,imbInsertar,imbConsultar,imbModificar,imbEliminar,imbAtras,imbCancelar;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    String formattedDate = df.format(c.getTime());
    RequestQueue requestQueue;
    double precioTotal;
    String IP = "192.168.0.10"; // mi pc
    double cantidadDispo,cantidadPedid, descuento;
    String cantidadDisponible = "";
    String descontarProducto="";

    String cantidadPedido = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudventas);

        imbInsertar = (ImageButton) findViewById(R.id.imbInsertar);
        imbConsultar = (ImageButton) findViewById(R.id.imbConsultar);
        imbModificar = (ImageButton) findViewById(R.id.imbModificar);
        imbEliminar = (ImageButton) findViewById(R.id.imbEliminar);
        imbAtras = (ImageButton) findViewById(R.id.imbAtras);
        imbCancelar = (ImageButton) findViewById(R.id.imbCancelar);

        edtCodVendedor = (EditText)findViewById(R.id.edtCodVendedor);
        edtSucursal = (EditText)findViewById(R.id.edtSucursal);
        edtCodVenta = (EditText)findViewById(R.id.edtCodVenta);
        edtFecha = (EditText)findViewById(R.id.edtFecha);
        edtIdCliente = (EditText)findViewById(R.id.edtIdCliente);
        edtCodVendedor = (EditText)findViewById(R.id.edtCodVendedor);
        edtIdCliente = (EditText)findViewById(R.id.edtIdCliente);
        edtNomCliente = (EditText)findViewById(R.id.edtNomCliente);
        edtApeCliente = (EditText)findViewById(R.id.edtApeCliente);
        edtCodBarras = (EditText)findViewById(R.id.edtCodBarras);
        edtNomProd = (EditText)findViewById(R.id.edtNomProd);
        edtPrecioProd = (EditText)findViewById(R.id.edtPrecioProd);
        edtCantProd = (EditText)findViewById(R.id.edtCantProd);
        edtTotal = (EditText)findViewById(R.id.edtTotal);
        edtCantDisp = (EditText)findViewById(R.id.edtCantDisp);

        btnBuscarCliente = (Button)findViewById(R.id.btnBuscarCliente);
        btnBuscarProd =(Button)findViewById(R.id.btnBuscarProd);
        edtFecha.setText(formattedDate);

    }


    void insertarVenta(View v) {
        cantidadDisponible = edtCantDisp.getText().toString();
        cantidadPedido = edtCantProd.getText().toString();
        cantidadDispo = Integer.parseInt(cantidadDisponible);
        cantidadPedid = Integer.parseInt(cantidadPedido);
        descuento = cantidadDispo -cantidadPedid;
        descontarProducto = String.valueOf(descuento);
        try {
            if (edtCodVenta.getText().toString().isEmpty() ||
                    edtFecha.getText().toString().isEmpty() ||
                    edtApeCliente.getText().toString().isEmpty() ||
                    edtIdCliente.getText().toString().isEmpty() ||
                    edtNomCliente.getText().toString().isEmpty() ||
                    edtApeCliente.getText().toString().isEmpty() ||
                    edtCodBarras.getText().toString().isEmpty() ||
                    edtNomProd.getText().toString().isEmpty() ||
                    edtPrecioProd.getText().toString().isEmpty() ||
                    edtCantProd.getText().toString().isEmpty() ||
                    edtTotal.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite todos los campos", Toast.LENGTH_SHORT).show();
            } else {



                if(cantidadDispo != 0 && cantidadDispo>=cantidadPedid) {

                    String URL = "http://" + IP + "/VirtualCheckV1BD/insertar_venta.php";
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(getApplicationContext(), "Venta registrada!", Toast.LENGTH_SHORT).show();

                        }
                    }, new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parametros = new HashMap<String, String>();
                            parametros.put("idVentas", edtCodVenta.getText().toString());
                            parametros.put("sucursal", edtSucursal.getText().toString());
                            parametros.put("fecha", edtFecha.getText().toString());
                            parametros.put("cantidad", edtCantProd.getText().toString());
                            parametros.put("total", edtTotal.getText().toString());
                            parametros.put("Producto_codigoBarras", edtCodBarras.getText().toString());
                            parametros.put("Vendedor_idVendedor", edtCodVendedor.getText().toString());
                            parametros.put("Cliente_idCliente", edtIdCliente.getText().toString());
                            parametros.put("cantidadPedido", descontarProducto);
                            return parametros;
                        }
                    };
                    requestQueue = Volley.newRequestQueue(this);
                    requestQueue.add(stringRequest);
                }else{
                    Toast.makeText(getApplicationContext(), "No hay la cantidad de producto disponible", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "La base de datos no responde!", Toast.LENGTH_SHORT).show();
        }
    }

    void consultarVenta(View v) {
        try {
            if (edtCodVenta.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite el codigo de la venta!", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/consultar_venta.php?idVentas=" + edtCodVenta.getText() + "";

                JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonObject = response.getJSONObject(i);
                                edtCodVenta.setText(jsonObject.getString("idVentas"));
                                edtSucursal.setText(jsonObject.getString("sucursal"));
                                edtFecha.setText(jsonObject.getString("fecha"));
                                edtTotal.setText(jsonObject.getString("total"));
                                edtCantProd.setText(jsonObject.getString("cantidad"));
                                edtCodBarras.setText(jsonObject.getString("Producto_codigoBarras"));
                                edtCodVendedor.setText(jsonObject.getString("Vendedor_idVendedor"));
                                edtIdCliente.setText(jsonObject.getString("Cliente_idCliente"));
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

    void modificarVenta(View v) {
        cantidadDisponible = edtCantDisp.getText().toString();
        cantidadPedido = edtCantProd.getText().toString();
        cantidadDispo = Integer.parseInt(cantidadDisponible);
        cantidadPedid = Integer.parseInt(cantidadPedido);
        descuento = cantidadDispo -cantidadPedid;
        descontarProducto = String.valueOf(descuento);
        try {
            if (edtCodVenta.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite el codigo de la venta!", Toast.LENGTH_SHORT).show();
            } else {
                if(cantidadDispo != 0 && cantidadDispo>=cantidadPedid) {
                String URL = "http://" + IP + "/VirtualCheckV1BD/modificar_venta.php?";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Venta modificada!", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<String, String>();
                        parametros.put("idVentas", edtCodVenta.getText().toString());
                        parametros.put("sucursal", edtSucursal.getText().toString());
                        parametros.put("fecha", edtFecha.getText().toString());
                        parametros.put("cantidad", edtCantProd.getText().toString());
                        parametros.put("total", edtTotal.getText().toString());
                        parametros.put("Producto_codigoBarras", edtCodBarras.getText().toString());
                        parametros.put("Vendedor_idVendedor", edtCodVendedor.getText().toString());
                        parametros.put("Cliente_idCliente", edtIdCliente.getText().toString());
                        parametros.put("cantidadPedido", descontarProducto);
                        return parametros;
                    }
                };
                requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
                }else{
                    Toast.makeText(getApplicationContext(), "No hay la cantidad de producto disponible", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "La base de datos no responde!", Toast.LENGTH_SHORT).show();
        }
    }

    void eliminarVenta(View v) {
        try {
            if (edtCodVenta.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite el codigo de la venta!", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/eliminar_venta.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "Venta eliminado!", Toast.LENGTH_SHORT).show();
                        limpiar();

                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error de conexion", Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<String, String>();
                        parametros.put("idVentas", edtCodVenta.getText().toString());
                        return parametros;
                    }
                };
                requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);

            }
        } catch (Exception e) {

        }
    }


    void buscarCliente(View v) {
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

    void buscarProducto(View v) {
        consultarProducto();
    }

    void calcularTotal(View v) {

        String precio = edtPrecioProd.getText().toString();
        String cantidad = edtCantProd.getText().toString();

        precioTotal = Double.parseDouble(precio) * Double.parseDouble(cantidad);
        String totalPagar = String.valueOf(precioTotal);
        edtTotal.setText(totalPagar);
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
                                edtNomProd.setText(jsonObject.getString("nombreProd"));
                                edtPrecioProd.setText(jsonObject.getString("precio"));
                                edtCantDisp.setText(jsonObject.getString("cantidad"));
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


    void atras(View v) {
        try {
            Intent intent = new Intent(v.getContext(), Usuarios.class);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Algo ocurrio mal, no se puede cerrar sesión!", Toast.LENGTH_SHORT).show();
        }
    }

    void borrarCampos(View v) {

        edtCodVenta.setText("");
        edtSucursal.setText("");
        edtCodVendedor.setText("");
        edtFecha.setText("");
        edtIdCliente.setText("");
        edtNomCliente.setText("");
        edtApeCliente.setText("");
        edtCodBarras.setText("");
        edtNomProd.setText("");
        edtPrecioProd.setText("");
        edtCantProd.setText("");
        edtTotal.setText("");
    }

    void limpiar() {
        edtCodVenta.setText("");
        edtSucursal.setText("");
        edtCodVendedor.setText("");
        edtFecha.setText("");
        edtIdCliente.setText("");
        edtNomCliente.setText("");
        edtApeCliente.setText("");
        edtCodBarras.setText("");
        edtNomProd.setText("");
        edtPrecioProd.setText("");
        edtCantProd.setText("");
        edtTotal.setText("");
    }

}
