package com.caco3.producthunt.producthunt.interceptors;


import com.caco3.producthunt.producthunt.TooManyRequestsException;

import org.junit.Rule;
import org.junit.Test;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

public class TooManyRequestsInterceptorTest {
  @Rule
  public MockWebServer mockWebServer = new MockWebServer();
  private final Interceptor tooManyRequestsInterceptor
          = new TooManyRequestsInterceptor();
  private final OkHttpClient okHttpClient
          = new OkHttpClient.Builder()
          .addInterceptor(tooManyRequestsInterceptor).build();
  private HttpUrl dummyUrl = mockWebServer.url("dummyUrl");

  @Test(expected = TooManyRequestsException.class)
  public void serverReturnsRateLimitExceedError_tooManyRequestsExceptionThrown() throws Exception {
    mockWebServer.enqueue(new MockResponse()
            .setResponseCode(TooManyRequestsInterceptor.TOO_MANY_REQUESTS_CODE));
    okHttpClient.newCall(new Request.Builder().url(dummyUrl).build()).execute();
  }

  @Test
  public void everythingOk_nothingHappens() throws Exception {
    mockWebServer.enqueue(new MockResponse());
    okHttpClient.newCall(new Request.Builder().url(dummyUrl).build()).execute();
  }
}
