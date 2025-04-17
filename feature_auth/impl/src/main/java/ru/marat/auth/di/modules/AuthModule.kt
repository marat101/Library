package ru.marat.auth.di.modules

import dagger.Module
import dagger.Provides
import ru.marat.auth.api.AuthApi
import ru.marat.auth.api.AuthApiImpl
import ru.marat.auth.di.AuthScope
import ru.marat.auth.domain.repository.AuthRepository
import ru.marat.auth.domain.repository.AuthRepositoryImpl
import ru.marat.auth.domain.repository.TokenRepository
import ru.marat.core_network.AppHttpClient

@Module
class AuthModule {
    @Provides
    @AuthScope
    fun provideAuthApi(client: AppHttpClient): AuthApi {
        return AuthApiImpl(client)
    }

    @Provides
    @AuthScope
    fun provideAuthRepository(
        authApi: AuthApi,
        tokenRepository: TokenRepository
    ): AuthRepository {
        return AuthRepositoryImpl(
            authApi,
            tokenRepository
        )
    }
}