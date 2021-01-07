package com.projects.rootmu.projectpinenut.util.general

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

class TargetedObserver<T>(
    private val dataGetter: () -> LiveData<out T>,
    val activationTime: ActivationTime = ActivationTime.RESUME,
    private val once: Boolean = false,
    private val observer: Observer<in T>
) {

    enum class ActivationTime {
        VIEW_CREATED, RESUME
    }

    constructor(dataGetter: () -> LiveData<out T>, activationTime: ActivationTime = ActivationTime.RESUME, once: Boolean = false, function: (T) -> Unit): this(
        dataGetter,
        activationTime,
        once,
        Observer(function)
    )

    fun reObserve(lifecycleOwner: LifecycleOwner) {
        dataGetter().reObserve(lifecycleOwner, observer, once)
    }
}