package com.projects.rootmu.projectpinenut.ui.viewmodel.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationListener
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationReselectedListener

class BottomNavigationViewModel @ViewModelInject constructor() : ViewModel() {

    private var bottomNavigationListener: BottomNavigationListener? = null

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
}