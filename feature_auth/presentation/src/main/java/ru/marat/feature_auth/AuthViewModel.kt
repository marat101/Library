package ru.marat.feature_auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.marat.auth.AuthScreen
import ru.marat.auth.domain.use_cases.AuthUseCase
import ru.marat.auth.domain.use_cases.ConfirmEmailUseCase
import ru.marat.core_ui.view_model.BaseViewModel
import ru.marat.feature_home.HomeScreen
import ru.marat.feature_profile.ProfileScreen
import ru.marat.navigation_api.AppNavController

class AuthViewModel(
    private val navigation: AppNavController,
    private val authUseCase: AuthUseCase,
    private val confirmEmailUseCase: ConfirmEmailUseCase
) : BaseViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state = _state.asStateFlow()

    fun onNavigateToRegistrationClick() {
        if (!_state.value.loginState.showEmailLoading)
            _state.update {
                it.copy(
                    internalScreen = InternalScreen.Registration,
                    registrationState = it.registrationState.copy(
                        email = if (it.registrationState.email.isBlank()) it.loginState.email
                        else it.registrationState.email
                    ),
                )
            }
    }

    fun onSendCodeClick(isLogin: Boolean) {
        if (_state.value.loginState.showEmailLoading ||
            _state.value.registrationState.showEmailLoading
        ) return

        _state.update {
            if (isLogin) it.copy(loginState = it.loginState.copy(showEmailLoading = true))
            else it.copy(registrationState = it.registrationState.copy(showEmailLoading = true))
        }
        viewModelScope.launch {
            appSuspendRunCatching {
                withContext(Dispatchers.IO) {
                    authUseCase.invoke(
                        email = state.value.let {
                            if (isLogin) it.loginState.email
                            else it.registrationState.email
                        },
                        isLogin = isLogin
                    )
                }
                _state.update {
                    if (isLogin)
                        it.copy(
                            loginState = it.loginState.copy(
                                showCodeField = true,
                                showEmailLoading = false
                            )
                        )
                    else
                        it.copy(
                            registrationState = it.registrationState.copy(
                                showCodeField = true,
                                showEmailLoading = false
                            )
                        )
                }
            }.onFailure { e ->
                _state.update {
                    it.copy(
                        loginState = it.loginState.copy(
                            showEmailLoading = false,
                            emailError = e.message
                        ),
                        registrationState = it.registrationState.copy(
                            showEmailLoading = false,
                            emailError = e.message
                        )
                    )
                }
            }
        }
    }

    fun onLoginConfirmClick() {
        if (_state.value.loginState.showConfirmLoading) return
        _state.update {
            it.copy(loginState = it.loginState.copy(showConfirmLoading = true))
        }
        viewModelScope.launch {
            appSuspendRunCatching {
                withContext(Dispatchers.IO) {
                    confirmEmailUseCase.invoke(
                        email = state.value.loginState.email,
                        code = state.value.loginState.confirmationCode,
                        isLogin = true
                    )
                }
                navigateToHome()
            }.onFailure { e ->
                _state.update {
                    it.copy(
                        loginState = it.loginState.copy(
                            showConfirmLoading = false,
                            codeError = e.message
                        )
                    )
                }
            }
        }
    }

    fun onRegistrationConfirmClick() {
        if (_state.value.registrationState.showConfirmLoading) return
        _state.update {
            it.copy(registrationState = it.registrationState.copy(showConfirmLoading = true))
        }
        viewModelScope.launch {
            appSuspendRunCatching {
                withContext(Dispatchers.IO) {
                    confirmEmailUseCase.invoke(
                        email = state.value.registrationState.email,
                        code = state.value.registrationState.confirmationCode,
                        isLogin = false
                    )
                }
                navigateToHome()
            }.onFailure { e ->
                _state.update {
                    it.copy(
                        registrationState = it.registrationState.copy(
                            showConfirmLoading = false,
                            codeError = e.message
                        )
                    )
                }
            }
        }
    }

    fun onNavigateToLoginClick() {
        if (!_state.value.registrationState.showEmailLoading)
            _state.update {
                it.copy(
                    internalScreen = InternalScreen.Login,
                )
            }
    }

    private fun navigateToHome() {
        navigation.navigate(HomeScreen) {
            popUpTo(AuthScreen) {
                inclusive = true
            }
        }
    }

    private fun navigateToEditProfile() {
        navigation.navigate(ProfileScreen) {
            popUpTo(AuthScreen) {
                inclusive = true
            }
        }
    }

    fun editEmail(newString: String, isLogin: Boolean) {
        _state.update {
            if (isLogin) it.copy(
                loginState = it.loginState.copy(
                    email = newString,
                    emailError = null
                )
            )
            else it.copy(
                registrationState = it.registrationState.copy(
                    email = newString,
                    emailError = null
                )
            )
        }
    }

    fun editCode(newString: String, isLogin: Boolean) {
        _state.update {
            if (isLogin) it.copy(
                loginState = it.loginState.copy(
                    confirmationCode = newString,
                    codeError = null
                )
            )
            else it.copy(
                registrationState = it.registrationState.copy(
                    confirmationCode = newString,
                    codeError = null
                )
            )
        }
    }

    @Suppress("UNCHECKED_CAST")
    class Factory(
        private val appNavigation: AppNavController,
        private val authUseCase: AuthUseCase,
        private val confirmEmailUseCase: ConfirmEmailUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AuthViewModel::class.java))
                return AuthViewModel(appNavigation, authUseCase, confirmEmailUseCase) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}