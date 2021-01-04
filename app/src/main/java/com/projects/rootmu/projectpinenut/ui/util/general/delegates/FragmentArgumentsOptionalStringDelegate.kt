package com.projects.rootmu.projectpinenut.ui.util.general.delegates

import androidx.fragment.app.Fragment

class FragmentArgumentsOptionalStringDelegate(
    fragment: Fragment,
    key: String
): FragmentArgumentsDelegate<String?>(fragment, key) {

    override fun get(): String? = getItemAsType()

    override fun set(value: String?) {
        if (value == null) {
            getOrCreateArguments().remove(key)
        } else {
            getOrCreateArguments().putString(key, value)
        }
    }
}

fun Fragment.getStringDelegate(key: String) =
    FragmentArgumentsOptionalStringDelegate(this, key)