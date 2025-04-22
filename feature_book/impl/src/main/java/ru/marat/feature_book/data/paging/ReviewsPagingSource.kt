package ru.marat.feature_book.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.marat.feature_book.model.Review
import ru.marat.feature_book.repository.ReviewsRepository

class ReviewsPagingSource(
    private val bookId: Long,
    private val reviewsRepository: ReviewsRepository
) : PagingSource<Int, Review>() {
    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        return try {
            val offset = params.key ?: 0
            val response = reviewsRepository.fetchReviews(
                bookId = bookId.toString(),
                offset = offset,
                limit = LOAD_SIZE
            )

            LoadResult.Page(
                data = response,
                prevKey = null,
                nextKey = if (response.isEmpty()) null else offset + LOAD_SIZE
            )
        } catch (e: Throwable) {
            e.printStackTrace()
            LoadResult.Error(e)
        }
    }

    companion object {
        const val LOAD_SIZE = 5
        const val PREFETCH_DISTANCE = 2
    }
}