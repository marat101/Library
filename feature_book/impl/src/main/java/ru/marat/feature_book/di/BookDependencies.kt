package ru.marat.feature_book.di

import androidx.work.WorkManager
import ru.marat.core_data.FileManager
import ru.marat.core_network.AppAuthHttpClient
import ru.marat.feature_book.data.network.BookApi
import ru.marat.navigation_api.AppNavController

interface BookDependencies {
    fun provideFileManager(): FileManager
    fun provideBookNavController(): AppNavController
    fun provideAuthHttpClient(): AppAuthHttpClient
//    fun provideBookApi(): BookApi
    fun provideWorkManager(): WorkManager
}