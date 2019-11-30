package com.example.michael.virtualcheckv1;

import android.content.Intent;
import android.net.Uri;
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

public class CRUDUsuarios extends AppCompatActivity {
    RadioButton rdbAdmin, rdbEmpl, rdbVend;
    EditText edtId, edtNombre, edtTel, edtEmail, edtSalario, edtApellido, edtPassReg, edtClave;
    ImageButton imbCrear, imbEliminar, imbModificar, imbLeer, imbAtras, imbCancelar, imbRecuperPass;
    Button btnGenVend;
    RequestQueue requestQueue;
    String IP = "192.168.0.10"; // mi pc
    //String IP = "192.168.43.44";
    //String IP = "192.168.0.5";// pc m
    //String IP = "192.168.43.1";
    //String IP = "192.168.43.99";
    //String IP = "192.168.43.151";
    //String IP = "virtualcheck.000webhostapp.com";
    //String IP = " 192.168.43.127";// mi pc con j6
    //String IP = "mipaginaprueba.vzpla.net"
    // String URL = "http://mipaginaprueba.vzpla.net/VirtualCheckV1BD/consultar_usuario.php?idUsuario=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crudusuarios);

        rdbAdmin = (RadioButton) findViewById(R.id.rdbAdmin);
        rdbEmpl = (RadioButton) findViewById(R.id.rdbEmpl);
        rdbVend = (RadioButton) findViewById(R.id.rdbVend);

        edtId = (EditText) findViewById(R.id.edtId);
        edtNombre = (EditText) findViewById(R.id.edtNombre);
        edtTel = (EditText) findViewById(R.id.edtTel);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSalario = (EditText) findViewById(R.id.edtSalario);
        edtApellido = (EditText) findViewById(R.id.edtApellido);
        edtPassReg = (EditText) findViewById(R.id.edtPassReg);
        edtClave = (EditText) findViewById(R.id.edtClave);

