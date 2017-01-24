package com.caco3.producthunt.categories;

import android.content.Context;
import android.content.SharedPreferences;

import static com.caco3.producthunt.util.Preconditions.checkNotNull;

class CategoriesPreferencesImpl implements CategoriesPreferences {
  private static final String PREFERENCES_FILENAME = "categories";
  private static final String LAST_UPDATE_TIME_MILLIS_KEY = "last_update_millis";

  private final SharedPreferences sharedPreferences;

  CategoriesPreferencesImpl(Context context) {
    this.sharedPreferences = checkNotNull(context, "context == null")
            .getSharedPreferences(PREFERENCES_FILENAME, Context.MODE_PRIVATE);
  }

  @Override
  public long getLastUpdateTimeMillis() {
    return sharedPreferences.getLong(LAST_UPDATE_TIME_MILLIS_KEY, 0);
  }

  @Override
  public void setLastUpdateTimeMillis(long lastUpdateTimeMillis) {
    sharedPreferences.edit().putLong(LAST_UPDATE_TIME_MILLIS_KEY, lastUpdateTimeMillis).apply();
  }
}
