package com.projects.rootmu.projectpinenut.di

import android.content.Context
import com.projects.rootmu.projectpinenut.utils.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext


/**
 * Network Specific Dependencies
 */
@InstallIn(ApplicationComponent::class)
@Module
object ApplicationModule {

    /**
     * Provides the InspectionsService implementation
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Inspections Service implementation
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun providesSharedPreferencesManager(@ApplicationContext context: Context) =
        SharedPreferencesManager(context)

}