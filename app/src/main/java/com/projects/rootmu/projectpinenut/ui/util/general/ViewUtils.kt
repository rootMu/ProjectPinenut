package com.projects.rootmu.projectpinenut.ui.util.general

import android.app.Activity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils

fun View.setHeightIfNecessary(height: Float) {
    setHeightIfNecessary(height.toInt())
}

fun View.setHeightIfNecessary(height: Int) {
    val layoutParams = layoutParams
    if (layoutParams.height != height) {
        layoutParams.height = height
        this.layoutParams = layoutParams
    }
}

fun View.setWidthIfNecessary(width: Float) {
    setWidthIfNecessary(width.toInt())
}

fun View.setWidthIfNecessary(width: Int) {
    val layoutParams = layoutParams
    if (layoutParams.width != width) {
        layoutParams.width = width
        this.layoutParams = layoutParams
    }
}

fun Activity.onAnimationEnd(animId: Int, view: View?, invoke: () -> Unit = {}): Animation = AnimationUtils.loadAnimation(this,animId).apply {
    setAnimationListener(object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation) {
            // TODO Auto-generated method stub
        }

        override fun onAnimationRepeat(animation: Animation) {
            // TODO Auto-generated method stub
        }

        override fun onAnimationEnd(animation: Animation) {
            invoke.invoke()
        }
    })
    view?.startAnimation(this)
}