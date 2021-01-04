package com.projects.rootmu.projectpinenut.ui.screens.account

import androidx.fragment.app.Fragment
import com.projects.rootmu.projectpinenut.ui.components.base.ContainerFragment
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationCountListener
import com.projects.rootmu.projectpinenut.ui.screens.home.HomeFragment

class AccountContainerFragment(private val listener: BottomNavigationCountListener): ContainerFragment() {

    override fun getInitialFragment(): Fragment = HomeFragment()
}