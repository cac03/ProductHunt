package com.caco3.producthunt.network;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class NetworkModule {
  @Provides
  @Singleton
  public OkHttpClient provideOkHttpClient() {
    return new OkHttpClient();
  }

  @Provides
  @Singleton
  public Gson provideGson() {
    return new Gson();
  }
}
