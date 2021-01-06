package com.projects.rootmu.projectpinenut.ui.screens.account

import androidx.fragment.app.Fragment
import com.projects.rootmu.projectpinenut.ui.components.base.ContainerFragment
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationListener

class AccountContainerFragment(private val listener: BottomNavigationListener): ContainerFragment() {

    override fun getInitialFragment(): Fragment = AccountFragment()
}