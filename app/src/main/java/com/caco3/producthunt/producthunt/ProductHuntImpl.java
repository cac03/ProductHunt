package com.caco3.producthunt.producthunt;


import com.caco3.producthunt.producthunt.category.CategoriesService;
import com.caco3.producthunt.producthunt.category.CategoriesServiceImpl;
import com.caco3.producthunt.producthunt.interceptors.AccessTokenInterceptor;
import com.caco3.producthunt.producthunt.interceptors.TooManyRequestsInterceptor;
import com.caco3.producthunt.producthunt.posts.PostsService;
import com.caco3.producthunt.producthunt.posts.PostsServiceImpl;
import com.google.gson.Gson;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.caco3.producthunt.util.Preconditions.checkNotNull;

class ProductHuntImpl implements ProductHunt {
  private static final String PRODUCT_HUNT_API_BASE_URL = "https://api.producthunt.com";

  private final CategoriesService categoriesService;
  private final PostsService postsService;

  ProductHuntImpl(OkHttpClient baseClient, Gson gson, AccessToken accessToken) {
    checkNotNull(baseClient, "baseClient == null");
    checkNotNull(accessToken, "accessToken == null");
    checkNotNull(gson, "gson == null");
    OkHttpClient okHttpClient = createHttpClient(baseClient, accessToken);
    Retrofit retrofit = createRetrofit(okHttpClient, gson);
    this.categoriesService = new CategoriesServiceImpl(retrofit);
    this.postsService = new PostsServiceImpl(retrofit);
  }

  private OkHttpClient createHttpClient(OkHttpClient baseClient, AccessToken accessToken) {
    return baseClient.newBuilder()
            .addInterceptor(new AccessTokenInterceptor(accessToken))
            .addInterceptor(new TooManyRequestsInterceptor())
            .build();
  }

  private Retrofit createRetrofit(OkHttpClient okHttpClient, Gson gson) {
    return new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(PRODUCT_HUNT_API_BASE_URL)
            .client(okHttpClient)
            .build();
  }

  @Override
  public PostsService posts() {
    return postsService;
  }

  @Override
  public CategoriesService categories() {
    return categoriesService;
  }
}
