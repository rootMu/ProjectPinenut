package com.projects.rootmu.projectpinenut.ui.screens.home

import androidx.fragment.app.Fragment
import com.projects.rootmu.projectpinenut.ui.components.base.ContainerFragment

class HomeContainerFragment : ContainerFragment() {

    override fun getInitialFragment(): Fragment = HomeFragment()
}