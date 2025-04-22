package ru.marat.feature_book.di.modules

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import ru.marat.core_data.FileManager
import ru.marat.core_network.AppAuthHttpClient
import ru.marat.feature_book.data.BookFileRepositoryImpl
import ru.marat.feature_book.data.BookInfoRepositoryImpl
import ru.marat.feature_book.data.ReviewsRepositoryImpl
import ru.marat.feature_book.data.network.BookApi
import ru.marat.feature_book.data.network.ReviewsApi
import ru.marat.feature_book.data.paging.ReviewsPagingSource
import ru.marat.feature_book.di.BookScope
import ru.marat.feature_book.repository.BookFileRepository
import ru.marat.feature_book.repository.BookInfoRepository
import ru.marat.feature_book.repository.ReviewsRepository
import javax.inject.Named

@Module
class BookModule {

    @Provides
    @BookScope
    fun provideReviewsApi(
        client: AppAuthHttpClient
    ): ReviewsApi = ReviewsApi(client)

    @Provides
    @BookScope
    fun provideReviewsRepo(
        api: ReviewsApi
    ): ReviewsRepository = ReviewsRepositoryImpl(api)

    @Provides
    @BookScope
    fun provideReviewsPager(
        @Named("bookId") bookId: Long,
        repository: ReviewsRepository
    ) = Pager(PagingConfig(pageSize = ReviewsPagingSource.LOAD_SIZE)) {
        ReviewsPagingSource(bookId, repository)
    }

    @Provides
    @BookScope
    fun provideBookApi(
        client: AppAuthHttpClient
    ) = BookApi(client)


    @Provides
    @BookScope
    fun provideFileRepo(
        fileManager: FileManager,
        workManager: WorkManager
    ): BookFileRepository = BookFileRepositoryImpl(fileManager, workManager)

    @Provides
    @BookScope
    fun provideBookInfoRepo(
        bookApi: BookApi
    ): BookInfoRepository {
        return BookInfoRepositoryImpl(bookApi)
    }
}