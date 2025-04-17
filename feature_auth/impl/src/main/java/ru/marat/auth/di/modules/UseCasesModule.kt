package ru.marat.auth.di.modules

import dagger.Binds
import dagger.Module
import ru.marat.auth.di.AuthScope
import ru.marat.auth.domain.use_cases.AuthUseCase
import ru.marat.auth.domain.use_cases.AuthUseCaseImpl
import ru.marat.auth.domain.use_cases.ConfirmEmailUseCase
import ru.marat.auth.domain.use_cases.ConfirmEmailUseCaseImpl

@Module
interface UseCasesModule {
    @Binds
    @AuthScope
    fun provideAuthUseCase(authRepository: AuthUseCaseImpl): AuthUseCase

    @Binds
    @AuthScope
    fun provideConfirmAuthUseCase(authRepository: ConfirmEmailUseCaseImpl): ConfirmEmailUseCase
}