package com.caco3.producthunt.data.posts;

import com.caco3.producthunt.data.BaseRepository;
import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;

import java.util.List;


public interface PostsRepository extends BaseRepository<ProductHuntPost> {
  List<ProductHuntPost> listAllByCategory(ProductHuntCategory category);
  void removeAllByCategory(ProductHuntCategory category);
}
