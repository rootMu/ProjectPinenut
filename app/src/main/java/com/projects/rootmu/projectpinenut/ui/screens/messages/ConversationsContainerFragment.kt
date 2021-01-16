package com.projects.rootmu.projectpinenut.ui.screens.messages

import androidx.fragment.app.Fragment
import com.projects.rootmu.projectpinenut.ui.components.base.ContainerFragment

class ConversationsContainerFragment : ContainerFragment() {

    override fun getInitialFragment(): Fragment = ConversationsFragment()

}