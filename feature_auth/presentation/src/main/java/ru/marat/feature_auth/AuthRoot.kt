package ru.marat.feature_auth

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.NavOptionsBuilder
import ru.marat.auth.domain.use_cases.AuthUseCase
import ru.marat.auth.domain.use_cases.ConfirmEmailUseCase
import ru.marat.core_ui.components.common.calculateHorizontalPadding
import ru.marat.library.ui.theme.LibraryTheme
import ru.marat.navigation_api.AppNavController
import ru.marat.navigation_api.Screen

@Composable
fun AuthRoot(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel
) {
    val state = viewModel.state.collectAsState()

    val horizontalPadding = calculateHorizontalPadding()
    val verticalPadding = WindowInsets.statusBars.asPaddingValues()

    val internalScreensModifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .verticalScroll(rememberScrollState())
        .padding(horizontalPadding)
        .padding(
            top = verticalPadding.calculateTopPadding(),
            bottom = verticalPadding.calculateBottomPadding()
        )
    AnimatedContent(
        modifier = modifier,
        targetState = state.value.internalScreen,
        transitionSpec = {
            val animSpec = tween<IntOffset>(durationMillis = 500)
            if (state.value.internalScreen == InternalScreen.Login)
                slideIn(animSpec) { IntOffset(-it.width, 0) }
                    .togetherWith(
                        slideOut(animSpec) {
                            IntOffset(it.width, 0)
                        }
                    )
            else slideIn(animSpec) { IntOffset(it.width, 0) }
                .togetherWith(
                    slideOut(animSpec) {
                        IntOffset(-it.width, 0)
                    }
                )
        }
    ) {
        when (it) {
            InternalScreen.Login -> AuthScreen(internalScreensModifier, viewModel, true)
            InternalScreen.Registration -> AuthScreen(internalScreensModifier, viewModel, false)
        }
    }
}

@Preview
@Composable
fun AuthScreensDarkPreview() {
    LibraryTheme(true) {
        AuthRoot(
            Modifier.fillMaxSize(),
            previewViewModel()
        )
    }
}

@Preview
@Composable
fun AuthScreensLightPreview() {
    LibraryTheme(false) {
        AuthRoot(
            Modifier.fillMaxSize(),
            previewViewModel()
        )
    }
}

private fun previewViewModel() = AuthViewModel(
    object : AppNavController {
        override fun navigate(
            route: Screen,
            options: (NavOptionsBuilder.() -> Unit)?
        ) {
        }

        override fun popBackStack() {}
    },
    object : AuthUseCase {
        override suspend fun invoke(isLogin: Boolean, email: String) {}
    },
    object : ConfirmEmailUseCase {
        override suspend fun invoke(
            isLogin: Boolean,
            email: String,
            code: String
        ) {
        }
    }
)
