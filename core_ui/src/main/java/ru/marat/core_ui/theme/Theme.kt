package ru.marat.library.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import com.turtleteam.ui.theme_animator.ThemeAnimator
import ru.marat.core_ui.R
import ru.marat.core_ui.theme.DarkColorScheme
import ru.marat.core_ui.theme.LightColorScheme
import ru.marat.core_ui.theme.appDarkColorScheme
import ru.marat.core_ui.theme.appLightColorScheme

val LocalColorScheme = compositionLocalOf { appLightColorScheme }
val LocalTheme = compositionLocalOf { Theme.SYSTEM }

@Composable
fun LibraryTheme(
    currentTheme: Theme = Theme.LIGHT,
//    onThemeChange: (Theme) -> Unit = {},
    content: @Composable () -> Unit
) {
    val darkTheme = when (currentTheme) {
        Theme.SYSTEM -> isSystemInDarkTheme()
        Theme.DARK -> true
        Theme.LIGHT -> false
    }
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val appColorScheme = if (darkTheme) appDarkColorScheme else appLightColorScheme
    ThemeAnimator(
        modifier = Modifier.fillMaxSize(),
        onThemeChange = {}
    ) {
        CompositionLocalProvider(
            LocalColorScheme provides appColorScheme,
            LocalTheme provides currentTheme
        ) {
            MaterialTheme(
                colorScheme = colorScheme,
                typography = Typography,
                content = content
            )
        }
    }
}

@Composable
fun LibraryTheme(
    darkTheme: Boolean = false,
//    onThemeChange: (Theme) -> Unit = {},
    content: @Composable () -> Unit
) {
    LibraryTheme(
        currentTheme = if (darkTheme) Theme.DARK else Theme.LIGHT,
//        onThemeChange = onThemeChange,
        content = content
    )
}

enum class Theme(
    @DrawableRes
    val icon: Int
) {
    SYSTEM(R.drawable.ic_theme_system),
    DARK(R.drawable.ic_theme_dark),
    LIGHT(R.drawable.ic_theme_light)
}

fun Theme.isDark(systemIsDark: Boolean): Boolean = when(this) {
    Theme.SYSTEM -> systemIsDark
    Theme.DARK -> true
    Theme.LIGHT -> false
}