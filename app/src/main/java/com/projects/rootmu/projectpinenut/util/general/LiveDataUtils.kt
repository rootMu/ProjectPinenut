package com.projects.rootmu.projectpinenut.util.general

import androidx.lifecycle.*

fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<in T>) {
    observe(lifecycleOwner, object : Observer<T> {
        override fun onChanged(t: T?) {
            observer.onChanged(t)
            t?.let{
                removeObserver(this)
            }
        }
    })
}

fun <T> LiveData<T>.reObserve(owner: LifecycleOwner, observer: Observer<in T>, once: Boolean = false) {
    removeObserver(observer)
    if(once)
        observeOnce(owner, observer)
    else{
        observe(owner, observer)
    }
}

fun <T> MutableLiveData<T>.updateValueIfNecessary(newValue: T?) {
    if (value != newValue) {
        value = newValue
    }
}

@Suppress("UNCHECKED_CAST")
fun <T, R> LiveData<T>.mapDistinct(mapFunction: (T) -> R): LiveData<R> {
    return MediatorLiveData<R>().apply {
        addSource(this@mapDistinct) { updateValueIfNecessary(mapFunction(it)) }
        value = mapFunction(this@mapDistinct.value as T)
    }
}
