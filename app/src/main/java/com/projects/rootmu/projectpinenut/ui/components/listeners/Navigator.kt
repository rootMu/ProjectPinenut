package com.projects.rootmu.projectpinenut.ui.components.listeners

import androidx.fragment.app.Fragment
import com.projects.rootmu.projectpinenut.ui.util.owning

interface Navigator {

    fun changeToScreen(
        fragment: Fragment,
        backStackName: String?,
        animated: Boolean,
        addToBackStack: Boolean
    )

    fun back(pastName: String?)

}

fun Fragment.navigateBack(pastName: String? = null) = owning<Navigator>()?.back(pastName)

fun Fragment.navigateTo(
    fragment: Fragment,
    backStackName: String? = null,
    animated: Boolean = true,
    addToBackStack: Boolean = true
) = owning<Navigator>()?.changeToScreen(fragment, backStackName, animated, addToBackStack)

