package com.projects.rootmu.projectpinenut.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Singleton

@Singleton
class SharedPreferencesManager(context: Context) {
    companion object {
        const val PREFS_FILENAME = "project_pinenut.config"
        const val LAST_APP_VERSION = "last_app_version"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var lastAppVersion: Int
        get() = prefs.getInt(LAST_APP_VERSION, -1)
        set(value) = prefs.edit().putInt(LAST_APP_VERSION, value).apply()

}