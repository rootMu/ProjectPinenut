package com.projects.rootmu.projectpinenut.utils

import android.app.Activity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils


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