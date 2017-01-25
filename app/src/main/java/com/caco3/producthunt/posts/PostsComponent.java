package com.caco3.producthunt.posts;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                PostsModule.class
        }
)
@PostsScope
public interface PostsComponent {
  void inject(PostsFragment fragment);
}
