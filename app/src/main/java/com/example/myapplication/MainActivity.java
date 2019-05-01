package com.example.myapplication;

import android.Manifest;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Looper;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class MainActivity extends AppCompatActivity implements LocationListener {
    Location loc;
    Location pastloc;
    LocationManager locationManager;
    EditText timeset;
    PendingIntent loctimepi;
    AlarmManager alarmManager;
    long time;
    int minutes;
    LocationRequest mLocationRequest;
    LocationCallback mLocationCallback;
    GoogleApiClient mGoogleApiClient;
    Location mCurrentLocation;
    String mLastUpdateTime;
    FusedLocationProviderClient fusedLocationClient;


    /*protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(20000);
        mLocationRequest.setFastestInterval(15000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Button addAlarmb = (Button) findViewById(R.id.addalarmbutton);
        Button addTimerb = (Button) findViewById(R.id.addtimerbutton);
        Button addLocTimerb = (Button) findViewById(R.id.locationtimerb);
        timeset = findViewById(R.id.locts);
        createNotificationChannel();
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        pastloc=(locationManager.getLastKnownLocation("gps"));
        loc=(locationManager.getLastKnownLocation("gps"));
        startLocationUpdates();
        minutes = Integer.parseInt(timeset.getText().toString());
        time = minutes * 30000;
        Intent locTimeintent = new Intent(MainActivity.this, LocTimerReciever.class);
        locTimeintent.putExtra("inMessage", "MOVE IT");
        loctimepi = PendingIntent.getBroadcast(this, 0, locTimeintent, 0);
       alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, loctimepi);
        startLocationUpdates();
//timer.schedule(locationCheck,20000,20000);
        addAlarmb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent addAlarmintent = new Intent(MainActivity.this, AddAlarmActivity.class);
                startActivity(addAlarmintent);
            }


        });

        addTimerb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent addTimerintent = new Intent(MainActivity.this, AddTimerActivity.class);
                startActivity(addTimerintent);
            }


        });

        addLocTimerb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                minutes = Integer.parseInt(timeset.getText().toString());
                time = minutes * 30000;
                alarmManager.cancel(loctimepi);
                Intent newlocTimeintent = new Intent(MainActivity.this, LocTimerReciever.class);
                newlocTimeintent.putExtra("inMessage", "MOVE IT");
                loctimepi = PendingIntent.getBroadcast(MainActivity.this, 0, newlocTimeintent, 0);
                alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, loctimepi);

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
    }

    @Override
    public void onLocationChanged(Location location) {
        //AlarmReceiver.ringtone.stop();
        pastloc = loc;
        loc = location;
        alarmManager.cancel(loctimepi);
        Intent newlocTimeintent = new Intent(MainActivity.this, LocTimerReciever.class);
        newlocTimeintent.putExtra("inMessage", "MOVE IT");
       loctimepi = PendingIntent.getBroadcast(MainActivity.this, 0, newlocTimeintent, 0);
        alarmManager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time, loctimepi);
       // Toast.makeText(this, pastloc.getLatitude()+","+pastloc.getLongitude()+" "+loc.getLatitude()+","+loc.getLongitude(), Toast.LENGTH_LONG).show();
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

    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(2000);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(this);
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        getFusedLocationProviderClient(this).requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        loc =locationResult.getLastLocation();
                        if((pastloc.distanceTo(loc)>=23.0f)){
                             //loc.distanceTo(pastloc);
                            Toast.makeText(MainActivity.this, String.valueOf( loc.distanceTo(pastloc)), Toast.LENGTH_LONG).show();
                        onLocationChanged(locationResult.getLastLocation());}
                        else{
                            //Toast.makeText(MainActivity.this, locationResult.getLastLocation().getLatitude()+","+locationResult.getLastLocation().getLongitude()+" "+loc.getLatitude()+","+loc.getLongitude(), Toast.LENGTH_LONG).show();

                        }
                    }
                },
                Looper.myLooper());
    }



   /* private void getLocation() throws SecurityException {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        List<String> providers = locationManager.getProviders(true);
        for (String provider : providers) {
            locationManager.requestLocationUpdates(provider, 20000, 20, (LocationListener) this);
            Location tempLocation = locationManager.getLastKnownLocation(provider);
            if (tempLocation == null) {
                continue;
            } else if (loc == null || tempLocation.getAccuracy() < loc.getAccuracy()) {
              //  pastloc= loc;
                loc = tempLocation;
                pastloc= loc;
            }
        }*/
    }





