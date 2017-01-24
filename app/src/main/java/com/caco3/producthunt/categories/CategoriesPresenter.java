package com.caco3.producthunt.categories;


import com.caco3.producthunt.mvp.BasePresenter;
import com.caco3.producthunt.producthunt.category.ProductHuntCategory;

public interface CategoriesPresenter extends BasePresenter<CategoriesView> {
  void onRefreshRequest();
  void onCategorySelected(ProductHuntCategory category);
}
