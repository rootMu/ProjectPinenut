package com.projects.rootmu.projectpinenut.ui.viewmodel.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.projects.rootmu.projectpinenut.ui.components.listeners.WaterfallToolbarListener
import com.projects.rootmu.projectpinenut.ui.models.NavType

class ToolBarViewModel @ViewModelInject constructor() : ViewModel() {

    private var waterfallToolbarListener: WaterfallToolbarListener? = null

    fun setWaterfallToolbarListener(listener: WaterfallToolbarListener) {
        waterfallToolbarListener = listener
    }

    fun clearWaterfallToolbarListener() {
        waterfallToolbarListener = null
    }


    fun setWaterfallToolbarRecyclerView(recyclerView: RecyclerView) {
        waterfallToolbarListener?.setRecyclerView(recyclerView)
    }

    fun showBackNavigation(show: Boolean) {
        waterfallToolbarListener?.setNavigationIcon(if (show) NavType.BACK else null)
    }

    fun updateTitle(title: String?) {
        waterfallToolbarListener?.setToolbarTitle(title)
    }
}