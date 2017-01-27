package com.caco3.producthunt.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import java.util.Date;

import timber.log.Timber;

public class Alarms {
  private Alarms(){
  }

  public static void scheduleAlarm(Context context, Class<?> receiverClass, long intervalMillis) {
    Intent intent = new Intent(context, receiverClass);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
            SystemClock.elapsedRealtime() + intervalMillis, // trigger at
            intervalMillis,
            pendingIntent);
    Timber.d("Alarm scheduled next time it will trigger at: %s. Receiver: %s",
            new Date(System.currentTimeMillis() + intervalMillis), receiverClass.getSimpleName());
  }

  public static void cancelAlarm(Context context, Class<?> receiverClass) {
    Intent intent = new Intent(context, receiverClass);
    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

    AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
    alarmManager.cancel(pendingIntent);
    Timber.d("Alarm canceled. Receiver: %s", receiverClass.getSimpleName());
  }
}
