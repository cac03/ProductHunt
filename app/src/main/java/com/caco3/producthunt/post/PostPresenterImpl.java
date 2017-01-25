package com.caco3.producthunt.post;

import com.caco3.producthunt.producthunt.posts.ProductHuntPost;

import static com.caco3.producthunt.util.Preconditions.checkNotNull;


class PostPresenterImpl implements PostPresenter {
  private ProductHuntPost productHuntPost;
  private PostView view;

  PostPresenterImpl(ProductHuntPost productHuntPost) {
    this.productHuntPost = checkNotNull(productHuntPost, "productHuntPost == null");
  }

  @Override
  public void onViewAttached(PostView view) {
    this.view = checkNotNull(view, "view == null");
    initView();
  }

  private void initView() {
    if (isViewAttached()) {
      view.showPost(productHuntPost);
    }
  }

  private boolean isViewAttached() {
    return view != null;
  }

  @Override
  public void onViewDetached(PostView view) {
    this.view = null;
  }

  @Override
  public void onGetItClicked() {
    view.openPostProductSite(productHuntPost);
  }
}
