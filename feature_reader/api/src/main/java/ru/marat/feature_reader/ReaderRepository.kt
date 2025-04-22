package ru.marat.feature_reader

import java.io.File

interface ReaderRepository {

    suspend fun getBookFile(id: Long): File
}