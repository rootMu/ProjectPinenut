package com.projects.rootmu.projectpinenut.ui.util.general.delegates

class GeneratedDefaultDelegate<T>(
    private val propertyDelegate: PropertyDelegate<T?>,
    private val defaultGenerator: () -> T
): PropertyDelegate<T> {

    override fun get(): T {
        return propertyDelegate.get() ?: defaultGenerator().also {
            propertyDelegate.set(it)
        }
    }

    override fun set(value: T) {
        propertyDelegate.set(value)
    }

}

fun <T> PropertyDelegate<T?>.withGeneratedDefault(defaultGenerator: () -> T) =
    GeneratedDefaultDelegate(this, defaultGenerator)

fun <T> PropertyDelegate<T?>.orThrow() = GeneratedDefaultDelegate(this) {
    throw IllegalStateException("Property is not initialized")
}