package com.projects.rootmu.projectpinenut.ui.viewmodel.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.facebook.login.widget.LoginButton.ToolTipMode.fromInt
import com.projects.rootmu.projectpinenut.ui.models.MainTab

class MainTabsViewModel @ViewModelInject constructor(): ViewModel() {

    fun getInitialSelectedTab(): MainTab {
        return MainTab.JOBS
    }

    fun setInitialSelectedTab() {
        return
    }

    fun getCurrentSelectedTab(): MainTab{
        return currentTab
    }

    fun setCurrentSelectedTab(index: Int){
        currentTab = MainTab.fromInt(index)
    }

    private var currentTab: MainTab = getInitialSelectedTab()
}