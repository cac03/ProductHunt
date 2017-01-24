package com.caco3.producthunt;

import com.caco3.producthunt.appuser.AppUserModule;
import com.caco3.producthunt.categories.CategoriesComponent;
import com.caco3.producthunt.categories.CategoriesModule;
import com.caco3.producthunt.data.DataModule;
import com.caco3.producthunt.network.NetworkModule;
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
}
