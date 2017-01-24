package com.caco3.producthunt.producthunt;


import com.caco3.producthunt.producthunt.category.CategoriesService;
import com.caco3.producthunt.producthunt.posts.PostsService;

public interface ProductHunt {
  CategoriesService categories();
  PostsService posts();
}
