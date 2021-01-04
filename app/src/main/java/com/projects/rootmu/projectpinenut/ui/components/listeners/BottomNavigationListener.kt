package com.projects.rootmu.projectpinenut.ui.components.listeners

import com.etebarian.meowbottomnavigation.MeowBottomNavigation

interface BottomNavigationListener {
    fun onNavigationItemSelected(model: MeowBottomNavigation.Model)
    fun onNavigationItemReSelected(model: MeowBottomNavigation.Model)
}