package ru.marat.library.di

import dagger.Module
import dagger.Provides
import ru.marat.feature_root.NavigationApiProvider
import ru.marat.library.NavigationApiProviderImpl
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideFeatures(): NavigationApiProvider = NavigationApiProviderImpl()
}