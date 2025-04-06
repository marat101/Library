package ru.marat.library.ui.theme

import androidx.annotation.DrawableRes
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.turtleteam.ui.theme_animator.ThemeAnimator
import ru.marat.core_ui.R
import ru.marat.core_ui.theme.AppTheme
import ru.marat.core_ui.theme.DarkColorScheme
import ru.marat.core_ui.theme.LightColorScheme
import ru.marat.core_ui.theme.appDarkColorScheme
import ru.marat.core_ui.theme.appLightColorScheme

val LocalColorScheme = compositionLocalOf { appLightColorScheme }
val LocalTheme = compositionLocalOf { Theme.SYSTEM }

@Composable
fun LibraryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    onThemeChange: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val currentTheme = remember { mutableStateOf(Theme.SYSTEM) }
    val darkTheme = when (currentTheme.value) {
        Theme.SYSTEM -> darkTheme
        Theme.DARK -> true
        Theme.LIGHT -> false
    }
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val appColorScheme = if (darkTheme) appDarkColorScheme else appLightColorScheme
    ThemeAnimator(
        modifier = Modifier.fillMaxSize(),
        onThemeChange = { currentTheme.value = it }
    ) {
        CompositionLocalProvider(
            LocalColorScheme provides appColorScheme,
            LocalTheme provides currentTheme.value
        ) {
            MaterialTheme(
                colorScheme = colorScheme,
                typography = Typography,
                content = content
            )
        }
    }
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