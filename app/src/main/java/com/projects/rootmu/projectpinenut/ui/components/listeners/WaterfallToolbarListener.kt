package com.projects.rootmu.projectpinenut.ui.components.listeners

import androidx.recyclerview.widget.RecyclerView
import com.projects.rootmu.projectpinenut.ui.models.NavType

interface WaterfallToolbarListener {
    fun setRecyclerView(recyclerView: RecyclerView)
    fun setNavigationIcon(navType: NavType?)
    fun setToolbarTitle(title: String?)
}