package com.caco3.producthunt.post;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.caco3.producthunt.R;
import com.caco3.producthunt.dagger.DaggerComponentsHolder;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;
import com.caco3.producthunt.webview.WebViewActivity;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

import static com.caco3.producthunt.util.Preconditions.checkState;


public class PostFragment extends Fragment implements PostView {
  private static final String POST_ARG_KEY = "post";
  @BindView(R.id.post_frag_screenshot)
  ImageView screenshot;
  @BindView(R.id.post_frag_post_title)
  TextView title;
  @BindView(R.id.post_frag_post_description)
  TextView description;
  @BindView(R.id.post_frag_post_votes_count)
  TextView upvotesCount;
  @BindView(R.id.post_frag_progress_bar)
  ProgressBar progressBar;
  @BindView(R.id.post_frag_unable_to_load_screenshot_view)
  TextView unableToLoadScreenshotView;
  @Inject
  PostPresenter presenter;

  public static PostFragment forProductHuntPost(ProductHuntPost post) {
    Bundle args = new Bundle();

    args.putSerializable(POST_ARG_KEY, post);
    PostFragment fragment = new PostFragment();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.post_fragment, container, false);
    ButterKnife.bind(this, root);

    return root;
  }

  @Override
  public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);
    if (presenter == null) {
      Bundle args = getArguments();
      checkState(args.containsKey(POST_ARG_KEY), "No productHuntPost provided. " +
              "Did you use forProductHuntPost static method to instantiate this fragment?");
      ProductHuntPost productHuntPost = (ProductHuntPost) args.getSerializable(POST_ARG_KEY);
      DaggerComponentsHolder
              .getInstance()
              .getPostComponent(productHuntPost)
              .inject(this);
    }
    presenter.onViewAttached(this);
  }

  @Override
  public void onDestroyView() {
    presenter.onViewDetached(this);
    Picasso.with(getContext())
            .cancelRequest(screenshot);
    super.onDestroyView();
  }

  @Override
  public void onDestroy() {
    DaggerComponentsHolder
            .getInstance()
            .releasePostComponent();
    super.onDestroy();
  }

  @Override
  public void showPost(ProductHuntPost post) {
    Picasso.with(getContext())
            .load(post.getScreenshot().getUrl850px())
            .centerCrop()
            .fit()
            .into(screenshot, new Callback() {
              @Override
              public void onSuccess() {
                hideScreenshotLoadingError();
                hideProgress();
              }

              @Override
              public void onError() {
                hideProgress();
                showScreenshotLoadingError();
              }
            });
    title.setText(post.getName());
    description.setText(post.getTagline());
    upvotesCount.setText(post.getVotesCount() + "");
    getActivity().setTitle(post.getName());
  }

  private void hideProgress() {
    if (progressBar != null) {
      progressBar.setVisibility(View.GONE);
    }
  }

  private void showScreenshotLoadingError() {
    if (unableToLoadScreenshotView != null) {
      unableToLoadScreenshotView.setVisibility(View.VISIBLE);
    }
  }

  private void hideScreenshotLoadingError() {
    if (unableToLoadScreenshotView != null) {
      unableToLoadScreenshotView.setVisibility(View.GONE);
    }
  }


  @Override
  public void openPostProductSite(ProductHuntPost post) {
    startActivity(WebViewActivity.forUrl(getContext(), post.getRedirectUrl()));
  }

  @OnClick(R.id.post_frag_get_it_btn)
  void onGetItClicked() {
    presenter.onGetItClicked();
  }
}
