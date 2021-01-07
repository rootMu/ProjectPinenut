package com.projects.rootmu.projectpinenut.ui.screens.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.projects.rootmu.projectpinenut.ui.components.base.BaseFragment
import com.projects.rootmu.projectpinenut.ui.models.MainTab
import com.projects.rootmu.projectpinenut.ui.models.getFragmentTag
import com.projects.rootmu.projectpinenut.ui.screens.account.AccountContainerFragment
import com.projects.rootmu.projectpinenut.ui.screens.home.HomeContainerFragment
import com.projects.rootmu.projectpinenut.ui.screens.jobs.JobsContainerFragment
import com.projects.rootmu.projectpinenut.ui.screens.messages.ConversationsContainerFragment
import java.lang.ref.WeakReference

class BottomNavAdapter(fragment: BaseFragment) :
    FragmentStateAdapter(fragment) {

    companion object {
        @JvmStatic
        fun newInstance(fragment: BaseFragment): BottomNavAdapter {
            return BottomNavAdapter(fragment)
        }
    }

    private val tabs = MainTab.values()

    private val fragments: Array<WeakReference<Fragment>?> =
        arrayOfNulls<WeakReference<Fragment>?>(itemCount).also { fragments ->
            val fragmentManager = fragment.childFragmentManager
            repeat(itemCount) { index ->
                fragmentManager.findFragmentByTag(tabs[index].getFragmentTag())?.let {
                    fragments[index] = WeakReference(it)
                }
            }
        }

    override fun getItemCount() = tabs.size

    override fun createFragment(position: Int): Fragment {

        return fragments[position]?.get() ?: when (MainTab.fromInt(position)) {
            MainTab.HOME -> HomeContainerFragment()
            MainTab.MESSAGES -> ConversationsContainerFragment()
            MainTab.JOBS -> JobsContainerFragment()
            MainTab.NOTIFICATIONS -> AccountContainerFragment()
            MainTab.ACCOUNT -> AccountContainerFragment()
        }.also {
            fragments[position] = WeakReference(it)
        }
    }

    fun getItem(position: Int) = createFragment(position)

    fun getIndexOf(tab: MainTab) = tab.ordinal

    fun getTabIconResId(index: Int) = tabs[index].getIconId()

}