package com.example.michael.virtualcheckv1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Usuarios extends AppCompatActivity {
    TextView txtNombreUser, txtTipoUser, txtUsuario, txtClientes, txtVentas, txtReportes,txtProducto;
    ImageButton imbUserGestion, imbVentas, imbClientes, imbReportes, imbProducto;
    String nombreUser = "";
    String usuario = "";
    String idCargo = "";
    RequestQueue requestQueue;
    String IP = "192.168.0.10"; ///mi pc
    //String IP = "192.168.43.44";
    //String IP = "192.168.0.5";// pc m
    //String IP = "192.168.43.1";
    //String IP = "192.168.43.99";
    //String IP = "192.168.43.151";
    //String IP = "virtualcheck.000webhostapp.com";
    //String IP = " 192.168.43.127";
    //mipaginaprueba.vzpla.net

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            try {
                Intent intent = new Intent(this, Login.class);
                startActivityForResult(intent, 0);
            } catch (Exception e) {
                Toast.makeText(this, "Algo ocurrio mal, no se puede cerrar sesión!", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios);

        txtNombreUser = (TextView) findViewById(R.id.txtNombreUser);
        txtTipoUser = (TextView) findViewById(R.id.txtTipoUser);
        txtUsuario = (TextView) findViewById(R.id.txtUsuario);
        txtClientes = (TextView) findViewById(R.id.txtClientes);
        txtVentas = (TextView) findViewById(R.id.txtVentas);
        txtReportes = (TextView) findViewById(R.id.txtReportes);
        txtProducto = (TextView) findViewById(R.id.txtProducto);

        imbUserGestion = (ImageButton) findViewById(R.id.imbUsuario);
        imbVentas = (ImageButton) findViewById(R.id.imbVentas);
        imbClientes = (ImageButton) findViewById(R.id.imbClientes);
        imbReportes = (ImageButton) findViewById(R.id.imbReportes);
        imbProducto = (ImageButton) findViewById(R.id.imbProducto);

        try {
            Bundle bundle = getIntent().getExtras();
            nombreUser = bundle.getString("Nombre");
            usuario = bundle.getString("Usuario");
            txtNombreUser.setText(nombreUser);
        } catch (Exception e) {
        }
        permisosUsuario();
    }

    public void permisosUsuario() {
        try {
            String URL = "http://" + IP + "/VirtualCheckV1BD/permisos_usuario.php?idUsuario=" + usuario + "";
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    JSONObject jsonObject = null;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            jsonObject = response.getJSONObject(i);
                            if (jsonObject.getString("Cargo_idCargo").compareTo("0") == 0) {
                                txtTipoUser.setText("Empleado");
                                getImbProducto().setVisibility(View.VISIBLE);
                                getTxtProducto().setVisibility(View.VISIBLE);
                                getImbUserGestion().setVisibility(View.GONE);
                                getTxtUsuario().setVisibility(View.GONE);
                                getImbVentas().setVisibility(View.GONE);
                                getTxtVentas().setVisibility(View.GONE);
                                getImbClientes().setVisibility(View.GONE);
                                getTxtClientes().setVisibility(View.GONE);
                                getImbReportes().setVisibility(View.GONE);
                                getTxtReportes().setVisibility(View.GONE);
                                idCargo = jsonObject.getString("Cargo_idCargo");
                            } else if(jsonObject.getString("Cargo_idCargo").compareTo("1") == 0){
                                txtTipoUser.setText("Administrador");
                                getImbProducto().setVisibility(View.VISIBLE);
                                getTxtProducto().setVisibility(View.VISIBLE);
                                getImbUserGestion().setVisibility(View.VISIBLE);
                                getTxtUsuario().setVisibility(View.VISIBLE);
                                getImbVentas().setVisibility(View.VISIBLE);
                                getTxtVentas().setVisibility(View.VISIBLE);
                                getImbClientes().setVisibility(View.VISIBLE);
                                getTxtClientes().setVisibility(View.VISIBLE);
                                getImbReportes().setVisibility(View.VISIBLE);
                                getTxtReportes().setVisibility(View.VISIBLE);
                                idCargo = jsonObject.getString("Cargo_idCargo");
                            }else{
                                txtTipoUser.setText("Vendedor");
                                getImbProducto().setVisibility(View.GONE);
                                getTxtProducto().setVisibility(View.GONE);
                                getImbUserGestion().setVisibility(View.GONE);
                                getTxtUsuario().setVisibility(View.GONE);
                                getImbVentas().setVisibility(View.VISIBLE);
                                getTxtVentas().setVisibility(View.VISIBLE);
                                getImbClientes().setVisibility(View.VISIBLE);
                                getTxtClientes().setVisibility(View.VISIBLE);
                                getImbReportes().setVisibility(View.GONE);
                                getTxtReportes().setVisibility(View.GONE);
                                idCargo = jsonObject.getString("Cargo_idCargo");
                            }
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
            requestQueue.add(jsonArrayRequest);

        } catch (Exception e) {
            Toast.makeText(this, "La base de datos no responde!", Toast.LENGTH_SHORT).show();
        }
    }

    public TextView getTxtUsuario() {
        return txtUsuario;
    }

    public ImageButton getImbUserGestion() {
        return imbUserGestion;
    }

    public TextView getTxtClientes() {
        return txtClientes;
    }

    public TextView getTxtVentas() {
        return txtVentas;
    }

    public ImageButton getImbVentas() {
        return imbVentas;
    }

    public ImageButton getImbClientes() {
        return imbClientes;
    }

    public TextView getTxtReportes() {
        return txtReportes;
    }

    public ImageButton getImbReportes() {
        return imbReportes;
    }

    public TextView getTxtProducto() {
        return txtProducto;
    }

    public ImageButton getImbProducto() {
        return imbProducto;
    }

    public void gestionUsuarios(View v) {
        try {
            Intent intent = new Intent(this, CRUDUsuarios.class);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Algo ocurrio con la base de datos!", Toast.LENGTH_SHORT).show();
        }
    }

    public void gestionProdcutos(View v) {
        try {
            Intent intent = new Intent(this, CRUDProductos.class);
            intent.putExtra("idCargo", idCargo);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Algo ocurrio con la base de datos!", Toast.LENGTH_SHORT).show();
        }
    }

    public void gestionVenta(View v) {
        try {
            Intent intent = new Intent(this, CRUDVentas.class);
            intent.putExtra("idCargo", idCargo);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Algo ocurrio con la base de datos!", Toast.LENGTH_SHORT).show();
        }
    }

    public void gestionCliente(View v) {
        try {
            Intent intent = new Intent(this, CRUDClientes.class);
            intent.putExtra("idCargo", idCargo);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Algo ocurrio con la base de datos!", Toast.LENGTH_SHORT).show();
        }
    }

    public void gestionReportes(View v) {
        try {
            Intent intent = new Intent(this, Reportes.class);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Algo ocurrio con la base de datos!", Toast.LENGTH_SHORT).show();
        }
    }

    public void cerrarSesion(View v) {
        try {
            Intent intent = new Intent(v.getContext(), Login.class);
            startActivityForResult(intent, 0);
        } catch (Exception e) {
            Toast.makeText(this, "Algo ocurrio mal, no se puede cerrar sesión!", Toast.LENGTH_SHORT).show();
        }
    }
}
