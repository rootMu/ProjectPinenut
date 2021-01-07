package com.projects.rootmu.projectpinenut.util.general

import com.projects.rootmu.projectpinenut.ui.components.base.BaseFragment

fun <T : BaseFragment> T.delayedRunnable(delay: Long = 1000, runnable: () -> Unit) =
    object : Runnable {
        override fun run() {
            runnable.invoke()
            mainHandler.postDelayed(this, delay)
        }
    }