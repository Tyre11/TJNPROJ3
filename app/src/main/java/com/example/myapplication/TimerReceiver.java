

package com.example.myapplication;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.Toast;

import com.example.myapplication.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Delayed;

import static android.content.Context.NOTIFICATION_SERVICE;

public class TimerReceiver extends BroadcastReceiver
{
    // Context contxt;
    static Ringtone ringtone;
    String msg;
    @Override
    public void onReceive(Context context, Intent intent)
    {
        msg = intent.getStringExtra("inMessage");
        // contxt = context;
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
        Uri alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        if (alarmUri == null)
        {
            alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

        Notification(context,msg);




        ringtone = RingtoneManager.getRingtone(context, alarmUri);
        ringtone.play();
        Timer timer = new Timer();
        TimerTask stopper = new TimerTask() {
            @Override
            public void run() {
                TimerReceiver.ringtone.stop();
            }
        };
        timer.schedule(stopper,5000);


    }

    public  void  Notification(Context context,String message){
        //   Intent intent = new Intent((context,Notification();)
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context,"CH002")
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setSmallIcon(R.drawable.ic_notifications_active_black_24dp)
                        .setContentTitle("ALARM")
                        .setContentText(message)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        // Gets an instance of the NotificationManager service//

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());
    }





}


