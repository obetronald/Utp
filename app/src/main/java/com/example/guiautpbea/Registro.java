package com.example.guiautpbea;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        final EditText nombreT     = (EditText)findViewById(R.id.nombreRegistro);
        final EditText apellidoT   = (EditText)findViewById(R.id.apellidoRegistro);
        final EditText telefonoT     = (EditText)findViewById(R.id.telefonoRegistro);
        final EditText correoT    = (EditText)findViewById(R.id.correoRegistro);
        final EditText passwordT      = (EditText)findViewById(R.id.passwordRegistro);

        Button btnregistro = (Button)findViewById(R.id.btnRegistro);

        btnregistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nombre     = nombreT.getText().toString() ;
                String apellido   = apellidoT.getText().toString() ;
                String telefono   = telefonoT.getText().toString();
                String correo     = correoT.getText().toString() ;
                String password   = passwordT.getText().toString() ;
                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try{
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean ok = jsonRespuesta.getBoolean("success");
                            if (ok == true){
                                Intent i = new Intent(Registro.this, Login.class);
                                Registro.this.startActivity(i);
                                Registro.this.finish();
                            }else{
                                AlertDialog.Builder alerta = new AlertDialog.Builder(Registro.this);
                                alerta.setMessage("Registro fallido")
                                        .setNegativeButton("intenta nuevamente", null)
                                        .create()
                                        .show();
                            }

                        }catch (JSONException e){
                            e.getMessage();
                        }
                    }
                };

                RegistroRequest r = new RegistroRequest(nombre,apellido,telefono,correo,password,respuesta);
                RequestQueue cola = Volley.newRequestQueue(Registro.this);
                cola.add(r);
            }
        });
    }
}

