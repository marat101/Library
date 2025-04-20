package ru.marat.core_data

import android.content.Context
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File

class FileManagerImpl(
    context: Context
) : FileManager {

    private val directory = File(context.filesDir.absolutePath + "/books")

    init {
        directory.mkdirs()
    }

    override fun getBookFileById(id: Long): File? {
        val bookDir = File(directory, "$id")
        if (!bookDir.exists()) return null
        val bookFile = File(bookDir, "book.pdf")
        File(bookDir, "metadata.json").apply {
            val value = Json.decodeFromString<FileMetadata>(readText())
            if (value.fileSize > bookFile.length()) {
                bookDir.deleteRecursively()
                return null
            }
        }
        return bookFile
    }

    override fun createNewBookFile(bookId: Long, size: Long): File {
        val bookDir = File(directory, "$bookId")
        if (!bookDir.exists()) bookDir.mkdirs()
        File(bookDir, "metadata.json").apply {
            if (!exists()) createNewFile()
            writeText(
                Json.encodeToString<FileMetadata>(
                    FileMetadata(
                        fileSize = size,
                        createdTimeMillis = System.currentTimeMillis()
                    )
                )
            )
        }
        val bookFile = File(bookDir, "book.pdf")
        if (!bookFile.exists()) bookFile.createNewFile()
        return bookFile
    }

    override fun deleteBookFileById(id: Long) {
        File(directory, "$id").deleteRecursively()
    }

    override fun deleteAll() {
        directory.deleteRecursively()
    }

    @Serializable
    data class FileMetadata(
        val fileSize: Long,
        val createdTimeMillis: Long
    )
}