package com.caco3.producthunt.autoupdate;

import android.content.Context;

import com.caco3.producthunt.autoupdate.categoriesandposts.CategoriesAndPostsUpdater;
import com.caco3.producthunt.autoupdate.categoriesandposts.CategoriesAndPostsUpdaterImpl;
import com.caco3.producthunt.autoupdate.categoriesandposts.CategoryPostsUpdatedListener;
import com.caco3.producthunt.autoupdate.categoriesandposts.CategoryPostsUpdatedNotificationListener;
import com.caco3.producthunt.data.categories.CategoriesRepository;
import com.caco3.producthunt.data.posts.PostsRepository;
import com.caco3.producthunt.producthunt.ProductHunt;

import java.util.Arrays;
import java.util.List;

import dagger.Module;
import dagger.Provides;

@Module
public class AutoUpdateModule {
  @Provides
  @AutoUpdateScope
  CategoriesAndPostsUpdater provideCategoriesAndPostsUpdater(ProductHunt productHunt,
                                                             CategoriesRepository categoriesRepository,
                                                             PostsRepository postsRepository) {
    return new CategoriesAndPostsUpdaterImpl(productHunt, categoriesRepository, postsRepository);
  }

  @Provides
  @AutoUpdateScope
  List<CategoryPostsUpdatedListener> provideCategoryPostsUpdatedListeners(Context context) {
    return Arrays.<CategoryPostsUpdatedListener>asList(
            new CategoryPostsUpdatedNotificationListener(context));
  }
}
