package com.projects.rootmu.projectpinenut.ui.screens.home

import androidx.fragment.app.Fragment
import com.projects.rootmu.projectpinenut.ui.components.base.ContainerFragment
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationListener

class HomeContainerFragment(private val listener: BottomNavigationListener): ContainerFragment() {

    override fun getInitialFragment(): Fragment = HomeFragment().apply { this.bottomNavigationListener = listener}
}