package com.projects.rootmu.projectpinenut.di

import com.projects.rootmu.projectpinenut.repository.account.AccountRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * Repository Dependencies
 */
@InstallIn(ApplicationComponent::class)
@Module
object RepositoryModule {

    /**
     * Provides the SharedPreferencesManager implementation
     * @param context the Application Context
     * @return SharedPreferencesManager Singleton implementation
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun providesAccountRepository() =
        AccountRepository()

}