package com.caco3.producthunt.posts;

import com.caco3.producthunt.data.posts.PostsRepository;
import com.caco3.producthunt.producthunt.ProductHunt;
import com.caco3.producthunt.producthunt.category.ProductHuntCategory;

import dagger.Module;
import dagger.Provides;

import static com.caco3.producthunt.util.Preconditions.checkNotNull;

@Module
public class PostsModule {
  private final ProductHuntCategory category;

  public PostsModule(ProductHuntCategory category) {
    this.category = checkNotNull(category, "category == null");
  }

  @Provides
  @PostsScope
  ProductHuntCategory provideProductHuntCategory() {
    return category;
  }

  @Provides
  @PostsScope
  PostsPresenter providePostsPresenter(ProductHuntCategory category,
                                              PostsRepository repository, ProductHunt productHunt) {
    return new PostsPresenterImpl(category, repository, productHunt);
  }
}
