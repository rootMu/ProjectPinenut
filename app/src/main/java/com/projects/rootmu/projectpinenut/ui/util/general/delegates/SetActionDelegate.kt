package com.projects.rootmu.projectpinenut.ui.util.general.delegates

class SetActionDelegate<T>(
    private val propertyDelegate: PropertyDelegate<T>,
    private val action: (T) -> Unit
): PropertyDelegate<T> by propertyDelegate {

    override fun set(value: T) {
        propertyDelegate.set(value)
        action.invoke(value)
    }
}

fun <T> PropertyDelegate<T>.withOnSetAction(action: (T) -> Unit): PropertyDelegate<T> {
    return SetActionDelegate(this, action)
}