package com.caco3.producthunt;

import com.caco3.producthunt.appuser.AppUserModule;
import com.caco3.producthunt.autoupdate.AutoUpdateComponent;
import com.caco3.producthunt.autoupdate.AutoUpdateModule;
import com.caco3.producthunt.categories.CategoriesComponent;
import com.caco3.producthunt.categories.CategoriesModule;
import com.caco3.producthunt.data.DataModule;
import com.caco3.producthunt.network.NetworkModule;
import com.caco3.producthunt.post.PostComponent;
import com.caco3.producthunt.post.PostModule;
import com.caco3.producthunt.posts.PostsComponent;
import com.caco3.producthunt.posts.PostsModule;
import com.caco3.producthunt.producthunt.ProductHuntModule;

import javax.inject.Singleton;

import dagger.Component;

@Component(
        modules = {
                ApplicationModule.class,
                AppUserModule.class,
                DataModule.class,
                NetworkModule.class,
                ProductHuntModule.class
        }
)
@Singleton
public interface ApplicationComponent {
  CategoriesComponent plus(CategoriesModule categoriesModule);
  PostsComponent plus(PostsModule postsModule);
  PostComponent plus(PostModule postModule);
  AutoUpdateComponent plus(AutoUpdateModule autoUpdateModule);
}
