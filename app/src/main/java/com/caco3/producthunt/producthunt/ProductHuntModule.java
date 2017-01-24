package com.caco3.producthunt.producthunt;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;

@Module
public class ProductHuntModule {
  @Provides
  @Singleton
  public ProductHunt provideProductHunt(OkHttpClient baseClient, Gson gson, AccessToken accessToken) {
    return new ProductHuntImpl(baseClient, gson, accessToken);
  }
}
