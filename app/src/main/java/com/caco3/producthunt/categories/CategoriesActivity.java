package com.caco3.producthunt.categories;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.caco3.producthunt.R;
import com.caco3.producthunt.ui.BaseActivity;


public class CategoriesActivity extends BaseActivity {
  private static final String FRAGMENT_TAG = "categories_frag";

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_base);

    hostFragment();
  }

  private void hostFragment() {
    Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG);
    if (fragment == null) {
      fragment = new CategoriesFragment();
      fragment.setRetainInstance(true);
    }
    getSupportFragmentManager().beginTransaction()
            .replace(R.id.activity_base_fragment_container, fragment, FRAGMENT_TAG)
            .commitNow();
  }
}
