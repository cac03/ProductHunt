package com.caco3.producthunt.autoupdate.categoriesandposts;


import com.caco3.producthunt.autoupdate.categoriesandposts.CategoriesAndPostsUpdaterImpl;
import com.caco3.producthunt.data.categories.CategoriesRepository;
import com.caco3.producthunt.data.posts.PostsRepository;
import com.caco3.producthunt.producthunt.ProductHunt;
import com.caco3.producthunt.producthunt.category.CategoriesService;
import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.producthunt.category.ProductHuntCategoryGenerator;
import com.caco3.producthunt.producthunt.posts.PostsService;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;
import com.caco3.producthunt.producthunt.posts.ProductHuntPostsGenerator;
import com.caco3.producthunt.rx.RxSchedulersRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import rx.observers.TestSubscriber;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CategoriesAndPostsUpdaterImplTest {
  @Rule
  public RxSchedulersRule rxSchedulersRule = new RxSchedulersRule();
  @Mock
  private ProductHunt productHunt;
  @Mock
  private CategoriesService categoriesService;
  @Mock
  private PostsService postsService;
  @Mock
  private CategoriesRepository categoriesRepository;
  @Mock
  private PostsRepository postsRepository;
  private ProductHuntCategoryGenerator categoryGenerator = new ProductHuntCategoryGenerator();
  private ProductHuntPostsGenerator productHuntPostsGenerator = new ProductHuntPostsGenerator();
  private CategoriesAndPostsUpdaterImpl categoriesAndPostsUpdater;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    initProductHunt();
    categoriesAndPostsUpdater = new CategoriesAndPostsUpdaterImpl(productHunt,
            categoriesRepository, postsRepository);
  }

  private void initProductHunt() {
    when(productHunt.categories()).thenReturn(categoriesService);
    when(productHunt.posts()).thenReturn(postsService);
  }

  @Test
  public void everythingIsOk_onNextCalledForEachCategory() throws Exception {
    List<ProductHuntCategory> categories = categoryGenerator.generateList(2);
    when(categoriesService.getAll()).thenReturn(categories);
    List<ProductHuntPost> receivedPosts = productHuntPostsGenerator.generateList(10);
    when(postsService.getTodayPosts(any(ProductHuntCategory.class))).thenReturn(receivedPosts);
    TestSubscriber<Map<ProductHuntCategory, List<ProductHuntPost>>> testSubscriber
            = new TestSubscriber<>();
    categoriesAndPostsUpdater.startUpdate().subscribe(testSubscriber);
    testSubscriber.assertCompleted();
    testSubscriber.assertNoErrors();
    assertThat(testSubscriber.getOnNextEvents())
            .contains(Collections.singletonMap(categories.get(0), receivedPosts),
                    Collections.singletonMap(categories.get(1), receivedPosts))
            .hasSize(2);
  }

  @Test
  public void exceptionThrown_onErrorCalled() throws Exception {
    Exception toThrow = new IOException();
    when(categoriesService.getAll()).thenThrow(toThrow);
    TestSubscriber<Map<ProductHuntCategory, List<ProductHuntPost>>> subscriber = new TestSubscriber<>();
    categoriesAndPostsUpdater.startUpdate().subscribe(subscriber);
    subscriber.assertError(toThrow);
    subscriber.assertNotCompleted();
  }
}
