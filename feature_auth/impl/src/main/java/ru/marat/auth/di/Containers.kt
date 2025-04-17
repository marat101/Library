package ru.marat.auth.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.marat.core_di.InjectUtils
import ru.marat.feature_auth.AuthViewModel
import javax.inject.Inject

@Stable
class AuthContainer() {

    @Inject
    lateinit var viewModel: AuthViewModel.Factory
}

@Composable
fun authViewModel(): AuthViewModel {
    val container = remember {
        AuthContainer().apply {
            DaggerAuthComponent
                .factory()
                .create(InjectUtils.appDependencies<AuthDependencies>())
                .inject(this)
        }
    }
    return viewModel(factory = container.viewModel)
}