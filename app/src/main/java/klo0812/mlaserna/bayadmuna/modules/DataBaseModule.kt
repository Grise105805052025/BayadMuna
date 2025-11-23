package klo0812.mlaserna.bayadmuna.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import klo0812.mlaserna.bayadmuna.database.AppDataBase
import javax.inject.Singleton

/**
 * Module used to provide database related injections.
 *
 * @author Roy M
 * @version 1.0
 * @since 2025-11-23
 */
@Module
@InstallIn(SingletonComponent::class)
class DataBaseModule {

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase {
        return AppDataBase.getInstance(context)
    }

}