package com.projects.rootmu.projectpinenut.ui.util.general

import android.animation.ObjectAnimator
import android.view.View

fun View.createRotationAnimation(toRotation: Float): ObjectAnimator {
    return ObjectAnimator.ofFloat(
        this,
        View.ROTATION,
        this.rotation,
        toRotation
    )
}

internal inline fun <T : View?> T.runAfterDelay(delay: Long, crossinline f: T.() -> Unit) {
    this?.postDelayed({
        try {
            f()
        } catch (e: Exception) {
        }
    }, delay)
}