        imbCrear = (ImageButton) findViewById(R.id.imbCrear);
        imbEliminar = (ImageButton) findViewById(R.id.imbEliminar);
        imbModificar = (ImageButton) findViewById(R.id.imbModificar);
        imbLeer = (ImageButton) findViewById(R.id.imbConsultar);
        imbAtras = (ImageButton) findViewById(R.id.imbAtras);
        imbCancelar = (ImageButton) findViewById(R.id.imbCancelar);
        imbRecuperPass = (ImageButton) findViewById(R.id.imbRecuperPass);
        inicial();
    }

    String tipoUser = "";
    String idCargo = "";


    public void inicial() {
        try {
            String URL = "http://" + IP + "/VirtualCheckV1BD/insertar_inicio.php";
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                }
            }, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                }
            }) {
            };
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        } catch (Exception e) {
        }
    }

    public void insertarUsuario(View v) {
        try {
            if (edtId.getText().toString().isEmpty() ||
                    edtNombre.getText().toString().isEmpty() ||
                    edtTel.getText().toString().isEmpty() ||
                    edtEmail.getText().toString().isEmpty() ||
                    edtSalario.getText().toString().isEmpty() ||
                    edtApellido.getText().toString().isEmpty() ||
                    edtPassReg.getText().toString().isEmpty() ||
                    edtClave.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite todos los campos", Toast.LENGTH_SHORT).show();
            } else {

                if (rdbAdmin.isChecked()) {
                    tipoUser = "Administrador";
                    idCargo = "1";
                } else if (rdbEmpl.isChecked()) {
                    tipoUser = "Empleado";
                    idCargo = "0";
                }else if (rdbVend.isChecked()) {
                    tipoUser = "Vendedor";
                    idCargo = "3";
                }
                String URL = "http://" + IP + "/VirtualCheckV1BD/insertar_usuario.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "OPERACION EXITOSA 1", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<String, String>();
                        parametros.put("idCargo", idCargo);
                        parametros.put("idTelefono", edtTel.getText().toString());
                        parametros.put("idUsuario", edtId.getText().toString());
                        parametros.put("nombre", edtNombre.getText().toString());
                        parametros.put("apellido", edtApellido.getText().toString());
                        parametros.put("salario", edtSalario.getText().toString());
                        parametros.put("contrasenia", edtPassReg.getText().toString());
                        parametros.put("palabraClave", edtClave.getText().toString());
                        parametros.put("email", edtEmail.getText().toString());
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

    public void eliminarUsuario(View v) {
        try {
            if (edtId.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite la identificación y el usuario!", Toast.LENGTH_SHORT).show();
            } else {
                String URL = "http://" + IP + "/VirtualCheckV1BD/eliminar_usuario.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "El usuario fue eliminado", Toast.LENGTH_SHORT).show();
                        limpiar();

                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<String, String>();
                        parametros.put("idUsuario", edtId.getText().toString());
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

    public void modificarUsuario(View v) {
        try {
            if (edtId.getText().toString().isEmpty()) {
                Toast.makeText(this, "Digite la identificación y el usuario!", Toast.LENGTH_SHORT).show();
            } else {
                if (rdbAdmin.isChecked()) {
                    idCargo = "1";
                } else if (rdbEmpl.isChecked()) {
                    idCargo = "0";
                }
                String URL = "http://" + IP + "/VirtualCheckV1BD/modificar_usuario.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), "OPERACION EXITOSA 1", Toast.LENGTH_SHORT).show();

                    }
                }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parametros = new HashMap<String, String>();
                        parametros.put("idTelefono", edtTel.getText().toString());
                        parametros.put("idUsuario", edtId.getText().toString());
                        parametros.put("nombre", edtNombre.getText().toString());
                        parametros.put("apellido", edtApellido.getText().toString());
                        parametros.put("salario", edtSalario.getText().toString());
                        parametros.put("contrasenia", edtPassReg.getText().toString());
                        parametros.put("palabraClave", edtClave.getText().toString());
                        parametros.put("email", edtEmail.getText().toString());
                        parametros.put("Cargo_idCargo", idCargo);

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

    public void consultarUsuario(View v) {
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
                                edtSalario.setText(jsonObject.getString("salario"));
                                edtPassReg.setText(jsonObject.getString("contrasenia"));
                                edtClave.setText(jsonObject.getString("palabraClave"));
                                edtEmail.setText(jsonObject.getString("email"));
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

                URL = "http://" + IP + "/VirtualCheckV1BD/consultar_telefono.php?idUsuario=" + edtId.getText() + "";
                JsonArrayRequest jsonArrayRequest1 = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        JSONObject jsonObject = null;
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                jsonObject = response.getJSONObject(i);
                                edtTel.setText(jsonObject.getString("idTelefono"));
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
                requestQueue.add(jsonArrayRequest1);
            }
        } catch (Exception e) {
            Toast.makeText(this, "La base de datos no responde!", Toast.LENGTH_SHORT).show();
        }
    }

    public void enviarEmail(View v) {
        if (edtClave.getText().toString().isEmpty() || edtEmail.getText().toString().isEmpty()) {
            Toast.makeText(this, "Consulte primero el usuario ", Toast.LENGTH_SHORT).show();

        } else {
            String[] TO = {edtEmail.getText().toString()};
            String[] CC = {""};
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            emailIntent.setData(Uri.parse("mailto:"));
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
            emailIntent.putExtra(Intent.EXTRA_CC, CC);

            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Recuperación de clave VirtualCheck");
            emailIntent.putExtra(Intent.EXTRA_TEXT, "El siguiente es la palabra clave de su cuenta VirtualCheck: " + edtClave.getText().toString()
                    + "\n\n\n" + "Pasos para recuperar su cuenta: " + "\n\n" +
                    "1- Click en el boton -Recuper-" + "\n" +
                    "2- Digite su usuario y la palabra clave" + "\n" +
                    "3- Digite su nueva contraseña");

            try {
                startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
                finish();
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(this, "No responde la base de datos", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void limpiar() {
        edtId.setText("");
        edtNombre.setText("");
        edtTel.setText("");
        edtEmail.setText("");
        edtSalario.setText("");
        edtApellido.setText("");
        edtPassReg.setText("");
        edtClave.setText("");
    }

    public void cancelar(View v) {
        edtId.setText("");
        edtNombre.setText("");
        edtTel.setText("");
        edtEmail.setText("");
        edtSalario.setText("");
        edtApellido.setText("");
        edtPassReg.setText("");
        edtClave.setText("");
    }

    public void atras(View v) {
        try {
            Intent intent = new Intent(v.getContext(), Usuarios.class);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Algo ocurrio!", Toast.LENGTH_SHORT).show();
        }
    }

    public Button getBtnGenVend() {
        return btnGenVend;
    }

    public void gestionVendedor(View v){
        try {
            Intent intent = new Intent(v.getContext(), CRUDVendedor.class);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Algo ocurrio mal!", Toast.LENGTH_SHORT).show();
        }
    }
}
