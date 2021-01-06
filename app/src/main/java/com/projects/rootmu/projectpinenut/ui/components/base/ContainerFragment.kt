package com.projects.rootmu.projectpinenut.ui.components.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.ui.components.FullScreenChild
import com.projects.rootmu.projectpinenut.ui.components.listeners.Navigator
import com.projects.rootmu.projectpinenut.ui.screens.main.OnTabIconClickedListener

abstract class ContainerFragment : BaseFragment(), Navigator, OnTabIconClickedListener {

    protected val fullScreenFragmentContainerId = R.id.full_screen_fragment_container

    protected val activeFragment
        get() = childFragmentManager.findFragmentById(childFragmentContainerId)
            ?.takeIf { it.isAdded }
            ?: childFragmentManager.findFragmentById(fullScreenFragmentContainerId)
                ?.takeIf { it.isAdded }

    protected val isBackStackEmpty
        get() = childFragmentManager.backStackEntryCount == 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_container, container, false)

    open fun onBackStackChanged() {
        activeFragment?.let { updateStateForFragment(it) }
    }

    open fun updateStateForFragment(fragment: Fragment, forceImmediate: Boolean = false) {
        //Do Nothing
    }

    abstract fun getInitialFragment(): Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        childFragmentManager.addOnBackStackChangedListener {
            onBackStackChanged()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val isFirstCreation = !hasCreatedView
        super.onViewCreated(view, savedInstanceState)

        val fragment = if (savedInstanceState == null && isFirstCreation) {
            getInitialFragment().also {
                addChildFragment(
                    it,
                    containerId = if (it is FullScreenChild) fullScreenFragmentContainerId else childFragmentContainerId
                )
            }
        } else {
            activeFragment
        }

        fragment?.let { updateStateForFragment(it, forceImmediate = true) }
    }

    override fun getReplaceChildFragmentTransaction(
        fragment: Fragment,
        backStackName: String?,
        addToBackStack: Boolean,
        tag: String?,
        enterAnim: Int,
        exitAnim: Int,
        popEnterAnim: Int,
        popExitAnim: Int,
        containerId: Int,
        animated: Boolean
    ): FragmentTransaction {
        return super.getReplaceChildFragmentTransaction(
            fragment,
            backStackName,
            addToBackStack,
            tag,
            enterAnim,
            exitAnim,
            popEnterAnim,
            popExitAnim,
            containerId,
            animated
        ).apply {
            val complimentaryId = when (containerId) {
                childFragmentContainerId -> fullScreenFragmentContainerId
                fullScreenFragmentContainerId -> childFragmentContainerId
                else -> null
            }
            if (complimentaryId != null) {
                childFragmentManager.findFragmentById(complimentaryId)?.takeIf { it.isAdded }?.let {
                    remove(it)
                    (it as? BaseFragment)?.willAnimateAway(exitAnim)
                }
            }
        }
    }

    protected open fun goBack(pastName: String? = null) {
        if (pastName != null) {
            childFragmentManager.popBackStack(pastName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        } else {
            childFragmentManager.popBackStack()
        }
    }

    /** OnTabIconClickedListener **/

    override fun onTabIconClicked(isNavigatingToTab: Boolean) {
        if (isAdded) {
            if (!isNavigatingToTab && childFragmentManager.backStackEntryCount > 0) {
                childFragmentManager.popBackStack()
            } else {
                (activeFragment as? OnTabIconClickedListener)?.onTabIconClicked(isNavigatingToTab)
            }
        }
    }

    /** Navigator **/

    override fun changeToScreen(fragment: Fragment, backStackName: String?, animated: Boolean) {
        replaceChildFragment(fragment, backStackName = backStackName, animated = animated)
    }

    override fun back(pastName: String?) {
        goBack(pastName)
    }

    /**  Functions for children to override **/

    open fun onTopButtonClicked(isRootFragment: Boolean = false) {
        childFragmentManager.popBackStack()
    }

}