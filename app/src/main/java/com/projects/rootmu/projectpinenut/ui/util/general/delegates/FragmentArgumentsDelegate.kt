package com.projects.rootmu.projectpinenut.ui.util.general.delegates

import android.os.Bundle
import androidx.fragment.app.Fragment

abstract class FragmentArgumentsDelegate<T>(
    protected val fragment: Fragment,
    protected val key: String
): PropertyDelegate<T> {

    protected val arguments
        get() = fragment.arguments

    protected fun getOrCreateArguments() =
        arguments ?: Bundle().also { fragment.arguments = it }

    protected fun getItem() = arguments?.get(key)

    protected inline fun <reified T> getItemAsType() = getItem() as? T
}