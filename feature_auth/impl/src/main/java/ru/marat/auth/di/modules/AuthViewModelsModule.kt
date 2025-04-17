package ru.marat.auth.di.modules

import dagger.Module
import dagger.Provides
import ru.marat.auth.di.AuthScope
import ru.marat.auth.domain.use_cases.AuthUseCase
import ru.marat.auth.domain.use_cases.ConfirmEmailUseCase
import ru.marat.feature_auth.AuthViewModel
import ru.marat.navigation_api.AppNavController

@Module
class AuthViewModelsModule {

    @Provides
    @AuthScope
    fun provideLoginViewModel(
        navigation: AppNavController,
        authUseCase: AuthUseCase,
        confirmEmailUseCase: ConfirmEmailUseCase
    ): AuthViewModel.Factory {
        return AuthViewModel.Factory(navigation, authUseCase, confirmEmailUseCase)
    }
}