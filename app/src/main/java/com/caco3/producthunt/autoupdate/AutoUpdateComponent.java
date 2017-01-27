package com.caco3.producthunt.autoupdate;

import dagger.Subcomponent;

@Subcomponent(
        modules = {
                AutoUpdateModule.class
        }
)
@AutoUpdateScope
public interface AutoUpdateComponent {
  void inject(CategoriesAndPostsUpdateService service);
}
