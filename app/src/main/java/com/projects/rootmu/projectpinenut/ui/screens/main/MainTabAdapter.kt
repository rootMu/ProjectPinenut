package com.projects.rootmu.projectpinenut.ui.screens.main

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationListener
import com.projects.rootmu.projectpinenut.ui.components.tabbedFragmentControl.TabbedFragmentContainer
import com.projects.rootmu.projectpinenut.ui.models.MainTab
import com.projects.rootmu.projectpinenut.ui.screens.account.AccountContainerFragment
import com.projects.rootmu.projectpinenut.ui.screens.home.HomeContainerFragment
import com.projects.rootmu.projectpinenut.ui.screens.jobs.JobsContainerFragment
import com.projects.rootmu.projectpinenut.ui.screens.messages.ConversationsContainerFragment

class MainTabAdapter(
    private val manager: FragmentManager,
    private val resources: Resources,
    private val bottomNavigationCountListener: BottomNavigationListener
) : TabbedFragmentContainer.Adapter {
    private val tabs = MainTab.values()

    override fun getFragmentManager(): FragmentManager = manager

    override fun getTabTitle(index: Int) = resources.getString(tabs[index].titleRes)

    override fun getTabIconResId(index: Int) = tabs[index].getIconId()

    override fun getCount(): Int = tabs.size

    override fun getItemTag(index: Int): String = tabs[index].getFragmentTag()

    override fun getItem(index: Int): Fragment {
        return tabs[index].getFragment(bottomNavigationCountListener)
    }

    override fun getItems() = tabs.map { it.getFragment(bottomNavigationCountListener) }

    fun getIndexOf(tab: MainTab) = tab.ordinal

    fun getTab(index: Int) = MainTab.values()[index]

}

fun MainTab.getFragment(bottomNavigationCountListener: BottomNavigationListener): Fragment =
    when (this) {
        MainTab.HOME -> HomeContainerFragment(bottomNavigationCountListener)
        MainTab.MESSAGES -> ConversationsContainerFragment(bottomNavigationCountListener)
        MainTab.JOBS -> JobsContainerFragment(bottomNavigationCountListener)
        MainTab.NOTIFICATIONS -> AccountContainerFragment(bottomNavigationCountListener)
        MainTab.ACCOUNT -> AccountContainerFragment(bottomNavigationCountListener)
    }

fun MainTab.getFragmentTag(): String =
    when (this) {
        MainTab.HOME -> HomeContainerFragment::class.simpleName ?: "home"
        MainTab.MESSAGES -> ConversationsContainerFragment::class.simpleName ?: "messages"
        MainTab.JOBS -> JobsContainerFragment::class.simpleName ?: "jobs"
        MainTab.NOTIFICATIONS -> AccountContainerFragment::class.simpleName ?: "notifications"
        MainTab.ACCOUNT -> AccountContainerFragment::class.simpleName ?: "account"
    }
