package ru.marat.feature_book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.marat.core_ui.view_model.BaseViewModel
import ru.marat.core_ui.view_model.LoadingState
import ru.marat.feature_book.model.BookFile
import ru.marat.feature_book.use_cases.CancelDownloadBookUseCase
import ru.marat.feature_book.use_cases.CheckIfDownloadingInProgressUseCase
import ru.marat.feature_book.use_cases.CheckIfFileExistsUseCase
import ru.marat.feature_book.use_cases.DownloadBookUseCase
import ru.marat.feature_book.use_cases.FetchBookInfoUseCase
import ru.marat.feature_reader.ReaderScreen
import ru.marat.navigation_api.AppNavController

class BookDetailsViewModel @AssistedInject constructor(
    private val navigation: AppNavController,
    private val fetchBookInfo: FetchBookInfoUseCase,
    private val downloadBook: DownloadBookUseCase,
    private val checkIfFileExists: CheckIfFileExistsUseCase,
    private val cancelDownloadBook: CancelDownloadBookUseCase,
    private val isDownloadingInProgress: CheckIfDownloadingInProgressUseCase,
    @Assisted private val bookId: Long
) : BaseViewModel() {

    private val _state = MutableStateFlow(BookDetailState())
    val state = _state.asStateFlow()

    fun loadBookInfo() {
        viewModelScope.launch(Dispatchers.IO) {
            val book = async {
                if (checkIfFileExists(bookId) != null) BookFile.Loaded
                else BookFile.NotLoaded
            }
            val bookInfo = async { fetchBookInfo(bookId) }
            withContext(Dispatchers.Main) {
                _state.update {
                    it.copy(
                        book = book.await(),
                        bookInfo = LoadingState.Success(bookInfo.await())
                    )
                }
            }
            if (isDownloadingInProgress(bookId)) subscribeOnDownloadProgress()
        }
    }

    fun onDownloadBook() {
        viewModelScope.launch(Dispatchers.IO) {
            subscribeOnDownloadProgress()
        }
    }

    fun onCancelDownload() {
        viewModelScope.launch(Dispatchers.IO) {
            cancelDownloadBook(bookId)
            withContext(Dispatchers.Main) {
                _state.update { it.copy(book = BookFile.NotLoaded) }
            }
        }
    }

    fun onOpenBook() {
        navigation.navigate(ReaderScreen(bookId)) { launchSingleTop = true }
    }

    private suspend fun subscribeOnDownloadProgress() = coroutineScope {
        if (state.value.book is BookFile.Loading) return@coroutineScope
        appSuspendRunCatching {
            downloadBook(bookId).collectLatest { progress ->
                withContext(Dispatchers.Main) {
                    _state.update { it.copy(book = progress) }
                }
            }
        }.onFailure {
            _state.update { it.copy(book = BookFile.NotLoaded) }
            println("DOWNLOADING FINISHED FAIL")
            //todo
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(id: Long): BookDetailsViewModel
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun create(
            id: Long,
            factory: Factory,
        ) = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(BookDetailsViewModel::class.java))
                    return factory.create(id) as T
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}

