package com.caco3.producthunt.posts;


import com.caco3.producthunt.mvp.BaseView;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;

import java.util.List;

public interface PostsView extends BaseView {
  void showRefreshLayout();
  void hideRefreshLayout();
  void showUnableLoadCategoriesDueNetworkIssuesError();
  void showUnableLoadCategoriesDueRequestLimitError();
  void showPosts(List<ProductHuntPost> posts);
  void navigateToPostView(ProductHuntPost post);
}
