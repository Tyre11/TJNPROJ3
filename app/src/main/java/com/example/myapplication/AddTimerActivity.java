package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

public class AddTimerActivity extends AppCompatActivity implements LocationListener {
    private LocationManager locationManager;
    Location loc;
    Button btnSet;
    EditText timetxt;
    EditText daytxt;
    EditText msgtxt;
     String message;
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
                getLocation();
                message = msgtxt.getText().toString();
                message = message + " " +loc.getLatitude()+" "+loc.getLongitude();
                int time = Integer.parseInt(timetxt.getText().toString());
                int day = Integer.parseInt(daytxt.getText().toString());
                time = time *60000;
                day = day * 86400000;
                Intent i = new Intent(AddTimerActivity.this,TimerReceiver.class);
                i.putExtra("inMessage",message);
                PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(),0,i,0);
                AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
                am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis()+day+time,pi);


            }
        });

    }

    @Override
    public void onLocationChanged(Location location) {

        loc = location;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }



    private void getLocation() throws SecurityException {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        List<String> providers = locationManager.getProviders(true);
        for (String provider : providers) {
            locationManager.requestLocationUpdates(provider, 0, 0, (LocationListener) this);
            Location tempLocation = locationManager.getLastKnownLocation(provider);
            if (tempLocation == null) {
                continue;
            } else if (loc == null || tempLocation.getAccuracy() < loc.getAccuracy()) {
                loc = tempLocation;
            }
        }
    }






}
