package com.projects.rootmu.projectpinenut.utils

import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import androidx.core.content.pm.PackageInfoCompat.getLongVersionCode
import com.projects.rootmu.projectpinenut.di.PackageName
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.random.Random

enum class AppStart {
    FIRST_TIME, UPDATE, NORMAL;
}

/**
 * Finds out started for the first time (ever or in the current version)
 *
 * @return the type of app start
 */

@Singleton
class CheckAppStart @Inject constructor(@PackageName var packageInfo: PackageInfo, var sharedPreferencesManager: SharedPreferencesManager) {

    private var appStart: AppStart = AppStart.NORMAL

    private val longVersionCode: Int by lazy {
        getLongVersionCode(packageInfo).toInt()
    }

    fun status(): AppStart {
        try {
            appStart = checkAppStart(longVersionCode, sharedPreferencesManager.lastAppVersion)
            // Update version in preferences
            sharedPreferencesManager.lastAppVersion = longVersionCode

        } catch (e: PackageManager.NameNotFoundException) {
            Timber.w("Unable to determine current app version from package manager. Defensively assuming normal app start.")
        }
        return appStart
    }

    private fun checkAppStart(currentVersionCode: Int, lastVersionCode: Int): AppStart {
        return when {
            lastVersionCode == -1 -> {
                AppStart.FIRST_TIME
            }
            lastVersionCode < currentVersionCode -> {
                AppStart.UPDATE
            }
            lastVersionCode > currentVersionCode -> {
                Timber.w("Current version code ($currentVersionCode) is less then the one recognized on last startup ($lastVersionCode). Defensively assuming normal app start.")
                AppStart.NORMAL
            }
            else -> {
                AppStart.NORMAL
            }
        }
    }

}