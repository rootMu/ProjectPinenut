package com.projects.rootmu.projectpinenut.ui.screens.messages

import androidx.fragment.app.Fragment
import com.projects.rootmu.projectpinenut.ui.components.base.ContainerFragment
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationListener

class ConversationsContainerFragment(private val listener: BottomNavigationListener) : ContainerFragment() {

    override fun getInitialFragment(): Fragment = ConversationsFragment().apply{this.bottomNavigationListener = listener}
}