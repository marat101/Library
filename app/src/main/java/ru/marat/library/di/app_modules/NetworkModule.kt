package ru.marat.library.di.app_modules

import dagger.Module
import dagger.Provides
import io.ktor.client.HttpClient
import ru.marat.auth.TokenRepositoryImpl
import ru.marat.auth.domain.repository.TokenRepository
import ru.marat.auth.local.TokenStorage
import ru.marat.auth.network.TokenApi
import ru.marat.core_data.AppPreferences
import ru.marat.core_network.AppAuthHttpClient
import ru.marat.core_network.AppAuthHttpClientImpl
import ru.marat.core_network.AppHttpClient
import ru.marat.core_network.AppHttpClientImpl
import ru.marat.core_network.HttpClientFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideTokenManager(
        appStorage: AppPreferences,
        client: AppHttpClient
    ): TokenRepository {
        return TokenRepositoryImpl(
            TokenApi(client),
            TokenStorage(appStorage)
        )
    }

    @Provides
    @Singleton
    fun provideAppHttpClient(client: HttpClient): AppHttpClient {
        return AppHttpClientImpl(client)
    }

    @Provides
    @Singleton
    fun provideAppAuthHttpClient(client: HttpClient, tokenRepository: TokenRepository): AppAuthHttpClient {
        return AppAuthHttpClientImpl(client, tokenRepository)
    }

    @Provides
    @Singleton
    fun provideKtorHttpClient() = HttpClientFactory.create()
}