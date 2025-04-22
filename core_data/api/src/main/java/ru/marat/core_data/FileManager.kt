package ru.marat.core_data

import java.io.File

interface FileManager {

    suspend fun getBookFileById(id: Long): File?

    suspend fun createNewBookFile(bookId: Long,size: Long): File

    suspend fun deleteBookFileById(id: Long)

    suspend fun deleteAll()
}