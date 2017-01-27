package com.caco3.producthunt.autoupdate;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;


public class CategoriesAndPostsAutoUpdateAlarmReceiver extends WakefulBroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    Intent serviceIntent = new Intent(context, CategoriesAndPostsUpdateService.class);
    startWakefulService(context, serviceIntent);
  }
}
