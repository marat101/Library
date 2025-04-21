package ru.marat.feature_home.network

import ru.marat.feature_home.model.BookItem

class MainRepository( //todo
    private val api: MainApi
) {
    suspend fun getMain(): List<BookItem> {
        val response = api.getMain()
        return response.map {
            BookItem(
                name = it.displayedName,
                imageUrl = "$BASE_URL/preview?id=${it.id}&isLowQuality=true",
                id = it.id,
                isFavorite = it.isFavorite,
                price = it.price
            )
        }
    }
    suspend fun addToFavorite(id: Long): Boolean {
        return runCatching {
            api.addToFavorites(id)
            true
        }.getOrElse {
            it.printStackTrace()
            false
        }
    }
    suspend fun removeFromFavorite(id: Long): Boolean {
        return runCatching {
            api.removeFromFavorites(id)
            true
        }.getOrElse {
            it.printStackTrace()
            false
        }
    }
}