package com.caco3.producthunt.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.caco3.producthunt.ProductHuntApp;
import com.caco3.producthunt.autoupdate.CategoriesAndPostsAutoUpdateAlarmReceiver;
import com.caco3.producthunt.util.Alarms;

import timber.log.Timber;


public class BootBroadcastReceiver extends BroadcastReceiver {
  @Override
  public void onReceive(Context context, Intent intent) {
    Timber.d("onReceive()");
    Alarms.scheduleAlarm(context, CategoriesAndPostsAutoUpdateAlarmReceiver.class,
            ProductHuntApp.CATEGORIES_AND_POSTS_AUTO_UPDATE_INTERVAL);
  }
}
