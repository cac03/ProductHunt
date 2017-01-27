package com.caco3.producthunt;

import android.app.Application;
import android.preference.PreferenceManager;

import com.caco3.producthunt.autoupdate.CategoriesAndPostsAutoUpdateAlarmReceiver;
import com.caco3.producthunt.dagger.DaggerComponentsHolder;
import com.caco3.producthunt.util.Alarms;

import java.util.concurrent.TimeUnit;

import timber.log.Timber;

public class ProductHuntApp extends Application {
  private static final String FIRST_RUN_KEY = "first_run";
  static final long CATEGORIES_AND_POSTS_AUTO_UPDATE_INTERVAL
          = TimeUnit.HOURS.toMillis(1);

  @Override
  public void onCreate() {
    super.onCreate();
    initTimber();
    initDaggerApplicationComponent();
    if (isFirstRun()) {
      scheduleCategoriesAndPostsUpdateService();
      setFirstRun(false);
    }
  }

  private void initTimber() {
    Timber.plant(new Timber.DebugTree());
  }

  private void initDaggerApplicationComponent() {
    DaggerComponentsHolder
            .getInstance()
            .initApplicationComponent(this);
  }

  private void scheduleCategoriesAndPostsUpdateService() {
    Alarms.scheduleAlarm(this,
            CategoriesAndPostsAutoUpdateAlarmReceiver.class,
            CATEGORIES_AND_POSTS_AUTO_UPDATE_INTERVAL);
  }

  private boolean isFirstRun() {
    return PreferenceManager.getDefaultSharedPreferences(this)
            .getBoolean(FIRST_RUN_KEY, true);
  }

  private void setFirstRun(boolean firstRun) {
    PreferenceManager.getDefaultSharedPreferences(this)
            .edit().putBoolean(FIRST_RUN_KEY, firstRun)
            .apply();
  }

}
