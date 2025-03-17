package ru.marat.feature_home.di

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import jakarta.inject.Inject
import ru.marat.core_di.InjectUtils
import ru.marat.feature_home.presentation.HomeViewModel

@Stable
class HomeContainer {
    @Inject
    lateinit var appNavigation: HomeViewModel.Factory
}


@SuppressLint("ComposableNaming")
@Composable
fun daggerViewModel(): HomeViewModel {
    val container = remember {
        HomeContainer().also {
            DaggerHomeComponent
                .builder()
                .dependencies(InjectUtils.appDependencies())
                .build()
                .inject(it)
        }
    }
    return viewModel(factory = container.appNavigation)
}