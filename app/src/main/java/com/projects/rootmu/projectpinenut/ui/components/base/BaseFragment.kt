package com.projects.rootmu.projectpinenut.ui.components.base

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.animation.AnimationUtils
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationReselectedListener
import com.projects.rootmu.projectpinenut.ui.util.general.getMainHandler
import com.projects.rootmu.projectpinenut.ui.util.general.onBackPressed
import com.projects.rootmu.projectpinenut.ui.viewmodel.main.MainTabsViewModel
import com.projects.rootmu.projectpinenut.util.general.TargetedObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment : Fragment(), OnBackPressedListener,
    BottomNavigationReselectedListener {

    open val observers = listOf<TargetedObserver<*>>()

    open val tasks = listOf<Runnable>()

    val baseActivity
        get() = activity as? BaseActivity

    var hasResumed = false
        private set

    var hasCreatedView = false
        private set

    val mainHandler: Handler by lazy {
        getMainHandler()
    }

    protected open val onBackPressed: (() -> Unit)? = null

    protected open val childFragmentContainerId = R.id.child_fragment_container

    protected val mainTabsViewModel: MainTabsViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        for (targetedObserver in observers(TargetedObserver.ActivationTime.VIEW_CREATED)) {
            targetedObserver.reObserve(viewLifecycleOwner)
        }

        setOnBackPressed(onBackPressed)
        mainTabsViewModel.updateBottomNavigationReselectListener(this)

    }

    override fun onPause() {
        super.onPause()
        tasks.map {
            mainHandler.removeCallbacks(it)
        }
    }

    override fun onResume() {
        super.onResume()

        observers(TargetedObserver.ActivationTime.RESUME).map {
            it.reObserve(this)
        }

        tasks.map {
            mainHandler.post(it)
        }

    }

    private fun setOnBackPressed(onBackPressed: (() -> Unit)? = null) {
        onBackPressed(onBackPressed)
    }

    protected fun addChildFragment(
        fragment: Fragment,
        backStackName: String? = null,
        tag: String? = null,
        enterAnim: Int = 0,
        exitAnim: Int = 0,
        @IdRes containerId: Int = childFragmentContainerId
    ) {
        childFragmentManager.beginTransaction().apply {
            setCustomAnimations(enterAnim, exitAnim, enterAnim, exitAnim)
            add(containerId, fragment, tag)
            if (backStackName != null) addToBackStack(backStackName)
        }.commit()
    }

    protected open fun replaceChildFragment(
        fragment: Fragment,
        backStackName: String? = null,
        addToBackStack: Boolean = true,
        tag: String? = null,
        enterAnim: Int = R.anim.push_enter,
        exitAnim: Int = R.anim.push_exit,
        popEnterAnim: Int = R.anim.pop_enter,
        popExitAnim: Int = R.anim.pop_exit,
        @IdRes containerId: Int = childFragmentContainerId,
        animated: Boolean = true
    ) {
        getReplaceChildFragmentTransaction(
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
        ).commit()
    }

    protected open fun getReplaceChildFragmentTransaction(
        fragment: Fragment,
        backStackName: String?,
        addToBackStack: Boolean,
        tag: String?,
        enterAnim: Int,
        exitAnim: Int,
        popEnterAnim: Int,
        popExitAnim: Int,
        @IdRes containerId: Int = childFragmentContainerId,
        animated: Boolean
    ) = childFragmentManager.beginTransaction().apply {
        if (animated) {
            setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim)
        } else {
            setCustomAnimations(0, 0, popEnterAnim, popExitAnim)
        }
        val previous = childFragmentManager.findFragmentById(containerId) as? BaseFragment

        replace(containerId, fragment, tag)

        previous?.willAnimateAway(exitAnim)
        if (addToBackStack) addToBackStack(backStackName)

    }

    fun willAnimateAway(animation: Int) {
        val anim = AnimationUtils.loadAnimation(context, animation)

        for (fragment in childFragmentManager.fragments) {
            (fragment as? BaseFragment)?.ancestorAnimatingAway(anim.duration)
        }
    }

    private var ancestorAnimateAwayDuration: Long? = null

    private fun observers(activationTime: TargetedObserver.ActivationTime) = observers.filter {
        it.activationTime == activationTime
    }

    open fun ancestorAnimatingAway(duration: Long) {
        ancestorAnimateAwayDuration = duration

        for (fragment in childFragmentManager.fragments) {
            (fragment as? BaseFragment)?.ancestorAnimatingAway(duration)
        }
    }

    /** BackPressedListener **/

    override fun onBackPressed(): Boolean {
        val topChildFragment =
            childFragmentManager.findFragmentById(childFragmentContainerId) as? OnBackPressedListener
        val backConsumed = topChildFragment?.onBackPressed() ?: false

        return if (backConsumed) {
            true
        } else {
            if (childFragmentManager.backStackEntryCount == 0) {
                false
            } else {
                childFragmentManager.popBackStack()
                true
            }
        }
    }

    /** BottomNavigationReselectedListener **/

    override fun onNavigationItemReselected(model: MeowBottomNavigation.Model) {
        // DO Nothing
    }

}