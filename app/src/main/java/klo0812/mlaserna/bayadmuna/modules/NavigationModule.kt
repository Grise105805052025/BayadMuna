package klo0812.mlaserna.bayadmuna.modules

import android.app.Activity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import klo0812.mlaserna.bayadmuna.ui.login.navigation.LoginNavigation

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
    fun provideMainNavigation(activity: Activity): LoginNavigation {
        return activity as LoginNavigation
    }

}