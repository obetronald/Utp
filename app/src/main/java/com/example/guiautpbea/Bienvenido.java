
package com.example.guiautpbea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Bienvenido extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);
        final TextView sms = (TextView)findViewById(R.id.sms);
        Intent i = this.getIntent();
        String nombre = i.getStringExtra("nombre");
        String apellido = i.getStringExtra("apellido");
        int telefono = i.getIntExtra("telefono",-1);
        sms.setText(sms.getText()+" "+nombre+" Su apellido es: "+apellido+" "+"  Su telefono es: "+telefono+"");

    }
}
