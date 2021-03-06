package com.caco3.producthunt.posts;


import com.caco3.producthunt.data.categories.CategoriesRepository;
import com.caco3.producthunt.data.posts.PostsRepository;
import com.caco3.producthunt.producthunt.ProductHunt;
import com.caco3.producthunt.producthunt.TooManyRequestsException;
import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.exceptions.Exceptions;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

import static com.caco3.producthunt.util.Preconditions.checkNotNull;

class PostsPresenterImpl implements PostsPresenter {
  private PostsView view;
  private ProductHuntCategory category;
  private PostsRepository postsRepository;
  private ProductHunt productHunt;
  private CategoriesRepository categoriesRepository;
  Subscriber<List<ProductHuntPost>> postsSubscriber;

  PostsPresenterImpl(ProductHuntCategory category,
                     PostsRepository repository, ProductHunt productHunt,
                     CategoriesRepository categoriesRepository) {
    this.category = checkNotNull(category, "category == null");
    this.postsRepository = checkNotNull(repository, "repository == null");
    this.productHunt = checkNotNull(productHunt, "productHunt == null");
    this.categoriesRepository = checkNotNull(categoriesRepository, "categoriesRepository == null");
  }

  @Override
  public void onViewAttached(PostsView view) {
    this.view = checkNotNull(view, "view == null");
    initView();
  }

  private void initView() {
    if (isViewAttached()) {
      loadPostsFromRepositoryToView();
      if (arePostsLoadingFromProductHunt()) {
        view.showRefreshLayout();
      }
      if (category.getNotificationsEnabled()) {
        view.showDisableNotificationsButton();
      } else {
        view.showEnableNotificationsButton();
      }
    }
  }

  private boolean isViewAttached() {
    return view != null;
  }

  private boolean arePostsLoadingFromProductHunt() {
    return postsSubscriber != null && !postsSubscriber.isUnsubscribed();
  }

  private void loadPostsFromRepositoryToView() {
    Observable.fromCallable(new Callable<List<ProductHuntPost>>() {
      @Override
      public List<ProductHuntPost> call() throws Exception {
        return postsRepository.listAllByCategory(category);
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Action1<List<ProductHuntPost>>() {
              @Override
              public void call(List<ProductHuntPost> posts) {
                if (isViewAttached()) {
                  view.showPosts(posts);
                }
              }
            });
  }

  @Override
  public void onViewDetached(PostsView view) {
    this.view = null;
  }

  @Override
  public void onRefreshRequest() {
    loadAndSavePostsFromProductHunt();
  }

  private void loadAndSavePostsFromProductHunt() {
    postsSubscriber = new PostsSubscriber();
    Observable.fromCallable(new Callable<List<ProductHuntPost>>() {
      @Override
      public List<ProductHuntPost> call() throws Exception {
        List<ProductHuntPost> posts = productHunt.posts().getTodayPosts(category);
        postsRepository.replaceAllInCategoryWith(category, posts);

        return posts;
      }
    }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(postsSubscriber);
  }

  @Override
  public void onPostSelected(ProductHuntPost post) {
    if (isViewAttached()) {
      view.navigateToPostView(post);
    }
  }

  private class PostsSubscriber extends Subscriber<List<ProductHuntPost>> {
    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
      postsSubscriber = null;
      Timber.e(e, "Unable to load posts: %s", e.getMessage());
      if (isViewAttached()) {
        view.hideRefreshLayout();
      }
      if (e instanceof IOException){
        if (isViewAttached()) {
          view.showUnableLoadPostsDueNetworkIssuesError();
        }
      } else if (e instanceof TooManyRequestsException) {
        if (isViewAttached()) {
          view.showUnableLoadPostsDueRequestLimitError();
        }
      } else {
        throw Exceptions.propagate(e);
      }
    }

    @Override
    public void onNext(List<ProductHuntPost> posts) {
      postsSubscriber = null;
      if (isViewAttached()) {
        view.hideRefreshLayout();
        view.showPosts(posts);
      }
    }
  }

  @Override
  public void toggleNotificationsEnabledSetting() {
    boolean enabled = category.getNotificationsEnabled();
    category.setNotificationsEnabled(!enabled);
    categoriesRepository.update(category);
    if (isViewAttached()) {
      if (!enabled) {
        view.showDisableNotificationsButton();
      } else {
        view.showEnableNotificationsButton();
      }
    }
  }
}
