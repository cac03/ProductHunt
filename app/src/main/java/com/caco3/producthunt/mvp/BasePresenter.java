package com.caco3.producthunt.mvp;


public interface BasePresenter<V extends BaseView> {
  void onViewAttached(V view);
  void onViewDetached(V view);
}
