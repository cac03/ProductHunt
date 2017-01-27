package com.caco3.producthunt.autoupdate.categoriesandposts;

import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;

import java.util.List;
import java.util.Map;

import rx.Observable;


public interface CategoriesAndPostsUpdater {
  Observable<Map<ProductHuntCategory, List<ProductHuntPost>>> startUpdate();
}
