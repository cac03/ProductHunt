package com.caco3.producthunt.ui;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.caco3.producthunt.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseActivity extends AppCompatActivity {
  @Nullable
  @BindView(R.id.toolbar)
  Toolbar toolbar;

  @Override
  public void setContentView(@LayoutRes int layoutResID) {
    super.setContentView(layoutResID);
    ButterKnife.bind(this);
    setUpToolbar();
  }

  private void setUpToolbar() {
    if (toolbar != null) {
      setSupportActionBar(toolbar);
      ActionBar actionBar = getSupportActionBar();
      if (actionBar != null && hasParentActivity()) {
        actionBar.setDisplayHomeAsUpEnabled(true);
      }
    }
  }

  protected boolean hasParentActivity() {
    return false;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      NavUtils.navigateUpFromSameTask(this);
      return true;
    } else {
      return super.onOptionsItemSelected(item);
    }
  }
}
