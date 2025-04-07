package ru.marat.feature_settings.di.contatiners

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.marat.core_di.InjectUtils
import ru.marat.feature_settings.di.SettingsDependencies
import ru.marat.feature_settings.presentation.settings.SettingsViewModel
import javax.inject.Inject

@Stable
class SettingsContainer {
    @Inject
    lateinit var settingsViewModelFactory: SettingsViewModel.Factory
}


@SuppressLint("ComposableNaming")
@Composable
fun settingsViewModel(): SettingsViewModel {
    val container = remember {
        SettingsContainer().also {
            InjectUtils.appDependencies<SettingsDependencies>()
                .settingsComponent
                .create()
                .inject(it)
        }
    }
    return viewModel(factory = container.settingsViewModelFactory)
}