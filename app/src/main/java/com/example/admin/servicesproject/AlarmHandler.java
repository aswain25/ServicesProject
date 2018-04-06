package com.example.admin.servicesproject;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

public class AlarmHandler extends BroadcastReceiver {
    // see https://www.javatpoint.com/android-alarmmanager

    @Override
    public void onReceive(Context context, Intent intent) {
        Object o = intent.getSerializableExtra("data");
        Toast.makeText(context, ((RandomObject)MainActivity.current).toString(), Toast.LENGTH_LONG).show();


        Intent serviceIntent = new Intent(context, RandomObjectNotificationService.class);
        context.startService(serviceIntent);
    }
}
