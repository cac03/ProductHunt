package com.caco3.producthunt.post;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                PostModule.class
        }
)
@PostScope
public interface PostComponent {
  void inject(PostFragment postFragment);
}
