package com.caco3.producthunt.categories;

import com.caco3.producthunt.mvp.BaseView;
import com.caco3.producthunt.producthunt.category.ProductHuntCategory;

import java.util.List;


public interface CategoriesView extends BaseView {
  void showCategories(List<ProductHuntCategory> categories);
  void navigateToCategoryView(ProductHuntCategory category);
  void showRefreshLayout();
  void hideRefreshLayout();
  void showUnableLoadCategoriesDueNetworkIssuesError();
  void showUnableLoadCategoriesDueRequestLimitError();
}
