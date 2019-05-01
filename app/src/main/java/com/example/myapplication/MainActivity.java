package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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
        createNotificationChannel();

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

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("CH002", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }


    }}


