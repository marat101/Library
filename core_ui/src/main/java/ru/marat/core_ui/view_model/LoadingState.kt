package ru.marat.core_ui.view_model

import ru.marat.core_ui.view_model.LoadingState.Success

sealed interface LoadingState<out T> {

    data class Success<out T>(val data: T) : LoadingState<T>
    data object Loading : LoadingState<Nothing>
    data class Error(val error: Throwable) : LoadingState<Nothing>

    val isLoading
        get() = this is Loading
    val isSuccess
        get() = this is Success
    fun dataOrNull(): T? {
        return (this as? Success)?.data
    }
}