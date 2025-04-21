package ru.marat.feature_reader

import java.io.File

interface ReaderRepository {

    fun getBookFile(id: Long): File
}