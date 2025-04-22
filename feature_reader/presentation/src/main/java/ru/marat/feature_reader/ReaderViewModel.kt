package ru.marat.feature_reader

import androidx.core.net.toUri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import ru.marat.core_ui.view_model.BaseViewModel

class ReaderViewModel(
    private val repo: ReaderRepository,
    private val id: Long
) : BaseViewModel() {

    private val _state = MutableStateFlow(
        ReaderState(
            fileUri = null
        )
    )
    val state = _state
        .combine(flow { emit(repo.getBookFile(id).toUri()) }.flowOn(Dispatchers.IO)) { state, uri ->
            state.copy(uri)
        }.stateIn(viewModelScope, SharingStarted.Eagerly, _state.value)


    class Provider @AssistedInject constructor(
        private val repository: ReaderRepository,
        @Assisted private val id: Long
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ReaderViewModel::class.java))
                return ReaderViewModel(repository, id) as T
            throw IllegalArgumentException("Unknown ViewModel class")
        }

        @AssistedFactory
        interface Factory {
            fun create(id: Long): Provider
        }
    }
}

