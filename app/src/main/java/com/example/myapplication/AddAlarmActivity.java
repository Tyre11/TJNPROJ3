package com.example.myapplication;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.Ringtone;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class AddAlarmActivity extends AppCompatActivity  implements  LocationListener{
    private FusedLocationProviderClient fusedLocationClient;
    private LocationManager locationManager;
    EditText messagetxt;
    // EditText timereptxt;
    TimePicker alarmTimePicker;
     String message;
    AlarmManager alarmManager;
    DatePicker alarmDatePicker;
    Button scheduleb;
    Button homb;
    Boolean repeat = false;
    long reptime;
    PendingIntent pendingIntent;
    Spinner spinner;
    Location loc;
    private LocationRequest mLocationRequest;

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker1);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmDatePicker = (DatePicker) findViewById(R.id.DatePicker1);
        homb = (Button) findViewById(R.id.addAlHombutton);
        reptime = 60000;
        messagetxt = findViewById(R.id.editText);
        //fusedLocationClient = getFusedLocationProviderClient(this);
        // timereptxt = findViewById(R.id.editText2);

        //timereptxt.setOnClickListener(new View.OnClickListener() {
        //   @Override
        //  public void onClick(View v) {
        // timereptxt.setText(" ");
        // }
        // });


        scheduleb = (Button) findViewById(R.id.ScheduleAlarmbutton);
        homb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   alarmManager.cancel(pendingIntent);
                Toast.makeText(AddAlarmActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
                Intent goHomeintent = new Intent(AddAlarmActivity.this, MainActivity.class);
                startActivity(goHomeintent);
            }
        });
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList<String> list = new ArrayList<String>();
        String options[] = TimeZone.getAvailableIDs();
        for (int i = 0; i < options.length; i++) {
            list.add(options[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, list);
        spinner.setAdapter(adapter);
        spinner.setSelection(93);
    }

    public void OnToggleClicked(View view) {
        long time;
        if (((ToggleButton) view).isChecked()) {
            repeat = true;
           /* Toast.makeText(MainActivity.this, "ALARM ON", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
            Intent intent = new Intent(this, AlarmReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

            time=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
            if(System.currentTimeMillis()>time)
            {
                if (calendar.AM_PM == 0)
                    time = time + (1000*60*60*12);
                else
                    time = time + (1000*60*60*24);
            }
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time, 10000, pendingIntent);*/
        } else {
            repeat = false;

            Toast.makeText(AddAlarmActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();

        }
    }

    public void onSchedule(View view) {
        long time;
        TimeZone tz = (TimeZone) TimeZone.getTimeZone(spinner.getSelectedItem().toString());
        Calendar calendar = Calendar.getInstance(tz);
        getLocation(this);
        if (!(messagetxt.getText().toString().equals("Message"))) {
            message = messagetxt.getText().toString();
        } else message = "Wake up";
        message = message + " " +loc.getLatitude()+" "+loc.getLongitude();

        calendar.set(Calendar.SECOND, 0000);
        calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
        calendar.set(Calendar.MONTH, alarmDatePicker.getMonth());
        calendar.set(Calendar.YEAR, alarmDatePicker.getYear());
        calendar.set(Calendar.DAY_OF_MONTH, alarmDatePicker.getDayOfMonth());
        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("inMessage",message);
        pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);





        time = (calendar.getTimeInMillis() - (calendar.getTimeInMillis() % 60000));
        if (System.currentTimeMillis() > time) {
            if (calendar.AM_PM == 0)
                time = time + (1000 * 60 * 60 * 12);
            else
                time = time + (1000 * 60 * 60 * 24);
        }
        Toast.makeText(AddAlarmActivity.this, "ALARM ON ", Toast.LENGTH_SHORT).show();
        // if(!(timereptxt.getText().toString().equals("Repeat in MIN")))
        // {
        //  Integer toreptime = Integer.parseInt(timereptxt.getText().toString());
        //  reptime=toreptime.intValue()* 60000;
        // }

        if (repeat) {

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), reptime, pendingIntent);
        } else {

            alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        }
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

    private void getLocation(Context context) throws SecurityException {
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
    /* protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

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
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }

    public void onLocationChanged(Location location) {
            loc = location;
        /*String msg = "Updated Location: " +
                Double.toString(location.getLatitude()) + "," +
                Double.toString(location.getLongitude());
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

  //  public void getLastLocation() {
        // Get last known recent location using new Google Play Services SDK (v11+)
       // FusedLocationProviderClient locationClient = getFusedLocationProviderClient(this);

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
        locationClient.getLastLocation()
                .addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // GPS location can be null if GPS is switched off
                        if (location != null) {
                            onLocationChanged(location);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d("MapDemoActivity", "Error trying to get last GPS location");
                        e.printStackTrace();
                    }
                });
        */




    }







