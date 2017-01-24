package com.caco3.producthunt.appuser;


import com.caco3.producthunt.producthunt.AccessToken;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppUserModule {
  @Provides
  @Singleton
  public AccessToken provideAccessToken() {
    throw new RuntimeException("Create an instance of access token here");
    // return new AccessToken("your_token");
  }
}
