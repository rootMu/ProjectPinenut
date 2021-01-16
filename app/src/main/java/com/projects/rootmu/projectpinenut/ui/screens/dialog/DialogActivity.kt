package com.projects.rootmu.projectpinenut.ui.screens.dialog

import androidx.activity.viewModels
import com.projects.rootmu.projectpinenut.ui.components.base.BaseActivity
import com.projects.rootmu.projectpinenut.ui.components.base.BaseDialogFragment
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.viewmodel.dialog.DialogViewModel
import com.projects.rootmu.projectpinenut.util.general.TargetedObserver
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class DialogActivity: BaseActivity() {

    companion object {
        const val DIALOG_FRAGMENT_TAG = "dialog"
    }

    private val dialogViewModel: DialogViewModel by viewModels()

    override val observers = super.observers + listOf(
        TargetedObserver({ dialogViewModel.activeDialogData }) { newDialogDataAndId ->
            val currentDialogFragment =
                supportFragmentManager.findFragmentByTag(DIALOG_FRAGMENT_TAG) as? BaseDialogFragment

            when {
                // If the id is the one showing ignore
                currentDialogFragment?.id == newDialogDataAndId?.first -> return@TargetedObserver
                // If we're showing something and we shouldn't then dismiss
                newDialogDataAndId == null ->
                    supportFragmentManager.fragments.filter { it.tag == DIALOG_FRAGMENT_TAG }
                        .filterIsInstance<BaseDialogFragment>().forEach(BaseDialogFragment::dismiss)
                // Otherwise show the new dialog
                else -> {
                    supportFragmentManager.fragments.filter { it.tag == DIALOG_FRAGMENT_TAG }
                        .filterIsInstance<BaseDialogFragment>().forEach(BaseDialogFragment::dismiss)
                    val fragment = newDialogDataAndId.second.createDialog(newDialogDataAndId.first)
                    fragment.show(supportFragmentManager, DIALOG_FRAGMENT_TAG)
                }
            }
        })

    fun DialogData.createDialog(id: Long) = when (this) {
        is DialogData.Input -> InputDialogFragment.instantiate(id)
        is DialogData.Basic -> BasicDialogFragment.instantiate(id)
        is DialogData.Banner -> BannerDialogFragment.instantiate(id)
    }

}