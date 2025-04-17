package ru.marat.auth.di

import ru.marat.auth.domain.repository.TokenRepository
import ru.marat.core_network.AppHttpClient
import ru.marat.navigation_api.AppNavController


interface AuthDependencies {

    val authNavigation: AppNavController

    val tokenRepository: TokenRepository

    val httpClient: AppHttpClient
}