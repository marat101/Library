package ru.marat.core_data

import android.annotation.SuppressLint
import android.content.Context

@SuppressLint("ApplySharedPref", "UseKtx")
class AppPreferencesImpl(
    context: Context
): AppPreferences {


    private val sharedPrefs = context.getSharedPreferences("library_preferences", Context.MODE_PRIVATE)

    override fun get(key: String): String? {
        val result = sharedPrefs.getString(key, null)
        if (result == "null") return null
        return result
    }

    override operator fun set(key: String, value: Any?) {
        sharedPrefs.edit().putString(key, value?.toString()).commit()
    }
}