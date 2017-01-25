package com.caco3.producthunt.posts;

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
import com.caco3.producthunt.post.PostActivity;
import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;
import com.caco3.producthunt.ui.recyclerview.itemdecorator.MarginItemDecorator;

import java.util.Comparator;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PostsFragment extends Fragment
        implements PostsView, PostsAdapter.UiEventsListener,
        SwipeRefreshLayout.OnRefreshListener {
  private static final String CATEGORY_ARG_KEY = "category";
  private static final Comparator<ProductHuntPost> postComparator = new Comparator<ProductHuntPost>() {
    @Override
    public int compare(ProductHuntPost o1, ProductHuntPost o2) {
      return o2.getPostId() - o1.getPostId();
    }
  };
  private final PostsAdapter adapter = new PostsAdapter(this, postComparator);
  @Inject
  PostsPresenter presenter;
  @BindView(R.id.posts_frag_refresh_layout)
  SwipeRefreshLayout refreshLayout;
  @BindView(R.id.posts_frag_recycler_view)
  RecyclerView recyclerView;

  public static PostsFragment forCategory(ProductHuntCategory category) {
    Bundle args = new Bundle();
    args.putSerializable(CATEGORY_ARG_KEY, category);

    PostsFragment fragment = new PostsFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater,
                           @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.posts_fragment, container, false);
    ButterKnife.bind(this, root);
    initViews();

    return root;
  }

  private void initViews() {
    initRecyclerView();
    initSwipeRefreshLayout();
  }

  private void initRecyclerView() {
    recyclerView.setAdapter(adapter);
    int margin = (int)getResources().getDimension(R.dimen.default_padding) / 2;
    recyclerView.addItemDecoration(new MarginItemDecorator(margin, margin));
  }

  private void initSwipeRefreshLayout() {
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
    ProductHuntCategory category = (ProductHuntCategory)getArguments()
            .getSerializable(CATEGORY_ARG_KEY);
    DaggerComponentsHolder
            .getInstance()
            .getPostsComponent(category)
            .inject(this);
  }

  @Override
  public void onDestroyView() {
    presenter.onViewDetached(this);
    super.onDestroyView();
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
  public void showUnableLoadPostsDueNetworkIssuesError() {
    Toast.makeText(getContext(), R.string.unable_to_load_posts_network_error, Toast.LENGTH_SHORT)
            .show();
  }

  @Override
  public void showUnableLoadPostsDueRequestLimitError() {
    Toast.makeText(getContext(), R.string.unable_to_load_posts_requests_limit, Toast.LENGTH_SHORT)
            .show();
  }

  @Override
  public void showPosts(List<ProductHuntPost> posts) {
    adapter.setItems(posts);
  }

  @Override
  public void navigateToPostView(ProductHuntPost post) {
    startActivity(PostActivity.forProductHuntPost(getContext(), post));
  }

  @Override
  public void onPostClicked(ProductHuntPost post) {
    presenter.onPostSelected(post);
  }
}
