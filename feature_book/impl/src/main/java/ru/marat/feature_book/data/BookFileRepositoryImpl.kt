package ru.marat.feature_book.data

import androidx.compose.ui.util.fastFirstOrNull
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.await
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.takeWhile
import kotlinx.coroutines.flow.transformWhile
import ru.marat.core_data.FileManager
import ru.marat.feature_book.FileDownloadException
import ru.marat.feature_book.data.network.DownloadBookWorker
import ru.marat.feature_book.model.BookFile
import ru.marat.feature_book.repository.BookFileRepository
import java.io.File
import java.util.UUID

class BookFileRepositoryImpl(
    private val fileManager: FileManager,
    private val workManager: WorkManager
) : BookFileRepository {

    override suspend fun startDownloadFile(
        id: Long
    ): Flow<BookFile> {
        if (hasExistingWork(id)) return awaitWithProgress(id)
        val workRequest = OneTimeWorkRequestBuilder<DownloadBookWorker>()
            .setInputData(DownloadBookWorker.createData(id))
            .addTag("downloadBook")
            .build()
        workManager.enqueueUniqueWork("downloadBook$id", ExistingWorkPolicy.KEEP, workRequest)
        return awaitWithProgressByUuid(workRequest.id)
    }

    override suspend fun cancelDownload(id: Long) {
        workManager.cancelUniqueWork("downloadBook$id").await()
        fileManager.deleteBookFileById(id)
    }

    override suspend fun downloadingInProgress(id: Long): Boolean = hasExistingWork(id)

    override suspend fun getFile(id: Long): File? {
        if (hasExistingWork(id)) return null
        return fileManager.getBookFileById(id)
    }

    private  fun awaitWithProgress(
        id: Long
    ): Flow<BookFile>  {
        val workInfo = workManager.getWorkInfosForUniqueWork("downloadBook$id").get()
            .fastFirstOrNull { it.state == WorkInfo.State.RUNNING || it.state == WorkInfo.State.ENQUEUED }
            ?: throw FileDownloadException("Workers not found")

        return awaitWithProgressByUuid(workInfo.id)
    }

    private fun awaitWithProgressByUuid(
        uuid: UUID
    ): Flow<BookFile> = workManager.getWorkInfoByIdFlow(uuid).map {
        when (it?.state) {
            WorkInfo.State.ENQUEUED -> BookFile.Loading(0f)

            WorkInfo.State.RUNNING ->
                BookFile.Loading(it.progress.getFloat(DownloadBookWorker.PROGRESS_KEY, 0f))

            WorkInfo.State.SUCCEEDED -> BookFile.Loaded
            else -> BookFile.Error(null)
        }
    }


    private fun hasExistingWork(id: Long): Boolean {
        val workInfos = workManager.getWorkInfosForUniqueWork("downloadBook$id").get() ?: return false
        return workInfos.any { it.state == WorkInfo.State.RUNNING || it.state == WorkInfo.State.ENQUEUED }
    }
}