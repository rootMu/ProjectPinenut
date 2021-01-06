package com.projects.rootmu.projectpinenut.ui.components.listeners

interface BottomNavigationListener {
    fun updateBottomNavigationCount(id: Int, count: String? = null)
    fun setBottomNavigationReselectedListener(listener: BottomNavigationReselectedListener)
}