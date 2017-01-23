package com.caco3.producthunt;

import android.app.Application;

import timber.log.Timber;

public class ProductHuntApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    initTimber();
  }

  private void initTimber() {
    Timber.plant(new Timber.DebugTree());
  }
}
