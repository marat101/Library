package ru.marat.feature_root.di

import dagger.Subcomponent
import ru.marat.feature_root.MainActivity
import ru.marat.feature_root.ui.RootViewModel
import javax.inject.Scope

@Scope
@Retention(AnnotationRetention.RUNTIME)
annotation class RootScope

@Subcomponent(
    modules = [
        RootViewModelModule::class
    ]
)
interface RootComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): RootComponent
    }

    fun inject(target: MainActivity)

    val rootViewModelFactory: RootViewModel.Factory
}