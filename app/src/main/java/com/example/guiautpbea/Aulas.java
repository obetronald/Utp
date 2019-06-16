package com.example.guiautpbea;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;



import android.graphics.Bitmap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Aulas extends AppCompatActivity {

    EditText edtHorario, edtCodbeacon, edtNombrebeacon, edtFechainicio,edtFechafin,edtNombarea,edtDescriparea,edtEstado,edtNombambiente,edtDescripamb;
    Button btnAgregar, btnCargar, btnBuscar;
    ImageView imagenes;
    RequestQueue request;
    RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aulas);

        edtNombarea = (EditText) findViewById(R.id.edtNombarea);
        edtDescriparea = (EditText) findViewById(R.id.edtDescriparea);
        edtEstado = (EditText) findViewById(R.id.edtEstado);
        edtHorario = (EditText) findViewById(R.id.edtHorario);
        edtCodbeacon = (EditText) findViewById(R.id.edtCodbeacon);
        edtNombrebeacon = (EditText) findViewById(R.id.edtNombreBeacon);
        edtFechainicio = (EditText) findViewById(R.id.edtFechainicio);
        edtFechafin = (EditText) findViewById(R.id.edtFechafin);
        edtNombambiente = (EditText) findViewById(R.id.edtNombambiente);
        edtDescripamb = (EditText) findViewById(R.id.edtDescripamb);
        imagenes=(ImageView)findViewById(R.id.imagenId);


        btnAgregar = (Button)findViewById(R.id.btnAgregar);
        btnCargar = (Button)findViewById(R.id.btnCargar);
        btnBuscar = (Button)findViewById(R.id.btnConsultar);

        request= Volley.newRequestQueue(getApplicationContext());

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ejecutarServicio("http://192.168.1.15/bdutp/insertar_beacon.php");

            }
        });

        btnCargar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();

            }
        });

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consultarhorario("http://192.168.1.15/bdutp/buscaID_horario.php?id_horario="+edtHorario.getText()+"");
            }
        });

    }

    private void cargarImagen(){

        String url = "http://192.168.1.15/bdutp/imagen/plano.jpg";
        url=url.replace(" ","%20");
        url=url.replace(" ","%20");

        ImageRequest imageRequest=new ImageRequest(url, new Response.Listener<Bitmap>() {
            @Override
            public void onResponse(Bitmap response) {

                imagenes.setImageBitmap(response);

            }
        }, 0, 0, ImageView.ScaleType.CENTER, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplication(),"error al cargar imagen",Toast.LENGTH_SHORT).show();

            }
        });

        request.add(imageRequest);

    }

    private void ejecutarServicio(String URL){

        StringRequest stringRequest=new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(), "OPERACION EXITOSA", Toast.LENGTH_SHORT).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parametros=new HashMap<String, String>();
                parametros.put("id_horario",edtHorario.getText().toString());
                parametros.put("cod_beacon",edtCodbeacon.getText().toString());
                parametros.put("nomb_beacon",edtNombrebeacon.getText().toString());
                parametros.put("fecha_inicio",edtFechainicio.getText().toString());
                parametros.put("fecha_fin",edtFechafin.getText().toString());
                parametros.put("area",edtNombarea.getText().toString());
                parametros.put("descrip_area",edtDescriparea.getText().toString());
                parametros.put("estado",edtEstado.getText().toString());
                parametros.put("nomb_amb",edtNombambiente.getText().toString());
                parametros.put("descrip_amb",edtDescripamb.getText().toString());

                return parametros;

            }
        };
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void consultarhorario(String URL){
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {
                    try {
                        jsonObject = response.getJSONObject(i);
                        edtCodbeacon.setText(jsonObject.getString("cod_beacon"));
                        edtNombrebeacon.setText(jsonObject.getString("nomb_beacon"));
                        edtFechainicio.setText(jsonObject.getString("fecha_inicio"));
                        edtFechafin.setText(jsonObject.getString("fecha_fin"));
                        edtNombarea.setText(jsonObject.getString("area"));
                        edtDescriparea.setText(jsonObject.getString("descrip_area"));
                        edtEstado.setText(jsonObject.getString("estado"));
                        edtNombambiente.setText(jsonObject.getString("nom_amb"));
                        edtDescripamb.setText(jsonObject.getString("descrip_amb"));

                    } catch (Exception e) {
                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(),"ERROR CONEXION",Toast.LENGTH_SHORT).show();

            }
        }

        );
        requestQueue=Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }
}

