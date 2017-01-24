package com.caco3.producthunt.producthunt.category;

import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;


public class CategoriesServiceImpl implements CategoriesService {
  private final CategoriesRetrofitService categoriesRetrofitService;

  public CategoriesServiceImpl(Retrofit retrofit) {
    this.categoriesRetrofitService = retrofit.create(CategoriesRetrofitService.class);
  }

  @Override
  public List<ProductHuntCategory> getAll() throws IOException {
    Call<CategoriesResponse> call = categoriesRetrofitService.getAll();
    Response<CategoriesResponse> response = call.execute();
    if (response.isSuccessful()) {
      return response.body().categories;
    } else {
      throw new IOException(String
              .format("Unable to get list of categories. Unexpected code: %s", response));
    }
  }

  private interface CategoriesRetrofitService {
    @GET("/v1/categories")
    Call<CategoriesResponse> getAll();
  }

  private static class CategoriesResponse {
    @SerializedName("categories")
    List<ProductHuntCategory> categories;
  }
}
