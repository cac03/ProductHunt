package com.caco3.producthunt;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {
  private final Context context;

  public ApplicationModule(Context context) {
    this.context = context;
  }

  @Provides
  @Singleton
  public Context provideContext() {
    return context;
  }
}
