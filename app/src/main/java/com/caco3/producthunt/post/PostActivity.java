package com.caco3.producthunt.post;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.caco3.producthunt.R;
import com.caco3.producthunt.producthunt.posts.ProductHuntPost;
import com.caco3.producthunt.ui.BaseActivity;

import static com.caco3.producthunt.util.Preconditions.checkState;


public class PostActivity extends BaseActivity {
  private static final String PRODUCT_HUNT_POST_EXTRA_KEY = "post";
  private static final String FRAGMENT_TAG = "post_frag";

  public static Intent forProductHuntPost(Context context, ProductHuntPost post) {
    return new Intent(context, PostActivity.class)
            .putExtra(PRODUCT_HUNT_POST_EXTRA_KEY, post);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base);

    hostFragment();
  }

  private void hostFragment() {
    checkState(getIntent().hasExtra(PRODUCT_HUNT_POST_EXTRA_KEY), "No productHunt post provided. " +
            "Did you use forProductHuntPost static method to start this activity?");
    Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    if (fragment == null) {
      ProductHuntPost post = (ProductHuntPost)getIntent().getSerializableExtra(PRODUCT_HUNT_POST_EXTRA_KEY);
      fragment = PostFragment.forProductHuntPost(post);
      fragment.setRetainInstance(true);
    }
    getSupportFragmentManager()
            .beginTransaction()
            .replace(R.id.activity_base_fragment_container, fragment, FRAGMENT_TAG)
            .commitNow();
  }

  @Override
  protected boolean hasParentActivity() {
    return true;
  }
}
