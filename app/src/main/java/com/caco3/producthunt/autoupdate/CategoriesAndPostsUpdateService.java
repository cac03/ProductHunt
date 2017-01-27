package com.caco3.producthunt.autoupdate;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.caco3.producthunt.autoupdate.categoriesandposts.CategoriesAndPostsUpdater;
import com.caco3.producthunt.autoupdate.categoriesandposts.CategoryPostsUpdatedListener;
import com.caco3.producthunt.dagger.DaggerComponentsHolder;
import com.caco3.producthunt.producthunt.ProductHuntApiException;
import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Subscriber;
import rx.exceptions.Exceptions;
import timber.log.Timber;


public class CategoriesAndPostsUpdateService extends IntentService {
  @Inject
  CategoriesAndPostsUpdater categoriesAndPostsUpdater;
  @Inject
  List<CategoryPostsUpdatedListener> listeners;

  public CategoriesAndPostsUpdateService() {
    super("CategoriesAndPostsUpdateService");
  }

  @Override
  protected void onHandleIntent(Intent intent) {
    Timber.d("Running");
    DaggerComponentsHolder
            .getInstance()
            .getAutoUpdateComponent()
            .inject(this);
    try {
      update();
    } finally {
      Timber.d("Complete");
      WakefulBroadcastReceiver.completeWakefulIntent(intent);
      DaggerComponentsHolder
              .getInstance()
              .releaseAutoUpdateComponent();
    }
  }

  private void update() {
    categoriesAndPostsUpdater
            .startUpdate()
            .subscribe(new Subscriber<Map<ProductHuntCategory, List<ProductHuntPost>>>() {
              @Override
              public void onCompleted() {
              }

              @Override
              public void onError(Throwable e) {
                Timber.e(e, "Unable to update data.");
                if (!(e instanceof IOException || e instanceof ProductHuntApiException)) {
                  throw Exceptions.propagate(e);
                }
              }

              @Override
              public void onNext(Map<ProductHuntCategory,
                      List<ProductHuntPost>> productHuntCategoryListMap) {
                for(Map.Entry<ProductHuntCategory, List<ProductHuntPost>> entry
                        : productHuntCategoryListMap.entrySet()) {
                  for(CategoryPostsUpdatedListener listener : listeners) {
                    listener.onPostsUpdated(entry.getKey(), entry.getValue());
                  }
                }
              }
            });
  }
}
