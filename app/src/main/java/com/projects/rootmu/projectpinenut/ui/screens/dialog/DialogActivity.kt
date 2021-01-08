package com.projects.rootmu.projectpinenut.ui.screens.dialog

import com.projects.rootmu.projectpinenut.ui.components.base.BaseActivity
import com.projects.rootmu.projectpinenut.ui.models.DialogData

abstract class DialogActivity: BaseActivity() {

    fun DialogData.createDialog(id: Long) = when (this) {
        is DialogData.Input -> InputDialogFragment.instantiate(id)
        is DialogData.Basic -> BasicDialogFragment.instantiate(id)
        is DialogData.Banner -> BannerDialogFragment.instantiate(id)
    }

}