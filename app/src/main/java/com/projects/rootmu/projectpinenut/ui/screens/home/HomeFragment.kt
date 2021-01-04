package com.projects.rootmu.projectpinenut.ui.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.HomeFragmentBinding
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationCountListener
import com.projects.rootmu.projectpinenut.ui.screens.dialog.NotifyingBaseFragment
import com.projects.rootmu.projectpinenut.ui.screens.dialog.showDialog
import com.projects.rootmu.projectpinenut.ui.screens.main.OnTabIconClickedListener
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.models.PopupType
import com.projects.rootmu.projectpinenut.ui.screens.jobs.JobsFragment
import com.projects.rootmu.projectpinenut.ui.util.general.autoCleared
import kotlinx.android.synthetic.main.home_fragment.*
import java.io.Serializable
import javax.annotation.meta.Exhaustive

class HomeFragment : NotifyingBaseFragment<HomeFragment.DialogCategory>(), OnTabIconClickedListener {

    sealed class DialogCategory : Serializable {
        object UpdateFailed : DialogCategory()
    }

    companion object{
        const val INDEX = 0
    }

    private var binding: HomeFragmentBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomNavigationCountListener?.updateBottomNavigationCount(INDEX, "test")
    }

    private fun showUpdateFailed(update: Unit) {
        showDialog(
            DialogCategory.UpdateFailed
        )
    }

    /** OnTabIconClickedListener **/

    override fun onTabIconClicked(isNavigatingToTab: Boolean) {
        if (!isNavigatingToTab) {
            scroll_view.smoothScrollTo(0, 0)
        }
    }

    // Notifying

    override fun doPrimaryAction(category: DialogCategory) {
        @Exhaustive
        when (category) {
            DialogCategory.UpdateFailed -> {
                //DO Nothing
            }
        }
    }

    override fun doSecondaryAction(category: DialogCategory) {
        @Exhaustive
        when (category) {
            DialogCategory.UpdateFailed -> {
                //DO Nothing
            }
        }
    }

    override fun getDialogData(category: DialogCategory) = when (category) {
        DialogCategory.UpdateFailed -> DialogData.Banner.fromIds(
            resources,
            PopupType.WARNING,
            R.string.update_failed
        )
    }


}