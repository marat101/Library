package ru.marat.feature_settings.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.marat.feature_settings.AppSettings
import ru.marat.library.ui.theme.Theme
import javax.inject.Inject

class AppSettingsImpl @Inject constructor(
    private val context: Context
): AppSettings  {

    companion object {
        private val THEME_KEY = stringPreferencesKey("theme")
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")


    override val themeFlow: Flow<Theme> = context.dataStore.data.map {
        val theme = it[THEME_KEY] ?: Theme.LIGHT.name
        when(theme) {
            "LIGHT" -> Theme.LIGHT
            "DARK" -> Theme.DARK
            else -> Theme.SYSTEM
        }
    }

    override suspend fun setNewTheme(theme: Theme) {
        context.dataStore.edit { settings ->
            settings[THEME_KEY] = theme.name
        }
    }
}
