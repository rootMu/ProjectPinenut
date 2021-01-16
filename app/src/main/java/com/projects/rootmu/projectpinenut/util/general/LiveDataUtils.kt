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
// This version invokes mapFunction even before the first value of the source livedata is set.
// androidx.lifecycle.Transformations.map doesn't do this and it causes issues
fun <T, R> LiveData<T>.map(mapFunction: (T) -> R): LiveData<R> {
    return MediatorLiveData<R>().apply {
        addSource(this@map) { value = mapFunction.invoke(it) }

        value = mapFunction.invoke(this@map.value as T)
    }
}

@Suppress("UNCHECKED_CAST")
fun <T, R> LiveData<T>.mapDistinct(mapFunction: (T) -> R): LiveData<R> {
    return MediatorLiveData<R>().apply {
        addSource(this@mapDistinct) { updateValueIfNecessary(mapFunction(it)) }
        value = mapFunction(this@mapDistinct.value as T)
    }
}

@Suppress("UNCHECKED_CAST")
fun <T, R, U> Pair<LiveData<out T>, LiveData<out R>>.map2(mapFunction: (T, R) -> U): LiveData<U> {
    return MediatorLiveData<U>().apply {
        addSource(first) { updateValueIfNecessary(mapFunction(it, second.value as R)) }
        addSource(second) { updateValueIfNecessary(mapFunction(first.value as T, it)) }

        value = mapFunction(first.value as T, second.value as R)
    }
}
