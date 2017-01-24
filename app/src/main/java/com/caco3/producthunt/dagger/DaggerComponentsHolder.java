package com.caco3.producthunt.dagger;


import android.content.Context;

import com.caco3.producthunt.ApplicationComponent;
import com.caco3.producthunt.ApplicationModule;
import com.caco3.producthunt.DaggerApplicationComponent;
import com.caco3.producthunt.categories.CategoriesComponent;
import com.caco3.producthunt.categories.CategoriesModule;
import com.caco3.producthunt.data.DataModule;
import com.caco3.producthunt.network.NetworkModule;
import com.caco3.producthunt.producthunt.ProductHuntModule;

public class DaggerComponentsHolder {
  private static final DaggerComponentsHolder instance = new DaggerComponentsHolder();
  private ApplicationComponent applicationComponent;
  private CategoriesComponent categoriesComponent;

  public static DaggerComponentsHolder getInstance() {
    return instance;
  }

  private DaggerComponentsHolder(){
  }

  public void initApplicationComponent(Context context) {
    this.applicationComponent = DaggerApplicationComponent
            .builder()
            .networkModule(new NetworkModule())
            .productHuntModule(new ProductHuntModule())
            .dataModule(new DataModule(context))
            .applicationModule(new ApplicationModule(context))
            .build();
  }

  public CategoriesComponent getCategoriesComponent() {
    if (categoriesComponent == null) {
      categoriesComponent = applicationComponent.plus(new CategoriesModule());
    }
    return categoriesComponent;
  }

  public void releaseCategoriesComponent() {
    categoriesComponent = null;
  }
}
