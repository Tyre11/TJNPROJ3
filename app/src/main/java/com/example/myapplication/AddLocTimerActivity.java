package com.example.myapplication;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class AddLocTimerActivity extends AppCompatActivity implements LocationListener {
    EditText timeset;
    int minutes;
    private LocationManager locationManager;
    Location loc;
    Location pastloc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loc_timer);
        timeset =  findViewById(R.id.editText2);
        minutes = Integer.parseInt(timeset.getText().toString());
        Timer timer = new Timer();
        TimerTask locationCheck = new TimerTask() {
            @Override
            public void run() {
            getLocation();
            if(loc.equals(pastloc)){

            }
            }
        };

    }

    @Override
    public void onLocationChanged(Location location) {
        pastloc = loc;
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
                pastloc= loc;
                loc = tempLocation;
            }
        }
    }





}
