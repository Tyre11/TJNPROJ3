package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.Calendar;

public class AddAlarmActivity extends AppCompatActivity {
    TimePicker alarmTimePicker;

    AlarmManager alarmManager;
    DatePicker alarmDatePicker;
    Button scheduleb;
    Button homb;
    Boolean repeat = false;
    PendingIntent pendingIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alarm);
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker1);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmDatePicker = (DatePicker) findViewById(R.id.DatePicker1);
        homb = (Button) findViewById(R.id.addAlHombutton);

        scheduleb = (Button) findViewById(R.id.ScheduleAlarmbutton);


       // Intent intent = new Intent(this, AlarmReceiver.class);
       // pendingIntent=PendingIntent.getBroadcast(this, 0, intent, 0);
      /*  scheduleb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                long time;

                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getCurrentHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());
                calendar.set(Calendar.MONTH, alarmDatePicker.getMonth());
                calendar.set(Calendar.YEAR, alarmDatePicker.getYear());
                calendar.set(Calendar.DAY_OF_MONTH, alarmDatePicker.getDayOfMonth());


                time=(calendar.getTimeInMillis()-(calendar.getTimeInMillis()%60000));
                if(System.currentTimeMillis()>time)
                {
                    if (calendar.AM_PM == 0)
                        time = time + (1000*60*60*12);
                    else
                        time = time + (1000*60*60*24);
                }
                Toast.makeText(AddAlarmActivity.this, "ALARM ON"+ time +" "+ calendar.getTimeInMillis() , Toast.LENGTH_SHORT).show();
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }

        });*/
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

        Calendar calendar = Calendar.getInstance();
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
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

}

