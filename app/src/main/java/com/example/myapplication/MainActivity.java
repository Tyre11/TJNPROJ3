package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button addAlarmb = (Button) findViewById(R.id.addalarmbutton);
        Button addTimerb = (Button) findViewById(R.id.addtimerbutton);
        Button addLocTimerb = (Button) findViewById(R.id.locationtimerb);

        addAlarmb.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                Intent addAlarmintent = new Intent(MainActivity.this, AddAlarmActivity.class);
                startActivity(addAlarmintent);
            }


        });

        addTimerb.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                Intent addTimerintent = new Intent(MainActivity.this, AddTimerActivity.class);
                startActivity(addTimerintent);
            }


        });

        addLocTimerb.setOnClickListener(new View.OnClickListener(){
            public void onClick (View view){
                Intent addLocTimerintent = new Intent(MainActivity.this, AddLocTimerActivity.class);
                startActivity(addLocTimerintent);
            }


        });



    }
}