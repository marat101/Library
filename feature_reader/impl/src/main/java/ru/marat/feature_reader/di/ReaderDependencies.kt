package ru.marat.feature_reader.di

import ru.marat.core_data.FileManager

interface ReaderDependencies {

    fun provideFileManager(): FileManager
}