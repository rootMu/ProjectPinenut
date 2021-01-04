package com.projects.rootmu.projectpinenut.ui.util.general.delegates

class EnumNameDelegate<T: Enum<T>>(
    private val stringDelegate: PropertyDelegate<String?>,
    enumType: Class<T>
):
    PropertyDelegate<T?> {

    private val constants by lazy { enumType.enumConstants!!.toList() }

    override fun get(): T? {
        return stringDelegate.get()?.let { stringVal ->
            constants.firstOrNull { it.name == stringVal }
        }
    }

    override fun set(value: T?) {
        stringDelegate.set(value?.name)
    }
}

inline fun <reified T: Enum<T>> enumDelegate(stringDelegate: PropertyDelegate<String?>): PropertyDelegate<T?> {
    return EnumNameDelegate(stringDelegate, T::class.java)
}