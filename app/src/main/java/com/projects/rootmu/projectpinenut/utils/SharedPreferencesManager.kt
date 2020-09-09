package com.projects.rootmu.projectpinenut.utils

import android.content.Context
import android.content.SharedPreferences
import javax.inject.Singleton

@Singleton
class SharedPreferencesManager(context: Context) {
    companion object {
        const val PREFS_FILENAME = "project_pinenut.config"
        const val EMAIL = "email"
        const val DISPLAY_NAME = "display_name"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    fun email(): String {
        return email ?: ""
    }

    fun displayName(): String {
        return displayName ?: ""
    }

    var email: String?
        get() = prefs.getString(EMAIL, "")
        set(value) = prefs.edit().putString(EMAIL, value).apply()

    var displayName: String?
        get() = prefs.getString(DISPLAY_NAME, "")
        set(value) = prefs.edit().putString(DISPLAY_NAME, value).apply()

}