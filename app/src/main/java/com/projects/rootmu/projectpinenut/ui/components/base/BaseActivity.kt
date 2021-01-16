package com.projects.rootmu.projectpinenut.ui.components.base

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.ui.components.listeners.Navigator
import com.projects.rootmu.projectpinenut.util.general.TargetedObserver

abstract class BaseActivity : AppCompatActivity(), Navigator {

    protected open val mainFragmentContainerId: Int
        get() = R.id.main_fragment

    open val observers = listOf<TargetedObserver<*>>()

    override fun onResume() {
        super.onResume()

        for (targetedObserver in observers) {
            targetedObserver.reObserve(this)
        }
    }

    private fun changeToFragment(
        fragment: Fragment,
        backStackName: String? = null,
        addToBackStack: Boolean = true,
        tag: String? = null,
        enterAnim: Int = 0,
        exitAnim: Int = 0,
        popEnterAnim: Int = 0,
        popExitAnim: Int = 0,
        @IdRes containerId: Int = mainFragmentContainerId
    ) {
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
            val previous = supportFragmentManager.findFragmentById(containerId) as? BaseFragment
            replace(containerId, fragment, tag)
            previous?.willAnimateAway(exitAnim)
            if (addToBackStack) addToBackStack(backStackName)
        }.commit()
    }

    protected fun addFragment(
        fragment: Fragment,
        backStackName: String? = null,
        tag: String? = null,
        enterAnim: Int = 0,
        exitAnim: Int = 0, @IdRes containerId: Int = mainFragmentContainerId
    ) {
        supportFragmentManager.beginTransaction().apply {
            setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim)
            add(containerId, fragment, tag)
            if (backStackName != null) addToBackStack(backStackName)
        }.commit()
    }

    /** Navigator **/

    override fun changeToScreen(
        fragment: Fragment,
        backStackName: String?,
        animated: Boolean,
        addToBackStack: Boolean
    ) {
        changeToFragment(fragment, backStackName = backStackName)
    }

    override fun back(pastName: String?) {
        if (pastName != null) {
            supportFragmentManager.popBackStack(pastName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } else {
            supportFragmentManager.popBackStack()
        }
    }

}