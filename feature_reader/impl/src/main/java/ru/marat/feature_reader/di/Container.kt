package ru.marat.feature_reader.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.marat.core_di.InjectUtils
import ru.marat.feature_reader.ReaderViewModel
import javax.inject.Inject

class ReaderContainer {

    @Inject
    lateinit var factory: ReaderViewModel.Provider.Factory

}

@Composable
fun readerViewModel(id: Long): ReaderViewModel {
    val container = remember {
        ReaderContainer().apply {
            DaggerReaderComponent
                .factory()
                .create(InjectUtils.appDependencies<ReaderDependencies>())
                .inject(this)
        }
    }

    return viewModel(factory = container.factory.create(id))
}