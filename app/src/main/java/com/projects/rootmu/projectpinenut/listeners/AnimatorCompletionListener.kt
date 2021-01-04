package com.projects.rootmu.projectpinenut.listeners

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.projects.rootmu.projectpinenut.BuildConfig

private class AnimatorCompletionListener(
    private val animator: Animator,
    private val owner: LifecycleOwner? = null,
    private val completion: ((Boolean) -> Unit)? = null
): AnimatorListenerAdapter(), LifecycleObserver {

    // This is needed on Samsung devices as removing the listener doesn't work
    private var hasFired = false

    private val isResumed
        get() = owner == null || owner.lifecycle.currentState.isAtLeast(Lifecycle.State.RESUMED)

    private var state = State.ACTIVE
        set(value) {
            field = value

            callCompletionIfNeeded()
        }

    init {
        owner?.lifecycle?.addObserver(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    private fun callCompletionIfNeeded() {
        if ((state == State.CANCELLED || state == State.FINISHED) && isResumed) {

            if (!hasFired) {
                completion?.invoke(state == State.FINISHED)
                hasFired = true
            }

            animator.removeListener(this)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        animator.cancel()
    }

    override fun onAnimationCancel(animation: Animator?) {
        super.onAnimationCancel(animation)

        state = State.CANCELLED

    }

    override fun onAnimationEnd(animation: Animator?) {
        super.onAnimationEnd(animation)

        state = State.FINISHED
    }
}

private enum class State {
    ACTIVE,
    CANCELLED,
    FINISHED
}

fun Animator.addCompletionListener(
    owner: LifecycleOwner? = null,
    completion: ((Boolean) -> Unit)? = null
): Animator {
    if (owner == null && BuildConfig.DEBUG) {
        Log.w("AnimatorCompListener", "No lifecycle owner supplied, are you sure about this?")
    }

    addListener(AnimatorCompletionListener(this, owner, completion))
    return this
}