package ru.marat.feature_profile.di

import dagger.Component
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class ProfileScope

@ProfileScope
@Component(
    dependencies = [ProfileDependencies::class],
    modules = [ProfileViewModelModule::class]
)
interface ProfileComponent {

    @Component.Factory
    interface Factory {
        fun create(
            dependencies: ProfileDependencies,
        ): ProfileComponent
    }
    fun inject(target: ProfileContainer)
}