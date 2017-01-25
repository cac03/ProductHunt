package com.caco3.producthunt.producthunt.posts;

import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.util.List;


import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class PostsServiceImpl implements PostsService {
  private final PostsService postsService;

  public PostsServiceImpl(Retrofit retrofit) {
    this.postsService = retrofit.create(PostsService.class);
  }

  @Override
  public List<ProductHuntPost> getTodayPosts(ProductHuntCategory category) throws IOException {
    Call<PostsResponse> call = postsService.getTodayPosts(category.getSlug());
    Response<PostsResponse> response = call.execute();
    if (response.isSuccessful()) {
      return response.body().posts;
    } else {
      throw new IOException(String.format(
              "Unable to get today posts for category '%s'. Unexpected response code: %s",
              category, response.raw()));
    }
  }

  private interface PostsService {
    @GET("/v1/categories/{category}/posts")
    Call<PostsResponse> getTodayPosts(@Path("category") String categoryName);
  }

  private static class PostsResponse {
    @SerializedName("posts")
    @Expose
    List<ProductHuntPost> posts;
  }
}
