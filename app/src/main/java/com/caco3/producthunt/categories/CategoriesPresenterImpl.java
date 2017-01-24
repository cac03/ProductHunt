package com.caco3.producthunt.categories;

import com.caco3.producthunt.data.categories.CategoriesRepository;
import com.caco3.producthunt.producthunt.ProductHunt;
import com.caco3.producthunt.producthunt.TooManyRequestsException;
import com.caco3.producthunt.producthunt.category.ProductHuntCategory;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static com.caco3.producthunt.util.Preconditions.checkNotNull;


public class CategoriesPresenterImpl implements CategoriesPresenter {
  static final long MIN_TIME_DIFF_MILLIS_TO_START_UPDATE = TimeUnit.HOURS.toMillis(8);

  private CategoriesView view;
  private ProductHunt productHunt;
  private CategoriesRepository repository;
  private CategoriesPreferences preferences;
  private Subscriber<List<ProductHuntCategory>> productHuntCategoriesSubscriber;

  public CategoriesPresenterImpl(ProductHunt productHunt, CategoriesRepository repository,
                                 CategoriesPreferences preferences) {
    this.productHunt = checkNotNull(productHunt);
    this.repository = checkNotNull(repository);
    this.preferences = checkNotNull(preferences);
  }

  @Override
  public void onViewAttached(CategoriesView view) {
    this.view = checkNotNull(view, "view == null");
    initView();
  }

  private boolean isViewAttached() {
    return view != null;
  }

  private void initView() {
    if (isViewAttached()) {
      if (needToShowRefreshLayout()) {
        view.showRefreshLayout();
      }
      loadCategoriesFromRepository();
      if (isForceUpdateNeeded()) {
        view.showRefreshLayout();
        onRefreshRequest();
      }
    }
  }

  private void loadCategoriesFromRepository() {
    Observable.fromCallable(new Callable<List<ProductHuntCategory>>() {
      @Override
      public List<ProductHuntCategory> call() throws Exception {
        return repository.listAll();
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<ProductHuntCategory>>() {
              @Override
              public void call(List<ProductHuntCategory> categories) {
                if (isViewAttached()) {
                  view.showCategories(categories);
                }
              }
            });
  }

  private boolean needToShowRefreshLayout() {
    return productHuntCategoriesSubscriber != null
            && !productHuntCategoriesSubscriber.isUnsubscribed();
  }

  private boolean isForceUpdateNeeded() {
    long rightNow = System.currentTimeMillis();
    long diff = rightNow - preferences.getLastUpdateTimeMillis();

    return diff > MIN_TIME_DIFF_MILLIS_TO_START_UPDATE;
  }

  @Override
  public void onViewDetached(CategoriesView view) {
    this.view = null;
  }

  @Override
  public void onRefreshRequest() {
    loadAndSaveCategoriesFromProductHunt();
  }

  private void loadAndSaveCategoriesFromProductHunt() {
    productHuntCategoriesSubscriber = new ProductHuntCategoriesSubscriber();
    Observable.fromCallable(new Callable<List<ProductHuntCategory>>() {
      @Override
      public List<ProductHuntCategory> call() throws Exception {
        List<ProductHuntCategory> categories = productHunt.categories().getAll();
        saveCategoriesToRepository(categories);
        updateLastTimeUpdate();
        return categories;
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(productHuntCategoriesSubscriber);
  }

  private void saveCategoriesToRepository(List<ProductHuntCategory> categories) {
    repository.removeAll();
    repository.saveAll(categories);
  }

  private void updateLastTimeUpdate() {
    preferences.setLastUpdateTimeMillis(System.currentTimeMillis());
  }

  @Override
  public void onCategorySelected(ProductHuntCategory category) {
    if (isViewAttached()) {
      view.navigateToCategoryView(category);
    }
  }

  private class ProductHuntCategoriesSubscriber extends Subscriber<List<ProductHuntCategory>> {
    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
      productHuntCategoriesSubscriber = null;
      Timber.e(e, "Unable to load categories from product hunt: %s", e.getMessage());
      if (isViewAttached()) {
        view.hideRefreshLayout();
      }
      if (e instanceof IOException) {
        if (isViewAttached()) {
          view.showUnableLoadCategoriesDueNetworkIssuesError();
        }
      } else if (e instanceof TooManyRequestsException) {
        if (isViewAttached()) {
          view.showUnableLoadCategoriesDueRequestLimitError();
        }
      } else {
        throw Exceptions.propagate(e);
      }
    }

    @Override
    public void onNext(List<ProductHuntCategory> categories) {
      productHuntCategoriesSubscriber = null;
      if (isViewAttached()) {
        view.hideRefreshLayout();
        view.showCategories(categories);
      }
    }
  }
}
