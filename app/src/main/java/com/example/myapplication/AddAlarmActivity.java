package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class AddAlarmActivity extends AppCompatActivity {
    EditText messagetxt;
   // EditText timereptxt;
    TimePicker alarmTimePicker;
    static String message;
    AlarmManager alarmManager;
    DatePicker alarmDatePicker;
    Button scheduleb;
    Button homb;
    Boolean repeat = false;
    long reptime;
    PendingIntent pendingIntent;
    Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker1);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmDatePicker = (DatePicker) findViewById(R.id.DatePicker1);
        homb = (Button) findViewById(R.id.addAlHombutton);
        reptime = 60000;
        messagetxt=findViewById(R.id.editText);
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
                alarmManager.cancel(pendingIntent);
                Toast.makeText(AddAlarmActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
                Intent goHomeintent = new Intent(AddAlarmActivity.this, MainActivity.class);
                startActivity(goHomeintent);
            }
        });
        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayList<String> list = new ArrayList<String>();
        String options[] = TimeZone.getAvailableIDs();
        for(int i =0; i< options.length;i++){
            list.add(options[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);
        spinner.setSelection(93);
    }
    public void OnToggleClicked(View view)
    {
        long time;
        if (((ToggleButton) view).isChecked())
        {
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
        }
        else
        {
            repeat = false;
            /*alarmManager.cancel(pendingIntent);
            Toast.makeText(MainActivity.this, "ALARM OFF", Toast.LENGTH_SHORT).show();*/
        }
    }
    public void onSchedule(View view) {
        long time;
       TimeZone tz = (TimeZone) TimeZone.getTimeZone(spinner.getSelectedItem().toString());
        Calendar calendar = Calendar.getInstance(tz);

        calendar.set(Calendar.SECOND,0000);
        calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
        calendar.set(Calendar.MONTH, alarmDatePicker.getMonth());
        calendar.set(Calendar.YEAR, alarmDatePicker.getYear());
        calendar.set(Calendar.DAY_OF_MONTH, alarmDatePicker.getDayOfMonth());
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
        Toast.makeText(AddAlarmActivity.this, "ALARM ON ", Toast.LENGTH_SHORT).show();
       // if(!(timereptxt.getText().toString().equals("Repeat in MIN")))
       // {
          //  Integer toreptime = Integer.parseInt(timereptxt.getText().toString());
          //  reptime=toreptime.intValue()* 60000;
       // }
        if(!(messagetxt.getText().toString().equals("Message"))){
            message = messagetxt.getText().toString();}
        else message="Wake up";
        if(repeat) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, time,reptime, pendingIntent);
        }
        else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
        }
        }


}

