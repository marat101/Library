package ru.marat.core_ui.view_model

sealed interface LoadingState {

    data class Success<T>(val data: T) : LoadingState
    data object Loading : LoadingState
    data class Error(val error: Throwable) : LoadingState
}