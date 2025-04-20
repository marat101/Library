package ru.marat.feature_book


data class FileDownloadException(
    override val message: String? = null
): Exception()