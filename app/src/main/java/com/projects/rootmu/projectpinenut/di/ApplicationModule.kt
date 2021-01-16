package com.projects.rootmu.projectpinenut.di

import android.content.Context
import android.content.pm.PackageManager
import com.projects.rootmu.projectpinenut.ui.util.SharedPreferencesManager
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext


/**
 * Application Specific Dependencies
 */
@InstallIn(ApplicationComponent::class)
@Module
object ApplicationModule {

    /**
     * Provides the SharedPreferencesManager implementation
     * @param context the Application Context
     * @return SharedPreferencesManager Singleton implementation
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun providesSharedPreferencesManager(@ApplicationContext context: Context) =
        SharedPreferencesManager(context)

    /**
     * Provides the PackageManager implementation
     * @param context the Application Context
     * @return PackageManager implementation
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun providesPackageManager(@ApplicationContext context: Context) =
        context.packageManager

    /**
     * Provides the PackageInfo implementation with the package Name
     * @param context the Application Context
     * @param packageManager the PackageManager
     * @return PackageInfo implementation
     */
    @Provides
    @Reusable
    @PackageName
    @JvmStatic
    internal fun providesPackageInfo(
        @ApplicationContext context: Context,
        packageManager: PackageManager
    ) =
        packageManager.getPackageInfo(context.packageName, 0)

}