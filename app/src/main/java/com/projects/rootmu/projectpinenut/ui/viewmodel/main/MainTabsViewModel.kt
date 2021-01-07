package com.projects.rootmu.projectpinenut.ui.viewmodel.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationListener
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationReselectedListener
import com.projects.rootmu.projectpinenut.ui.models.MainTab

class MainTabsViewModel @ViewModelInject constructor() : ViewModel() {

    var bottomNavigationListener: BottomNavigationListener? = null
        private set

    fun setBottomNavigationListener(listener: BottomNavigationListener) {
        bottomNavigationListener = listener
    }

    fun clearBottomNavigationListener() {
        bottomNavigationListener = null
    }

    fun updateBottomNavigationCount(id: Int, count: String? = null) {
        bottomNavigationListener?.updateBottomNavigationCount(
            id,
            count
        )
    }

    fun updateBottomNavigationReselectListener(listener: BottomNavigationReselectedListener) {
        bottomNavigationListener?.setBottomNavigationReselectedListener(listener)
    }

    fun getInitialSelectedTab(): MainTab {
        return MainTab.JOBS
    }

    fun getCurrentSelectedTab(): MainTab {
        return currentTab
    }

    fun setCurrentSelectedTab(index: Int) {
        currentTab = MainTab.fromInt(index)
    }

    private var currentTab: MainTab = getInitialSelectedTab()
}