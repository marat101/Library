package ru.marat.core_ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import ru.marat.library.ui.theme.LocalColorScheme

@Immutable
data class AppColorScheme(
    val bookItemBackground: Color,
    val bookItemTitle: Color,
    val bookItemDescription: Color
)

val appLightColorScheme = AppColorScheme(
    bookItemBackground = Color(0xFFefefef),
    bookItemTitle = Color(0xFF000000),
    bookItemDescription = Color(0xFF343434)
)

val appDarkColorScheme = AppColorScheme(
    bookItemBackground = Color(0xFF212121),
    bookItemTitle = Color(0xFFFFFFFF),
    bookItemDescription = Color(0xFF999999)
)

object AppTheme {
    val colorScheme: AppColorScheme
        @Composable @ReadOnlyComposable get() = LocalColorScheme.current
}
