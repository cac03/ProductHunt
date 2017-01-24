package com.caco3.producthunt.producthunt.interceptors;

import com.caco3.producthunt.producthunt.AccessToken;

import org.junit.Rule;
import org.junit.Test;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Connection;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.assertj.core.api.Java6Assertions.assertThat;

public class AccessTokenInterceptorTest {
  private static final AccessToken dummyAccessToken = new AccessToken("dummyAccessToken");
  private static final Interceptor accessTokenInterceptor
          = new AccessTokenInterceptor(dummyAccessToken);
  private final OkHttpClient okHttpClient
          = new OkHttpClient.Builder()
          .addInterceptor(accessTokenInterceptor)
          .build();
  @Rule
  public MockWebServer mockWebServer = new MockWebServer();

  @Test
  public void requestSent_accessTokenExistsInParameters() throws Exception{
    HttpUrl httpUrl = mockWebServer.url("dummy_url");
    mockWebServer.enqueue(new MockResponse());
    Call call = okHttpClient.newCall(new Request.Builder().url(httpUrl).build());
    Response response = call.execute();

    HttpUrl resultUrl = response.request().url();
    assertThat(resultUrl.queryParameterNames())
            .contains(AccessTokenInterceptor.ACCESS_TOKEN_PARAM_KEY);
    assertThat(resultUrl.queryParameter(AccessTokenInterceptor.ACCESS_TOKEN_PARAM_KEY))
            .isEqualTo(dummyAccessToken.getToken());
  }
}
