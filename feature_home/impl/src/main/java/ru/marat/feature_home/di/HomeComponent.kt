package ru.marat.feature_home.di

import dagger.Component
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class HomeScope

@HomeScope
@Component(
    dependencies = [HomeDependencies::class],
    modules = [HomeModule::class]
)
interface HomeComponent {

    @Component.Builder
    interface Builder {
        fun dependencies(deps: HomeDependencies): Builder
        fun build(): HomeComponent
    }

    fun inject(target: HomeContainer)
}