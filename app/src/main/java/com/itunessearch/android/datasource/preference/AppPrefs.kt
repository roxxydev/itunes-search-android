package com.itunessearch.android.datasource.preference

import android.content.Context
import android.content.SharedPreferences

class AppPrefs(context: Context) {

    companion object {
        private const val PREFS_FILENAME = "app_preferences"
        private const val KEY_IS_FIRST_APP_USE = "first_use"
    }

    private val sharedPrefs: SharedPreferences =
        context.getSharedPreferences(PREFS_FILENAME, Context.MODE_PRIVATE)

    var isFirstUse: Boolean
        get() = sharedPrefs.getBoolean(KEY_IS_FIRST_APP_USE, true)
        set(value) = with (sharedPrefs.edit()) {
            putBoolean(KEY_IS_FIRST_APP_USE, value)
            apply()
        }
}