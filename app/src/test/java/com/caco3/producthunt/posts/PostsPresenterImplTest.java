package com.caco3.producthunt.posts;


import com.caco3.producthunt.data.posts.PostsRepository;
import com.caco3.producthunt.producthunt.ProductHunt;
import com.caco3.producthunt.producthunt.TooManyRequestsException;
import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.producthunt.posts.ProductHuntPostsGenerator;
import com.caco3.producthunt.producthunt.posts.PostsService;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;
import com.caco3.producthunt.rx.RxSchedulersRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import rx.observers.TestSubscriber;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class PostsPresenterImplTest {
  @Mock
  private PostsRepository postsRepository;
  @Mock
  private ProductHunt productHunt;
  @Mock
  private PostsService postsService;
  @Mock
  private PostsView view;
  @Mock
  private ProductHuntCategory dummyCategory;
  @Rule
  public RxSchedulersRule rxSchedulersRule = new RxSchedulersRule();
  private PostsPresenterImpl presenter;
  private ProductHuntPostsGenerator productHuntPostsGenerator = new ProductHuntPostsGenerator();


  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    when(productHunt.posts()).thenReturn(postsService);
    presenter = new PostsPresenterImpl(dummyCategory, postsRepository, productHunt);
  }

  @Test
  public void viewAttached_postsFromRepositoryShown() {
    List<ProductHuntPost> posts = productHuntPostsGenerator.generateList(10);
    when(postsRepository.listAllByCategory(dummyCategory)).thenReturn(posts);
    final AtomicBoolean postsFromRepositoryShown = new AtomicBoolean(false);
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        postsFromRepositoryShown.set(true);
        return null;
      }
    }).when(view).showPosts(posts);
    presenter.onViewAttached(view);
    assertThat(postsFromRepositoryShown.get())
            .isTrue();
  }

  @Test
  public void postsLoaded_postsSavedToRepository() throws Exception {
    List<ProductHuntPost> posts = productHuntPostsGenerator.generateList(10);
    when(postsService.getTodayPosts(dummyCategory)).thenReturn(posts);
    final AtomicBoolean postsSaved = new AtomicBoolean();
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        postsSaved.set(true);
        return null;
      }
    }).when(postsRepository).saveAll(posts);
    presenter.onRefreshRequest();

    assertThat(postsSaved.get())
            .isTrue();
  }

  @Test
  public void ioExceptionThrownWhenLoadingPosts_showNetworkErrorCalled() throws Exception {
    when(postsService.getTodayPosts(dummyCategory)).thenThrow(IOException.class);
    final AtomicBoolean showNetworkErrorCalled = new AtomicBoolean();
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        showNetworkErrorCalled.set(true);
        return null;
      }
    }).when(view).showUnableLoadPostsDueNetworkIssuesError();
    presenter.onViewAttached(view);
    presenter.onRefreshRequest();

    assertThat(showNetworkErrorCalled.get())
            .isTrue();
  }

  @Test
  public void postsLoaded_newPostsShownInView() throws Exception {
    List<ProductHuntPost> postsFromProductHunt = productHuntPostsGenerator.generateList(10);
    when(postsService.getTodayPosts(dummyCategory)).thenReturn(postsFromProductHunt);
    final AtomicBoolean newPostsShown = new AtomicBoolean();
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        newPostsShown.set(true);
        return null;
      }
    }).when(view).showPosts(postsFromProductHunt);
    presenter.onViewAttached(view);
    presenter.onRefreshRequest();

    assertThat(newPostsShown.get())
            .isTrue();
  }

  @Test
  public void tooManyRequestsExceptionThrown_showErrorCalled() throws Exception {
    when(postsService.getTodayPosts(dummyCategory)).thenThrow(TooManyRequestsException.class);
    final AtomicBoolean showErrorCalled = new AtomicBoolean();
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        showErrorCalled.set(true);
        return null;
      }
    }).when(view).showUnableLoadPostsDueRequestLimitError();
    presenter.onViewAttached(view);
    presenter.onRefreshRequest();

    assertThat(showErrorCalled.get())
            .isTrue();
  }

  @Test
  public void postsLoadingViewAttached_showRefreshLayoutCalled() {
    presenter.postsSubscriber = new TestSubscriber<>();
    final AtomicBoolean showRefreshLayoutCalled = new AtomicBoolean();
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        showRefreshLayoutCalled.set(true);
        return null;
      }
    }).when(view).showRefreshLayout();
    presenter.onViewAttached(view);

    assertThat(showRefreshLayoutCalled.get())
            .isTrue();
  }

  @Test
  public void postSelected_openPostViewCalled() {
    final ProductHuntPost dummyPost = productHuntPostsGenerator.generateOne();
    final AtomicBoolean openPostViewCalled = new AtomicBoolean();
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        openPostViewCalled.set(true);
        return null;
      }
    }).when(view).navigateToPostView(dummyPost);
    presenter.onViewAttached(view);
    presenter.onPostSelected(dummyPost);

    assertThat(openPostViewCalled.get())
            .isTrue();
  }
}
