package ru.marat.library.di

import dagger.Module
import dagger.Provides
import ru.marat.feature_home.di.HomeModule
import ru.marat.feature_home.presentation.HomeViewModel
import ru.marat.feature_root.NavigationApiProvider
import ru.marat.feature_root.di.RootComponent
import ru.marat.feature_root.di.RootModule
import ru.marat.library.NavigationApiProviderImpl
import javax.inject.Singleton

@Module(
    subcomponents = [RootComponent::class],
    includes = [RootModule::class]
)
class AppModule {

    @Provides
    fun provideFeatures(): NavigationApiProvider = NavigationApiProviderImpl()
}