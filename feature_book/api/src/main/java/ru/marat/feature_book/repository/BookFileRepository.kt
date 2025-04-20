package ru.marat.feature_book.repository

import kotlinx.coroutines.flow.Flow
import ru.marat.feature_book.model.BookFile
import java.io.File

interface BookFileRepository {
    /** @return progress fraction from 0.0(0%) to 1.0(100%) */
    suspend fun startDownloadFile(
        id: Long
    ): Flow<BookFile>
    suspend fun cancelDownload(id: Long)

    suspend fun downloadingInProgress(id: Long): Boolean

    suspend fun getFile(id: Long): File?
}