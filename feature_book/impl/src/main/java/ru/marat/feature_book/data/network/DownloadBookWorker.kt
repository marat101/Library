package ru.marat.feature_book.data.network

import android.content.Context
import android.content.pm.ServiceInfo
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import kotlinx.coroutines.withContext
import ru.marat.core_data.FileManager
import ru.marat.core_di.InjectUtils
import ru.marat.feature_book.api.R
import ru.marat.feature_book.di.BookDependencies
import java.io.File
import kotlin.math.floor
import ru.marat.core_data.api.R as CoreDataRes
import ru.marat.core_ui.R as CoreRes

class DownloadBookWorker(
    context: Context,
    private val params: WorkerParameters,
) : CoroutineWorker(context, params) {

    private val api: BookApi = BookApi(
        InjectUtils
            .appDependencies<BookDependencies>()
            .provideAuthHttpClient()
    )
    private val fileManager: FileManager = InjectUtils
        .appDependencies<BookDependencies>()
        .provideFileManager()

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val bookId = extractData()
        var file: File? = null
        semaphore.withPermit {
            if (bookId == -1L) return@withContext Result.failure(
                errorData(
                    id = bookId,
                    message = "Invalid params"
                )
            )
            runCatching {
                api.downloadBook(
                    id = bookId,
                    onCreateFile = { size ->
                        file = fileManager.createNewBookFile(bookId, size)
                        file
                    },
                    onProgress = { progress ->
                        setForeground(createForegroundInfo(floor(progress * 100).toInt()))
                        setProgress(workDataOf(PROGRESS_KEY to progress))
                    }
                )
            }.onFailure {
                it.printStackTrace()
                fileManager.deleteBookFileById(bookId)
                return@withContext Result.failure(
                    errorData(
                        id = bookId,
                        message = it.message ?: "Unknown error"
                    )
                )
            }
        }
        return@withContext Result.success(workDataOf(OUTPUT_FILE_KEY to file?.absolutePath))
    }


    private fun createForegroundInfo(progress: Int): ForegroundInfo {
        val id =
            applicationContext.getString(CoreDataRes.string.notification_channel_id_download_files)
        val title = applicationContext.getString(R.string.download_notification_title)

        val notification = NotificationCompat.Builder(applicationContext, id)
            .setContentTitle(title)
            .setProgress(100, progress, false)
            .setSmallIcon(CoreRes.drawable.layer_logo)
            .setOngoing(true)
            .build()

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q)
            ForegroundInfo(
                DOWNLOAD_NOTIFICATION_ID,
                notification,
                ServiceInfo.FOREGROUND_SERVICE_TYPE_DATA_SYNC
            )
        else
            ForegroundInfo(DOWNLOAD_NOTIFICATION_ID, notification)
    }

    fun extractData(): Long {
        return params.inputData.getLong(ID_KEY, -1)
    }

    fun errorData(
        id: Long,
        message: String
    ): Data {
        return workDataOf(
            ID_KEY to id,
            ERROR_KEY to message
        )
    }

    companion object {
        private val semaphore = Semaphore(1) // Limit concurrent downloads

        const val DOWNLOAD_NOTIFICATION_ID = 1

        const val ID_KEY = "id"
        const val ERROR_KEY = "error"
        const val PROGRESS_KEY = "progress"

        const val OUTPUT_FILE_KEY = "filePath"

        fun createData(
            id: Long
        ) = workDataOf(
            ID_KEY to id
        )
    }
}

