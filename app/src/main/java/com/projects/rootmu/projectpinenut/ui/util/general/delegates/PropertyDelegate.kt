package com.projects.rootmu.projectpinenut.ui.util.general.delegates

import kotlin.reflect.KProperty

interface PropertyDelegate<T> {

    fun get(): T

    fun set(value: T)
}

operator fun <T> PropertyDelegate<T>.getValue(thisRef: Any?, property: KProperty<*>): T {
    return get()
}

operator fun <T> PropertyDelegate<T>.setValue(thisRef: Any?, property: KProperty<*>, value: T) {
    set(value)
}