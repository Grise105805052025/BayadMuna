package klo0812.mlaserna.bayadmuna.modules

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import klo0812.mlaserna.bayadmuna.pages.login.navigation.LoginNavigation
import klo0812.mlaserna.bayadmuna.pages.main.navigation.MainNavigation

/**
 * Module used to provide network related injections.
 *
 * @author Roy M
 * @version 1.0
 * @since 2025-11-24
 */
@Module
@InstallIn(ActivityComponent::class)
class NavigationModule {

    @Provides
    fun provideLoginNavigation(activity: Activity): LoginNavigation {
        return activity as LoginNavigation
    }

    @Provides
    fun provideMainNavigation(activity: Activity): MainNavigation {
        return activity as MainNavigation
    }


}