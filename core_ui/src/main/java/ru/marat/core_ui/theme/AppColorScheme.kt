package ru.marat.core_ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import ru.marat.library.ui.theme.LocalColorScheme
import ru.marat.library.ui.theme.LocalTheme
import ru.marat.library.ui.theme.isDark

@Immutable
data class AppColorScheme(
    val bookItemBackground: Color,
    val bookItemTitle: Color,
    val bookItemDescription: Color,
    val topAppBarActions: Color,
    val ripple: Color,

    val avatarBackground: Color,
    val avatarIcon: Color,
    val profileText: Color,

    val themeDisabled: Color,
    val themeButtonsBackground: Color,

    val settingsCategory: Color,

    val ratingLine: Color,
    val ratingLineBackground: Color,

    val buttonBackBackground: Color,
)

val appLightColorScheme = AppColorScheme(
    bookItemBackground = Color(0xFFFFFFFF),
    bookItemTitle = Color(0xFF000000),
    bookItemDescription = Color(0xFF343434),
    topAppBarActions = Color(0xFF000000),
    avatarBackground = Color(0xffe8e8e8),
    avatarIcon = Color(0xFF000000),
    profileText = Color(0xFF000000),
    ripple = Color(0xFF000000),
    themeDisabled = Color(0xFF999999),
    themeButtonsBackground = Color(0xFFF1F0F0),
    settingsCategory = Color(0xFF686868),
    ratingLineBackground = Color.Gray.copy(0.5f),
    ratingLine = Color.Gray,
    buttonBackBackground = Color(0xfff3edf7)
)

val appDarkColorScheme = AppColorScheme(
    bookItemBackground = Color(0xFF212121),
    bookItemTitle = Color(0xFFFFFFFF),
    bookItemDescription = Color(0xFF999999),
    topAppBarActions = Color(0xFFFFFFFF),
    avatarBackground = Color(0xFF303030),
    avatarIcon = Color(0xFFFFFFFF),
    profileText = Color(0xFFFFFFFF),
    ripple = Color(0xFFFFFFFF),
    themeDisabled = Color(0xFF999999),
    themeButtonsBackground = Color(0xFF1A1D26),
    settingsCategory = Color(0x99EBEBF5),
    ratingLineBackground = Color.Gray.copy(0.5f),
    ratingLine = Color.White,
    buttonBackBackground = Color(0xFF212026)
)

object AppTheme {
    val colorScheme: AppColorScheme
        @Composable @ReadOnlyComposable get() = LocalColorScheme.current
    val isDark: Boolean
        @Composable @ReadOnlyComposable get() = LocalTheme.current.isDark(isSystemInDarkTheme())
}
