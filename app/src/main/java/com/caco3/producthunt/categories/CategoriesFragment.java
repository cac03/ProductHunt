package com.caco3.producthunt.categories;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.caco3.producthunt.R;
import com.caco3.producthunt.dagger.DaggerComponentsHolder;
import com.caco3.producthunt.posts.PostsActivity;
import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.ui.recyclerview.itemdecorator.MarginItemDecorator;

import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CategoriesFragment extends Fragment
        implements CategoriesView, CategoriesAdapter.UiEventsListener,
        SwipeRefreshLayout.OnRefreshListener {
  private static final Comparator<ProductHuntCategory> categoryComparator
          = new Comparator<ProductHuntCategory>() {
    @Override
    public int compare(ProductHuntCategory o1, ProductHuntCategory o2) {
      return o1.getName().compareTo(o2.getName());
    }
  };
  @Inject
  CategoriesPresenter presenter;

  @BindView(R.id.categories_frag_refresh_layout)
  SwipeRefreshLayout refreshLayout;
  @BindView(R.id.categories_frag_recycler_view)
  RecyclerView recyclerView;
  private final CategoriesAdapter adapter = new CategoriesAdapter(this, categoryComparator);

  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.categories_fragment, container, false);
    ButterKnife.bind(this, root);

    initViews();
    return root;
  }

  private void initViews() {
    initRecyclerView();
    initRefreshLayout();
  }

  private void initRecyclerView() {
    recyclerView.setAdapter(adapter);
    int margin = (int)getResources().getDimension(R.dimen.default_padding) / 2;
    recyclerView.addItemDecoration(new MarginItemDecorator(margin, margin));
  }

  private void initRefreshLayout() {
    refreshLayout.setOnRefreshListener(this);
  }

  @Override
  public void onRefresh() {
    presenter.onRefreshRequest();
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (presenter == null) {
      injectPresenter();
    }
    presenter.onViewAttached(this);
  }

  private void injectPresenter() {
    DaggerComponentsHolder.getInstance()
            .getCategoriesComponent().inject(this);
  }

  @Override
  public void onDestroyView() {
    presenter.onViewDetached(this);
    super.onDestroyView();
  }

  @Override
  public void onItemClicked(ProductHuntCategory category) {
    presenter.onCategorySelected(category);
  }

  @Override
  public void showCategories(List<ProductHuntCategory> categories) {
    adapter.setItems(categories);
  }

  @Override
  public void navigateToCategoryView(ProductHuntCategory category) {
    startActivity(PostsActivity.forCategory(getContext(), category));
  }

  @Override
  public void showRefreshLayout() {
    refreshLayout.setRefreshing(true);
  }

  @Override
  public void hideRefreshLayout() {
    refreshLayout.setRefreshing(false);
  }

  @Override
  public void showUnableLoadCategoriesDueNetworkIssuesError() {
    Toast.makeText(getContext(), R.string.unable_to_load_categories_network_error, Toast.LENGTH_SHORT)
            .show();
  }

  @Override
  public void showUnableLoadCategoriesDueRequestLimitError() {
    Toast.makeText(getContext(), R.string.unable_to_load_categories_request_limit, Toast.LENGTH_LONG)
            .show();
  }
}
