package com.projects.rootmu.projectpinenut.ui.viewmodel.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.projects.rootmu.projectpinenut.ui.models.MainTab

class MainTabsViewModel @ViewModelInject constructor() : ViewModel() {

    private var currentTab: MainTab = getInitialSelectedTab()

    fun getInitialSelectedTab(): MainTab {
        return MainTab.JOBS
    }

    fun getCurrentSelectedTab(): MainTab {
        return currentTab
    }

    fun setCurrentSelectedTab(index: Int) {
        currentTab = MainTab.fromInt(index)
    }

}