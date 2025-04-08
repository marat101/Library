package ru.marat.feature_profile.di

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.marat.core_di.InjectUtils
import ru.marat.feature_profile.presentation.ProfileViewModel
import javax.inject.Inject


class ProfileContainer {
    @Inject
    lateinit var profileViewModelFactory: ProfileViewModel.Factory
}

@Composable
fun profileViewModel(): ProfileViewModel {
    val container = remember {
        ProfileContainer().apply {
            DaggerProfileComponent
                .factory()
                .create(InjectUtils.appDependencies<ProfileDependencies>())
                .inject(this)
        }
    }

    return viewModel(factory = container.profileViewModelFactory)
}