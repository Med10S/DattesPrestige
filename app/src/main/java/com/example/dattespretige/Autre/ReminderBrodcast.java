package com.example.dattespretige.Autre;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dattespretige.R;

public class ReminderBrodcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationCompat.Builder builder =  new NotificationCompat.Builder(context,"notifyLemubit")
                .setSmallIcon(R.mipmap.ic_launcher2_foreground)
                .setContentTitle("Vous monquer de temps...")
                .setPriority(NotificationCompat.PRIORITY_HIGH);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200,builder.build());
    }
}
