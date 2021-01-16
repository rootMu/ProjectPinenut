package com.projects.rootmu.projectpinenut.di

import com.projects.rootmu.projectpinenut.useCase.dialog.DialogUseCase
import com.projects.rootmu.projectpinenut.useCase.dialog.DialogUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

/**
 * Application Specific Dependencies
 */
@Module
@InstallIn(ApplicationComponent::class)
abstract class UseCaseModule {

    @Binds
    @Singleton
    abstract fun bindDialogUseCase(dialogUseCaseImpl: DialogUseCaseImpl): DialogUseCase?

}