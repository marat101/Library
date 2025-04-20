package ru.marat.feature_home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.marat.core_ui.view_model.BaseViewModel
import ru.marat.core_ui.view_model.LoadingState
import ru.marat.feature_book.BookScreen
import ru.marat.feature_home.network.MainRepository
import ru.marat.feature_profile.SearchScreen
import ru.marat.feature_reader.ReaderScreen
import ru.marat.navigation_api.AppNavController
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

class HomeViewModel(
    private val navigation: AppNavController,
    private val mainRepository: MainRepository
) : BaseViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState>
        get() = _state.asStateFlow()

    fun onSearchClick() {
        navigation.navigate(SearchScreen()) {
            launchSingleTop = true
        }
    }

    fun addToFavorite(id: Long) {
        viewModelScope.launch {
            val added = withContext(Dispatchers.IO) {
                mainRepository.addToFavorite(id)
            }
            _state.update {
                it.copy(
                    books = LoadingState.Success(
                        it.books.dataOrNull()?.map { book ->
                            if (book.id == id) book.copy(isFavorite = added)
                            else book
                        } ?: listOf()
                    )
                )
            }
        }
    }

    fun onFavoriteClick(id: Long) {
        if (_state.value.books.dataOrNull()?.find { it.id == id }!!.isFavorite)
            removeFromFavorite(id)
        else addToFavorite(id)
    }

    fun removeFromFavorite(id: Long) {
        viewModelScope.launch {
            val removed = withContext(Dispatchers.IO) {
                mainRepository.removeFromFavorite(id)
            }
            _state.update {
                it.copy(
                    books = LoadingState.Success(
                        it.books.dataOrNull()?.map { book ->
                            if (book.id == id) book.copy(isFavorite = removed)
                            else book
                        } ?: listOf()
                    )
                )
            }
        }
    }

    fun loadMainScreen() {
        if (!_state.value.books.isSuccess) {
            _state.update {
                it.copy(books = LoadingState.Loading)
            }
            viewModelScope.launch {
                appSuspendRunCatching {
                    val books = withContext(Dispatchers.IO) {
                        mainRepository.getMain()
                    }
                    _state.update {
                        it.copy(
                            books = LoadingState.Success(books)
                        )
                    }
                }.onFailure { e ->
                    _state.update {
                        it.copy(
                            books = LoadingState.Error(e)
                        )
                    }
                }
            }
        }
    }

    fun onBookClick(id: Long) {
        navigation.navigate(BookScreen(id))
    }

    @Suppress("UNCHECKED_CAST")
    class Factory @Inject constructor(
        private val appNavigation: AppNavController,
        private val repository: MainRepository
    ) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java))
                return HomeViewModel(appNavigation, repository) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}