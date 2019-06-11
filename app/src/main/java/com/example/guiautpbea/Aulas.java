package com.example.guiautpbea;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Date;



public class Aulas extends AppCompatActivity {

    Button btn;
    TextView vt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aulas);

        btn = (Button)findViewById(R.id.btnHora);
        vt = (TextView) findViewById(R.id.fecha);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn.setText(new Date().toString());

            }
        });
        Time today=new Time(Time.getCurrentTimezone());
        today.setToNow();
        int dia=today.monthDay;
        int mes=today.month;
        int ano=today.year;
        mes=mes+1;
        vt.setText("mes :"+mes+""+"dia :"+dia+""+"año :"+ ano);


    }
}
