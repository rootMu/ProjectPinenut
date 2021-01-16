package com.projects.rootmu.projectpinenut.ui.util.general.delegates

import androidx.fragment.app.Fragment

class FragmentArgumentsOptionalLongDelegate(
    fragment: Fragment,
    key: String
): FragmentArgumentsDelegate<Long?>(fragment, key) {

    override fun get(): Long? = getItemAsType()

    override fun set(value: Long?) {
        if (value != null) {
            getOrCreateArguments().putLong(key, value)
        } else {
            getOrCreateArguments().remove(key)
        }
    }
}

fun Fragment.getLongDelegate(key: String) = FragmentArgumentsOptionalLongDelegate(this, key)