package com.caco3.producthunt.post;

import com.caco3.producthunt.mvp.BasePresenter;


public interface PostPresenter extends BasePresenter<PostView> {
  void onGetItClicked();
}
