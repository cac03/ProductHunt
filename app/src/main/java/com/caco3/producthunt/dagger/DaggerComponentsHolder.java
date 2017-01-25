package com.caco3.producthunt.dagger;


import android.content.Context;

import com.caco3.producthunt.ApplicationComponent;
import com.caco3.producthunt.ApplicationModule;
import com.caco3.producthunt.DaggerApplicationComponent;
import com.caco3.producthunt.categories.CategoriesComponent;
import com.caco3.producthunt.categories.CategoriesModule;
import com.caco3.producthunt.data.DataModule;
import com.caco3.producthunt.network.NetworkModule;
import com.caco3.producthunt.post.PostComponent;
import com.caco3.producthunt.post.PostModule;
import com.caco3.producthunt.posts.PostsComponent;
import com.caco3.producthunt.posts.PostsModule;
import com.caco3.producthunt.producthunt.ProductHuntModule;
import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;

import java.util.HashMap;
import java.util.Map;

import static com.caco3.producthunt.util.Preconditions.checkState;

public class DaggerComponentsHolder {
  private static final DaggerComponentsHolder instance = new DaggerComponentsHolder();
  private ApplicationComponent applicationComponent;
  private CategoriesComponent categoriesComponent;
  private final Map<ProductHuntCategory, PostsComponent> postsComponents = new HashMap<>();
  private PostComponent postComponent;

  public static DaggerComponentsHolder getInstance() {
    return instance;
  }

  private DaggerComponentsHolder(){
  }

  public void initApplicationComponent(Context context) {
    this.applicationComponent = DaggerApplicationComponent
            .builder()
            .networkModule(new NetworkModule())
            .productHuntModule(new ProductHuntModule())
            .dataModule(new DataModule(context))
            .applicationModule(new ApplicationModule(context))
            .build();
  }

  public CategoriesComponent getCategoriesComponent() {
    if (categoriesComponent == null) {
      categoriesComponent = applicationComponent.plus(new CategoriesModule());
    }
    return categoriesComponent;
  }

  public void releaseCategoriesComponent() {
    categoriesComponent = null;
  }

  public PostsComponent getPostsComponent(ProductHuntCategory category) {
    PostsComponent component;
    if (!postsComponents.containsKey(category)) {
      component = applicationComponent.plus(new PostsModule(category));
      postsComponents.put(category, component);
    } else {
      component = postsComponents.get(category);
    }

    return component;
  }

  public void releasePostsComponent(ProductHuntCategory category) {
    checkState(postsComponents.containsKey(category),
            String.format("Attempt to release posts component that was not created (category: %s)", category));
    postsComponents.remove(category);
  }

  public void releasePostsComponents() {
    postsComponents.clear();
  }

  public PostComponent getPostComponent(ProductHuntPost productHuntPost) {
    if (postComponent == null) {
      postComponent = applicationComponent.plus(new PostModule(productHuntPost));
    }

    return postComponent;
  }

  public void releasePostComponent() {
    postComponent = null;
  }
}
