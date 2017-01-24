package com.caco3.producthunt.categories;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                CategoriesModule.class
        }
)
@CategoriesScope
public interface CategoriesComponent {
  void inject(CategoriesFragment fragment);
}
