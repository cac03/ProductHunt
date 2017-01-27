package com.caco3.producthunt.autoupdate.categoriesandposts;

import com.caco3.producthunt.data.categories.CategoriesRepository;
import com.caco3.producthunt.data.posts.PostsRepository;
import com.caco3.producthunt.producthunt.ProductHunt;
import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;


public class CategoriesAndPostsUpdaterImpl implements CategoriesAndPostsUpdater {
  private static final Comparator<ProductHuntPost> comparator
          = new Comparator<ProductHuntPost>() {
    @Override
    public int compare(ProductHuntPost o1, ProductHuntPost o2) {
      return Integer.compare(o1.getPostId(), o2.getPostId());
    }
  };

  private final ProductHunt productHunt;
  private final CategoriesRepository categoriesRepository;
  private final PostsRepository postsRepository;

  public CategoriesAndPostsUpdaterImpl(ProductHunt productHunt,
                                       CategoriesRepository categoriesRepository,
                                       PostsRepository postsRepository) {
    this.postsRepository = postsRepository;
    this.productHunt = productHunt;
    this.categoriesRepository = categoriesRepository;
  }

  @Override
  public Observable<Map<ProductHuntCategory, List<ProductHuntPost>>> startUpdate() {
    return Observable.create(new Observable.OnSubscribe<Map<ProductHuntCategory, List<ProductHuntPost>>>() {
      @Override
      public void call(Subscriber<? super Map<ProductHuntCategory, List<ProductHuntPost>>> subscriber) {
        try {
          List<ProductHuntCategory> receivedCategories = productHunt.categories().getAll();
          replaceCategoriesInRepository(receivedCategories);
          for (ProductHuntCategory receivedCategory : receivedCategories) {
            List<ProductHuntPost> receivedPosts = productHunt.posts().getTodayPosts(receivedCategory);
            List<ProductHuntPost> savedPosts = postsRepository.listAllByCategory(receivedCategory);
            List<ProductHuntPost> newPosts = extractNewPosts(savedPosts, receivedPosts);
            replacePostsInRepository(receivedCategory, receivedPosts);
            if (!subscriber.isUnsubscribed()) {
              subscriber.onNext(Collections.singletonMap(receivedCategory, newPosts));
            }
          }
          if (!subscriber.isUnsubscribed()) {
            subscriber.onCompleted();
          }
        } catch (Throwable tr) {
          if (!subscriber.isUnsubscribed()) {
            subscriber.onError(tr);
          } else {
            throw Exceptions.propagate(tr);
          }
        }
      }
    });
  }

  private List<ProductHuntPost> extractNewPosts(List<ProductHuntPost> savedPosts,
                                                List<ProductHuntPost> receivedPosts) {
    List<ProductHuntPost> newPosts = new ArrayList<>();
    Collections.sort(savedPosts, comparator);
    Collections.sort(receivedPosts, comparator);
    for (ProductHuntPost receivedPost : receivedPosts) {
      if (Collections.binarySearch(savedPosts, receivedPost, comparator) < 0) {
        newPosts.add(receivedPost);
      }
    }

    return newPosts;
  }

  private void replacePostsInRepository(ProductHuntCategory category, List<ProductHuntPost> posts) {
    postsRepository.removeAllByCategory(category);
    postsRepository.saveAll(posts);
  }

  private void replaceCategoriesInRepository(List<ProductHuntCategory> categories) {
    List<ProductHuntCategory> oldCategories = categoriesRepository.listAll();
    for(int i = 0; i < oldCategories.size(); i++) {
      for(int j = 0; j < categories.size(); j++) {
        if (oldCategories.get(i).getCategoryId() == categories.get(j).getCategoryId()) {
          categories.get(j).setNotificationsEnabled(oldCategories.get(i).getNotificationsEnabled());
        }
      }
    }

    categoriesRepository.removeAll();
    categoriesRepository.saveAll(categories);
  }
}
