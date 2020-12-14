package com.projects.rootmu.projectpinenut.utils

import androidx.annotation.CheckResult
import androidx.fragment.app.Fragment

@CheckResult
inline fun <reified T> Fragment.owning(): T? {
    var currentAncestor = parentFragment
    while (currentAncestor != null) {
        if (currentAncestor is T && currentAncestor.isAdded) {
            return currentAncestor
        }
        currentAncestor = currentAncestor.parentFragment
    }
    return activity?.takeIf { !it.isDestroyed } as? T
}
