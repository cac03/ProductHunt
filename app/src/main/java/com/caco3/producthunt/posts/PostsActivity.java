package com.caco3.producthunt.posts;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.caco3.producthunt.R;
import com.caco3.producthunt.producthunt.category.ProductHuntCategory;
import com.caco3.producthunt.ui.BaseActivity;

import static com.caco3.producthunt.util.Preconditions.checkState;


public class PostsActivity extends BaseActivity {
  private static final String EXTRA_CATEGORY_KEY = "category";
  private static final String FRAGMENT_TAG_PREFIX = "posts_frag";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base);
    checkState(getIntent().hasExtra(EXTRA_CATEGORY_KEY),
            "No category provided. Did you use static forCategory method to start this activity?");

    hostFragment();
  }

  public static Intent forCategory(Context context, ProductHuntCategory category) {
    return new Intent(context, PostsActivity.class)
            .putExtra(EXTRA_CATEGORY_KEY, category);
  }

  private void hostFragment() {
    ProductHuntCategory category = (ProductHuntCategory)getIntent()
            .getSerializableExtra(EXTRA_CATEGORY_KEY);
    String tag = FRAGMENT_TAG_PREFIX + category.getName();
    Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
    if (fragment == null) {
      fragment = PostsFragment.forCategory(category);
      fragment.setRetainInstance(true);
    }
    getSupportFragmentManager().beginTransaction()
            .replace(R.id.activity_base_fragment_container, fragment, tag)
            .commitNow();
  }
}
