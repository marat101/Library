package ru.marat.feature_book.di.modules

import dagger.Module
import dagger.Provides
import ru.marat.feature_book.di.BookScope
import ru.marat.feature_book.repository.BookFileRepository
import ru.marat.feature_book.repository.BookInfoRepository
import ru.marat.feature_book.use_cases.CancelDownloadBookUseCase
import ru.marat.feature_book.use_cases.CheckIfDownloadingInProgressUseCase
import ru.marat.feature_book.use_cases.CheckIfFileExistsUseCase
import ru.marat.feature_book.use_cases.DownloadBookUseCase
import ru.marat.feature_book.use_cases.FetchBookInfoUseCase

@Module
class UseCasesModule {

    @Provides
    fun provideBookInfoUseCase(
        repository: BookInfoRepository
    ) = FetchBookInfoUseCase(repository)

    @Provides
    fun provideDownloadBookUseCase(
        repository: BookFileRepository
    ) = DownloadBookUseCase(repository)

    @Provides
    fun provideCancelDownloadBookUseCase(
        repository: BookFileRepository
    ) = CancelDownloadBookUseCase(repository)

    @Provides
    fun provideCheckIfFileExistsUseCase(
        repository: BookFileRepository
    ) = CheckIfFileExistsUseCase(repository)

    @Provides
    fun provideIsDownloadingInProgressUseCase(
        repository: BookFileRepository
    ) = CheckIfDownloadingInProgressUseCase(repository)
}