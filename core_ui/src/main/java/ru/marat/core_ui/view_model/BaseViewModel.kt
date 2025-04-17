package ru.marat.core_ui.view_model

import androidx.lifecycle.ViewModel
import kotlin.coroutines.cancellation.CancellationException

abstract class BaseViewModel: ViewModel() {
    suspend inline fun <T> appSuspendRunCatching(block: suspend () -> T): Result<T> {
        return try {
            Result.success(block())
        } catch (e: Throwable) {
            if (e !is CancellationException) e.printStackTrace() else throw e
            Result.failure(e)
        }
    }

    inline fun <T> appRunCatching(block: () -> T): Result<T> {
        return try {
            Result.success(block())
        } catch (e: Throwable) {
            if (e !is CancellationException) e.printStackTrace() else throw e
            Result.failure(e)
        }
    }
}