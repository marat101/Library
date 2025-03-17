package ru.marat.core_ui.view_model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CancellationException

abstract class BaseViewModel: ViewModel() {
    inline fun <reified T> appRunCatching(block: () -> T): Result<T> {
        return kotlin.runCatching(block).apply {
            exceptionOrNull()?.let { exception ->
                if (exception !is CancellationException) exception.printStackTrace()
            }
        }
    }
}