package com.projects.rootmu.projectpinenut.di

import com.projects.rootmu.projectpinenut.repositories.MessagingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent

/**
 * Repo prodivdes
 */
@InstallIn(ApplicationComponent::class)
@Module
class RepoModule {

    /**
     * Provides the MessagingRepository implementation
     * @return MessagingRepository Singleton implementation
     */
    @Provides
    internal fun providesMessagingRepository() =
        MessagingRepository()
}