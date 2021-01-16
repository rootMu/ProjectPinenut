package com.projects.rootmu.projectpinenut.util.general

import androidx.lifecycle.MutableLiveData

// Explicitly bound from Any, instead of Any? which is the default
class NonNullMutableLiveData<T: Any>(initialValue: T): MutableLiveData<T>() {

    init {
        value = initialValue
    }

    override fun getValue(): T {
        // We set the value initially, and we can't set this back to null so we should be safe
        return super.getValue()!!
    }
}