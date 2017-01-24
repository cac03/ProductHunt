package com.caco3.producthunt.categories;


import com.caco3.producthunt.data.categories.CategoriesRepository;
import com.caco3.producthunt.producthunt.ProductHunt;
import com.caco3.producthunt.producthunt.TooManyRequestsException;
import com.caco3.producthunt.producthunt.category.CategoriesService;
import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.rx.RxSchedulersRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

public class CategoriesPresenterImplTest {
  @Rule
  public RxSchedulersRule allSchedulersAreImmediateRule
          = new RxSchedulersRule();
  @Mock
  private CategoriesView view;
  @Mock
  private ProductHunt productHunt;
  @Mock
  private CategoriesService categoriesService;
  @Mock
  private CategoriesPreferences preferences;
  @Mock
  private CategoriesRepository repository;
  private CategoriesPresenterImpl presenter;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    when(productHunt.categories()).thenReturn(categoriesService);
    presenter = new CategoriesPresenterImpl(productHunt, repository, preferences);
  }

  @Test
  public void viewAttached_showCategoriesCalled() {
    preventForceUpdate();
    final AtomicBoolean showCategoriesCalled = new AtomicBoolean();
    doAnswer(new Answer<Object>(){
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        showCategoriesCalled.set(true);
        return null;
      }
    }).when(view).showCategories(any(List.class));
    presenter.onViewAttached(view);
    assertThat(showCategoriesCalled.get())
            .isTrue();
  }

  @Test
  public void forceUpdateNeeded_viewUpdated() throws Exception {
    ensureForceUpdate();
    List<ProductHuntCategory> categories = new ArrayList<>();
    List<ProductHuntCategory> fromRepository = new ArrayList<>();
    when(repository.listAll()).thenReturn(fromRepository);
    categories.add(new ProductHuntCategory());
    when(categoriesService.getAll()).thenReturn(categories);
    final AtomicBoolean viewUpdatedWithNewCategories = new AtomicBoolean();
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        viewUpdatedWithNewCategories.set(true);
        return null;
      }
    }).when(view).showCategories(categories);
    presenter.onViewAttached(view);

    assertThat(viewUpdatedWithNewCategories.get())
            .isTrue();
  }

  @Test
  public void tooManyRequestsExceptionThrown_showErrorCalled() throws Exception {
    ensureForceUpdate();
    when(categoriesService.getAll()).thenThrow(TooManyRequestsException.class);
    final AtomicBoolean showErrorCalled = new AtomicBoolean();
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        showErrorCalled.set(true);
        return null;
      }
    }).when(view).showUnableLoadCategoriesDueRequestLimitError();
    presenter.onViewAttached(view);
    assertThat(showErrorCalled.get())
            .isTrue();
  }

  @Test
  public void ioExceptionThrown_showErrorCalled() throws Exception {
    ensureForceUpdate();
    when(categoriesService.getAll()).thenThrow(IOException.class);
    final AtomicBoolean showErrorCalled = new AtomicBoolean();
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        showErrorCalled.set(true);
        return null;
      }
    }).when(view).showUnableLoadCategoriesDueNetworkIssuesError();
    presenter.onViewAttached(view);

    assertThat(showErrorCalled.get())
            .isTrue();
  }

  @Test
  public void whenForceRefreshNeeded_showRefreshLayoutCalled() {
    ensureForceUpdate();
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
  public void categorySelected_navigateToCategoryViewCalled() {
    preventForceUpdate();

    ProductHuntCategory dummyCategory = new ProductHuntCategory();
    final AtomicBoolean navigateToCategoryViewCalled = new AtomicBoolean();
    doAnswer(new Answer() {
      @Override
      public Object answer(InvocationOnMock invocation) throws Throwable {
        navigateToCategoryViewCalled.set(true);
        return null;
      }
    }).when(view).navigateToCategoryView(dummyCategory);
    presenter.onViewAttached(view);
    presenter.onCategorySelected(dummyCategory);
    assertThat(navigateToCategoryViewCalled.get())
            .isTrue();
  }

  private void preventForceUpdate() {
    when(preferences.getLastUpdateTimeMillis()).thenReturn(System.currentTimeMillis());
  }

  private void ensureForceUpdate() {
    when(preferences.getLastUpdateTimeMillis()).thenReturn(System.currentTimeMillis()
            - 2 * CategoriesPresenterImpl.MIN_TIME_DIFF_MILLIS_TO_START_UPDATE);
  }
}
