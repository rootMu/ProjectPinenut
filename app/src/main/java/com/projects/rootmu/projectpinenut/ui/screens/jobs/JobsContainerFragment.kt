package com.projects.rootmu.projectpinenut.ui.screens.jobs

import androidx.fragment.app.Fragment
import com.projects.rootmu.projectpinenut.ui.components.base.ContainerFragment
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationCountListener

class JobsContainerFragment(private val listener: BottomNavigationCountListener) : ContainerFragment() {

    override fun getInitialFragment(): Fragment = JobsFragment().apply{this.bottomNavigationCountListener = listener}
}