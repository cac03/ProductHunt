package com.caco3.producthunt.data;

import android.content.Context;

import com.caco3.producthunt.data.categories.CategoriesRepository;
import com.caco3.producthunt.data.categories.CategoriesRepositoryImpl;
import com.caco3.producthunt.data.posts.PostsRepository;
import com.caco3.producthunt.data.posts.PostsRepositoryImpl;
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
  private final PostsRepository postsRepository;

  public DataModule(Context context) {
    DaoSession daoSession = createDaoSession(context);
    this.categoriesRepository = new CategoriesRepositoryImpl(daoSession.getProductHuntCategoryDao());
    this.postsRepository = new PostsRepositoryImpl(daoSession.getProductHuntPostDao());
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

  @Provides
  @Singleton
  public PostsRepository providePostsRepository() {
    return postsRepository;
  }
}
