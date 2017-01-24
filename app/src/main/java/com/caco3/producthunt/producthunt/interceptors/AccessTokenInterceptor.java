package com.caco3.producthunt.producthunt.interceptors;

import com.caco3.producthunt.producthunt.AccessToken;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.caco3.producthunt.util.Preconditions.checkNotNull;

public class AccessTokenInterceptor implements Interceptor {
  static final String ACCESS_TOKEN_PARAM_KEY = "access_token";
  private final AccessToken accessToken;

  public AccessTokenInterceptor(AccessToken accessToken) {
    this.accessToken = checkNotNull(accessToken, "accessToken == null");
  }

  @Override
  public Response intercept(Chain chain) throws IOException {
    Request request = chain.request();
    HttpUrl url = request.url().newBuilder()
            .addQueryParameter(ACCESS_TOKEN_PARAM_KEY, accessToken.getToken())
            .build();
    request = request.newBuilder().url(url).build();

    return chain.proceed(request);
  }
}
