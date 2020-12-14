package com.projects.rootmu.projectpinenut.listeners

import androidx.fragment.app.Fragment
import com.projects.rootmu.projectpinenut.utils.owning

interface Navigator {

    fun changeToScreen(fragment: Fragment, backStackName: String?, animated: Boolean)

    fun back(pastName: String?)
}

fun Fragment.navigateBack(pastName: String? = null) = owning<Navigator>()?.back(pastName)

fun Fragment.navigateTo(
    fragment: Fragment,
    backStackName: String? = null,
    animated: Boolean = true
) = owning<Navigator>()?.changeToScreen(fragment, backStackName, animated)

interface ResetableNavigator:
    Navigator {

    fun changeRootScreen(fragment: Fragment)

    fun closeNavigation()
}

fun Fragment.resetNavigationTo(fragment: Fragment) =
    owning<ResetableNavigator>()?.changeRootScreen(fragment)

fun Fragment.closeCurrentNavigation() = owning<ResetableNavigator>()?.closeNavigation()