package com.projects.rootmu.projectpinenut.ui.screens.home

import androidx.fragment.app.Fragment
import com.projects.rootmu.projectpinenut.ui.components.base.ContainerFragment
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationCountListener

class HomeContainerFragment(private val listener: BottomNavigationCountListener): ContainerFragment() {

    override fun getInitialFragment(): Fragment = HomeFragment().apply { this.bottomNavigationCountListener = listener}
}