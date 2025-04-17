package ru.marat.feature_settings.di

import dagger.Subcomponent
import ru.marat.feature_settings.di.contatiners.SettingsContainer

@Subcomponent(
    modules = [
        SettingsUseCasesModule::class,
        SettingsViewModelModule::class
    ]
)
interface SettingsComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): SettingsComponent
    }

    fun inject(target: SettingsContainer)
}