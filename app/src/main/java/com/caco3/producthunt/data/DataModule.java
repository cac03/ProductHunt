package com.caco3.producthunt.data;

import android.content.Context;

import com.caco3.producthunt.data.categories.CategoriesRepository;
import com.caco3.producthunt.data.categories.CategoriesRepositoryImpl;
import com.caco3.producthunt.producthunt.category.DaoMaster;
import com.caco3.producthunt.producthunt.category.DaoSession;

import org.greenrobot.greendao.database.Database;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class DataModule {
  private static final String DATABASE_NAME = "ph.db";
  private final CategoriesRepository categoriesRepository;

  public DataModule(Context context) {
    DaoSession daoSession = createDaoSession(context);
    this.categoriesRepository = new CategoriesRepositoryImpl(daoSession.getProductHuntCategoryDao());
  }

  private DaoSession createDaoSession(Context context) {
    DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, DATABASE_NAME);
    Database db = helper.getWritableDb();
    return new DaoMaster(db).newSession();
  }

  @Provides
  @Singleton
  public CategoriesRepository provideCategoriesRepository() {
    return categoriesRepository;
  }
}
