package com.projects.rootmu.projectpinenut.util.specific

import androidx.fragment.app.Fragment
import com.projects.rootmu.projectpinenut.ui.MainActivity

val Fragment.mainActivity : MainActivity? get() = activity as? MainActivity

fun Fragment.goToAccount() {
    mainActivity?.goToAccount()
}

fun Fragment.goHome() {
    mainActivity?.goToHome()
}

fun Fragment.goToMessages() {
    mainActivity?.goToMessages()
}

fun Fragment.goToNotifications() {
    mainActivity?.goToNotifications()
}


