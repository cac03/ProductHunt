package com.caco3.producthunt;

import android.app.Application;

import com.caco3.producthunt.dagger.DaggerComponentsHolder;

import timber.log.Timber;

public class ProductHuntApp extends Application {

  @Override
  public void onCreate() {
    super.onCreate();
    initTimber();
    initDaggerApplicationComponent();
  }

  private void initTimber() {
    Timber.plant(new Timber.DebugTree());
  }

  private void initDaggerApplicationComponent() {
    DaggerComponentsHolder
            .getInstance()
            .initApplicationComponent(this);
  }
}
