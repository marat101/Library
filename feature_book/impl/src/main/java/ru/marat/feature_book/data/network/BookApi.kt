package ru.marat.feature_book.data.network

import io.ktor.client.call.body
import io.ktor.http.HttpMethod
import ru.marat.core_network.AppAuthHttpClient
import ru.marat.feature_book.FileDownloadException
import ru.marat.feature_book.data.dto.BookInfoDto
import ru.marat.feature_book.impl.BuildConfig
import java.io.File

class BookApi(
    private val client: AppAuthHttpClient
) {
    suspend fun fetchBookInfo(id: Long): BookInfoDto {
        val response = client.request(
            url = "${BuildConfig.API_URL}/book?bookId=$id"
        ) {
            method = HttpMethod.Get
        }
        return response.body()
    }

    suspend fun downloadBook(
        id: Long,
        onCreateFile: (totalFileSize: Long) -> File,
        onProgress: suspend (Float) -> Unit
    ) {
        var fileSize: Long = 0
        var file: File? = null
        client.downloadFile(
            url = "${BuildConfig.API_URL}/downloadBook?id=$id",
            onCreateFile = {
                fileSize = it
                val mfile = onCreateFile(it)
                file = mfile
                file
            },
            progress = onProgress
        )
        if (fileSize != file!!.length()) throw FileDownloadException("file id: $id")
    }
}