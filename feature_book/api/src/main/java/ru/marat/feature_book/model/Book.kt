package ru.marat.feature_book.model

sealed interface BookFile {
    data object Undefined : BookFile
    data object Loaded : BookFile
    data object NotLoaded : BookFile
    /** progress from 0 to 1 */
    data class Loading(val progress: Float = 0f) : BookFile
    data class Error(val error: Throwable?) : BookFile
}