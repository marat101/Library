package ru.marat.auth.di

import dagger.Component
import ru.marat.auth.di.modules.AuthModule
import ru.marat.auth.di.modules.AuthViewModelsModule
import ru.marat.auth.di.modules.UseCasesModule
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class AuthScope

@AuthScope
@Component(
    dependencies = [
        AuthDependencies::class
    ],
    modules = [
        AuthViewModelsModule::class,
        AuthModule::class,
        UseCasesModule::class
    ]
)
interface AuthComponent {

    @Component.Factory
    interface Factory {
        fun create(dependencies: AuthDependencies): AuthComponent
    }

    fun inject(target: AuthContainer)
}