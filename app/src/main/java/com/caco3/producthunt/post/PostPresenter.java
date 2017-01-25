package com.caco3.producthunt.post;

import com.caco3.producthunt.mvp.BasePresenter;
import com.caco3.producthunt.posts.PostsView;


public interface PostPresenter extends BasePresenter<PostsView> {
  void onGetItClicked();
}
