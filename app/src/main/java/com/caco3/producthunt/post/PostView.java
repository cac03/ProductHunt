package com.caco3.producthunt.post;

import com.caco3.producthunt.mvp.BaseView;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;

public interface PostView extends BaseView {
  void showPost(ProductHuntPost post);
  void openPostProductSite(ProductHuntPost post);
}
