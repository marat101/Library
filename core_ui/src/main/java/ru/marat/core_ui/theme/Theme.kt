package ru.marat.library.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import ru.marat.core_ui.theme.DarkColorScheme
import ru.marat.core_ui.theme.LightColorScheme
import ru.marat.core_ui.theme.appDarkColorScheme
import ru.marat.core_ui.theme.appLightColorScheme

val LocalColorScheme = compositionLocalOf { appLightColorScheme }

@Composable
fun LibraryTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val appColorScheme = if (darkTheme) appDarkColorScheme else appLightColorScheme
    CompositionLocalProvider(
        LocalColorScheme provides appColorScheme
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}