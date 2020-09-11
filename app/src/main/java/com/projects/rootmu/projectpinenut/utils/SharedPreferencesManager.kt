package com.projects.rootmu.projectpinenut.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Singleton

@Singleton
class SharedPreferencesManager(context: Context) {
    companion object {
        const val PREFS_FILENAME = "project_pinenut.config"
        const val LAST_APP_VERSION = "last_app_version"
        const val EMAIL = "email"
        const val DISPLAY_NAME = "display_name"
        const val LAUNCH_TUTORIAL = "launch_tutorial"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    fun email(): String {
        return email ?: ""
    }

    fun displayName(): String {
        return displayName ?: ""
    }

    fun launchTutorial(): Boolean {
        return launchTutorial
    }

    var email: String?
        get() = prefs.getString(EMAIL, "")
        set(value) = prefs.edit().putString(EMAIL, value).apply()

    var displayName: String?
        get() = prefs.getString(DISPLAY_NAME, "")
        set(value) = prefs.edit().putString(DISPLAY_NAME, value).apply()

    var lastAppVersion: Int
        get() = prefs.getInt(LAST_APP_VERSION, -1)
        set(value) = prefs.edit().putInt(LAST_APP_VERSION, value).apply()

    var launchTutorial: Boolean
        get() = prefs.getBoolean(LAUNCH_TUTORIAL, false)
        set(value) = prefs.edit().putBoolean(LAUNCH_TUTORIAL, value).apply()

}