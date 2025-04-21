package ru.marat.feature_reader.di.modules

import dagger.Module
import dagger.Provides
import ru.marat.core_data.FileManager
import ru.marat.feature_reader.ReaderRepository
import ru.marat.feature_reader.data.ReaderRepositoryImpl
import ru.marat.feature_reader.di.ReaderScope

@Module
class ReaderModule {

    @Provides
    @ReaderScope
    fun provideReaderRepository(fileManager: FileManager): ReaderRepository {
        return ReaderRepositoryImpl(fileManager)
    }

}