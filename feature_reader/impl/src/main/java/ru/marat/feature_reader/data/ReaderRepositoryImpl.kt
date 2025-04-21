package ru.marat.feature_reader.data

import ru.marat.core_data.FileManager
import ru.marat.feature_reader.ReaderRepository
import java.io.File

class ReaderRepositoryImpl(
    private val fileManager: FileManager
): ReaderRepository {
    override fun getBookFile(id: Long): File {
        return fileManager.getBookFileById(id)!!
    }
}