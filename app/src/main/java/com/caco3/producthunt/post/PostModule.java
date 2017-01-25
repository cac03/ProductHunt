package com.caco3.producthunt.post;

import com.caco3.producthunt.producthunt.posts.ProductHuntPost;

import dagger.Module;
import dagger.Provides;

import static com.caco3.producthunt.util.Preconditions.checkNotNull;

@Module
public class PostModule {
  private final ProductHuntPost productHuntPost;

  public PostModule(ProductHuntPost productHuntPost) {
    this.productHuntPost = checkNotNull(productHuntPost, "productHuntPost == null");
  }

  @Provides
  @PostScope
  ProductHuntPost provideProductHuntPost() {
    return productHuntPost;
  }

  @Provides
  @PostScope
  PostPresenter providePostPresenter(ProductHuntPost productHuntPost) {
    return new PostPresenterImpl(productHuntPost);
  }
}
