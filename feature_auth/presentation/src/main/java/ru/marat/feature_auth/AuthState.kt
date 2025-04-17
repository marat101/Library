package ru.marat.feature_auth

import androidx.compose.runtime.Immutable

@Immutable
data class AuthState(
    val internalScreen: InternalScreen = InternalScreen.Login,

    val loginState: InternalState = InternalState(),
    val registrationState: InternalState = InternalState()
)

@Immutable
data class InternalState(
    val email: String = "",
    val confirmationCode: String = "",
    val showCodeField: Boolean = false,

    val showEmailLoading: Boolean = false,
    val showConfirmLoading: Boolean = false,

    val emailError: String? = null,
    val codeError: String? = null
)
enum class InternalScreen {
    Login, Registration
}