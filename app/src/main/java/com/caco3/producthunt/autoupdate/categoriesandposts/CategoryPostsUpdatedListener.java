package com.caco3.producthunt.autoupdate.categoriesandposts;

import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;

import java.util.List;

public interface CategoryPostsUpdatedListener {
  void onPostsUpdated(ProductHuntCategory category, List<ProductHuntPost> newPosts);
}
