package com.projects.rootmu.projectpinenut.ui.screens.dialog

import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.util.general.TargetedObserver

interface Notifying<T> {

    companion object {
        const val DIALOG_IDS_KEY = "DIALOG_IDS"
    }

    val dialogIds: MutableMap<Long, T>

    fun doPrimaryAction(category: T)

    fun doPrimaryActionWithInput(category: T, input: String) {}

    fun doSecondaryAction(category: T)

    fun getDialogData(category: T): DialogData
}

fun <T> Notifying<T>.showDialog(category: T, debugMessage: String? = null) {
    if (isShowingDialog(category)) return
    val origDialogData = getDialogData(category)
    val dialogData = if (origDialogData is DialogData.Basic && debugMessage != null) {
        val data = origDialogData.data.copy(debugMessage = debugMessage)
        origDialogData.copy(data = data)
    } else {
        origDialogData
    }
    //val id = showDialogViewModel.createDialog(dialogData)
    //dialogIds[id] = category
}

fun <T> Notifying<T>.isShowingDialog(category: T): Boolean {
    return dialogIds.values.contains(category)
}