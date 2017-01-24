package com.caco3.producthunt.producthunt.posts;

import com.caco3.producthunt.producthunt.category.ProductHuntCategory;

import java.io.IOException;
import java.util.List;


public interface PostsService {
  List<ProductHuntPost> getTodayPosts(ProductHuntCategory category) throws IOException;
}
