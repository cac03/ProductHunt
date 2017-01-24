package com.caco3.producthunt.categories;

import android.content.Context;

import com.caco3.producthunt.data.categories.CategoriesRepository;
import com.caco3.producthunt.producthunt.ProductHunt;

import dagger.Module;
import dagger.Provides;

@Module
public class CategoriesModule {
  @Provides
  @CategoriesScope
  CategoriesPreferences provideCategoriesPreferences(Context context) {
    return new CategoriesPreferencesImpl(context);
  }

  @Provides
  @CategoriesScope
  CategoriesPresenter provideCategoriesPresenter(ProductHunt productHunt,
                                                        CategoriesRepository repository,
                                                        CategoriesPreferences preferences) {
    return new CategoriesPresenterImpl(productHunt, repository, preferences);
  }
}
