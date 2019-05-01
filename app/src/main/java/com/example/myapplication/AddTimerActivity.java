package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddTimerActivity extends AppCompatActivity {
    Button btnSet;
    EditText timetxt;
    EditText daytxt;
    EditText msgtxt;
    static String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_timer);

        btnSet =(Button) findViewById(R.id.buttonset);
        timetxt =(EditText) findViewById(R.id.mintxt);
        daytxt =(EditText) findViewById(R.id.daytxt);
        msgtxt =(EditText) findViewById(R.id.messg);

        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int time = Integer.parseInt(timetxt.getText().toString());
                int day = Integer.parseInt(daytxt.getText().toString());
                time = time *60000;
                day = day * 86400000;
                Intent i = new Intent(AddTimerActivity.this,AlarmReceiver.class);
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(),0,i,0);
                AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+day+time,pi);
                message = msgtxt.getText().toString();

            }
        });

    }
}
