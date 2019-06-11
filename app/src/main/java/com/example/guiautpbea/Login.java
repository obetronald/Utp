package com.example.guiautpbea;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView registro = (TextView)findViewById(R.id.registroLogin);
        Button btnLogin = (Button)findViewById(R.id.btnSesion);

        final EditText usuarioT = (EditText)findViewById(R.id.txtUser);
        final EditText claveT = (EditText)findViewById(R.id.txtPwd);

        registro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent registro = new Intent(Login.this, Registro.class);
                Login.this.startActivity(registro);
                //finish();
            }

        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String correo = usuarioT.getText().toString();
                final String password = claveT.getText().toString();
                Response.Listener<String> respuesta = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonRespuesta = new JSONObject(response);
                            boolean ok = jsonRespuesta.getBoolean("success");
                            if(ok==true){
                                String nombre = jsonRespuesta.getString("nombre");
                                String apellido = jsonRespuesta.getString("apellido");
                                String telefono = jsonRespuesta.getString("telefono");
                                Intent bienvenido = new Intent(Login.this, Modulo3.class);
                                bienvenido.putExtra("nombre",nombre);
                                bienvenido.putExtra("apellido",apellido);
                                bienvenido.putExtra("telefono",telefono);

                                Login.this.startActivity(bienvenido);
                                //Login.this.finish();

                            }else{
                                AlertDialog.Builder alerta = new AlertDialog.Builder(Login.this);
                                alerta.setMessage("Login fallido")
                                        .setNegativeButton("intenta nuevamente", null)
                                        .create()
                                        .show();
                            }
                        }catch (JSONException e){
                            e.getMessage();
                        }

                    }
                };
                LoginRequest r = new LoginRequest(correo,password,respuesta);
                RequestQueue cola = Volley.newRequestQueue(Login.this);
                cola.add(r);

            }
        });

    }
}
