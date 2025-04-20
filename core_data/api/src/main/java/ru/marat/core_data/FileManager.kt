package ru.marat.core_data

import java.io.File

interface FileManager {

    fun getBookFileById(id: Long): File?

    fun createNewBookFile(bookId: Long,size: Long): File

    fun deleteBookFileById(id: Long)

    fun deleteAll()
}