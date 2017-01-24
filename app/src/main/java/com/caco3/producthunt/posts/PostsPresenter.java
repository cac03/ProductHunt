package com.caco3.producthunt.posts;

import com.caco3.producthunt.mvp.BasePresenter;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;


public interface PostsPresenter extends BasePresenter<PostsView> {
  void onRefreshRequest();
  void onPostSelected(ProductHuntPost post);
}
