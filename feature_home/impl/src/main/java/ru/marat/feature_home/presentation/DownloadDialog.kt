package ru.marat.feature_home.presentation

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.prepareGet
import io.ktor.client.statement.bodyAsChannel
import io.ktor.http.contentLength
import io.ktor.utils.io.core.isEmpty
import io.ktor.utils.io.core.readBytes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream

@Composable
fun DownloadingDialog(
    //todo пока нет экрана с отзывами
    visible: Boolean,
    id: Long,
    onOpenBook: (String) -> Unit,
    onClose: () -> Unit,
) {
    if (visible) {
        val context = LocalContext.current
        val downloader = remember { FileDownloader() }
        Dialog(
            properties = DialogProperties(
                dismissOnClickOutside = false
            ),
            onDismissRequest = {
                downloader.cancel()
                onClose()
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val isError = remember { mutableStateOf(false) }
                val progress = remember { mutableStateOf<Float?>(null) }
                if (progress.value == null)
                    Text(
                        "Экрана с отзывами ещё нет :("
                    )
                else
                    CircularProgressIndicator(
                        progress = {
                            progress.value ?: 0f
                        }
                    )
                if (isError.value) {
                    Text(
                        text = "Ошибка",
                        color = Color.Red
                    )
                }
                Row {
                    Button(
                        onClick = {
                            downloader.downloadFile(
                                id,
                                url = "http://193.188.20.158:8989/book?id=$id",
                                context = context,
                                onProgress = {
                                    progress.value = it
                                },
                                onError = {
                                    isError.value = true
                                },
                                onSuccess = {
                                    onOpenBook(it.toString())
                                }
                            )
                        }
                    ) {
                        Text("Скачать книгу")
                    }
                    Button(
                        onClick = {
                            downloader.cancel()
                            onClose()
                        }
                    ) {
                        Text("Отмена")
                    }
                }
            }
        }
    }
}

class FileDownloader {
    companion object {
        private val client by lazy {
            HttpClient(OkHttp) {
                Logging {
                    level = LogLevel.ALL
                    logger = object : Logger {
                        override fun log(message: String) {
                            println("Ktor: $message")
                        }
                    }
                }
            }
        }
    }

    private val scope = CoroutineScope(Dispatchers.IO)

    fun downloadFile(
        id: Long,
        url: String,
        context: Context,
        onProgress: (Float) -> Unit,
        onSuccess: (Uri) -> Unit,
        onError: () -> Unit
    ) {
        scope.launch(Dispatchers.IO) {
            runCatching {
                val fileName = "$id$id$id.pdf"
                val downloads = context.cacheDir
                val file = File("$downloads/$fileName")
                if (file.exists()) file.delete()
                file.createNewFile()
                client.prepareGet(url).execute { response ->
                    val channel = response.bodyAsChannel()
                    val size = response.contentLength() ?: 0L
                    var dBytes = 0L
                    while (!channel.isClosedForRead) {
                        val packet = channel.readRemaining(limit = 4_096)
                        while (!packet.isEmpty) {
                            val bytes = packet.readBytes()
                            file.appendBytes(bytes)
                            dBytes += bytes.size
                            withContext(Dispatchers.Main.immediate) {
                                onProgress(
                                    (dBytes.toDouble() / size.toDouble()).toFloat().coerceIn(0f, 1f)
                                )
                            }
                        }
                    }
                }
                withContext(Dispatchers.Main.immediate) { onSuccess(file.toUri()) }
            }.onFailure {
                withContext(Dispatchers.Main.immediate) { onError() }
            }
        }
    }

    fun cancel() {
        scope.cancel()
    }

    private fun copyFileToDownloads(context: Context, downloadedFile: File): Uri? {
        val resolver = context.contentResolver
        val downloadDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path + "/PdfReader"
        File(downloadDir).let { directory ->
            if (!directory.exists() || !directory.isDirectory) directory.mkdir()
        }
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, downloadedFile.name)
                put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
                put(MediaStore.MediaColumns.SIZE, downloadedFile.length())
                put(MediaStore.MediaColumns.RELATIVE_PATH, "Download/PdfReader")
            }
            resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
        } else {
            val authority = "${context.packageName}.provider"
            val destinyFile = File("$downloadDir/PdfReader", downloadedFile.name)
            FileProvider.getUriForFile(context, authority, destinyFile)
        }?.also { downloadedUri ->
            resolver.openOutputStream(downloadedUri).use { outputStream ->
                val brr = ByteArray(1024)
                var len: Int
                val bufferedInputStream =
                    BufferedInputStream(FileInputStream(downloadedFile.absoluteFile))
                while ((bufferedInputStream.read(brr, 0, brr.size).also { len = it }) != -1) {
                    outputStream?.write(brr, 0, len)
                }
                outputStream?.flush()
                bufferedInputStream.close()
            }
        }
    }
}