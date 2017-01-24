package com.caco3.producthunt.producthunt.interceptors;

import com.caco3.producthunt.producthunt.TooManyRequestsException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;


public class TooManyRequestsInterceptor implements Interceptor {
  /**@see <a href="https://api.producthunt.com/v1/docs/rate_limits/headers">rate limits doc</a>*/
  static final int TOO_MANY_REQUESTS_CODE = 429;

  @Override
  public Response intercept(Chain chain) throws IOException {
    Response response = chain.proceed(chain.request());
    if (response.code() == TOO_MANY_REQUESTS_CODE) {
      throw new TooManyRequestsException(String.format("Rate limit exceed. Response: %s", response));
    } else {
      return response;
    }
  }
}
