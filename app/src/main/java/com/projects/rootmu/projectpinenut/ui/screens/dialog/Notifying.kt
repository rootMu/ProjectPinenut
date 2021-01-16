package com.projects.rootmu.projectpinenut.ui.screens.dialog

import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.models.DialogResult
import com.projects.rootmu.projectpinenut.ui.viewmodel.dialog.DialogViewModel
import com.projects.rootmu.projectpinenut.util.general.TargetedObserver

interface Notifying<T> {

    companion object {
        const val DIALOG_IDS_KEY = "DIALOG_IDS"
    }

    val dialogViewModel: DialogViewModel

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
    val id = dialogViewModel.createDialog(dialogData)
    dialogIds[id] = category
}

fun <T> Notifying<T>.clearDialog(category: T) {
    if (!isShowingDialog(category)) return
    val id = dialogIds.entries.firstOrNull { it.value == category }?.key ?: return
    dialogViewModel.removeDialog(id)
    dialogIds.remove(id)
}


fun <T> Notifying<T>.getActionObserver() =
    TargetedObserver({ dialogViewModel.dialogResults }) { results ->
        val myResults = results.filter { dialogIds.containsKey(it.key) }
        if (myResults.isNotEmpty()) {

            for ((id, result) in myResults) {
                handleDialogResponse(id, result)
            }

            dialogViewModel.handledDialogResults(myResults.keys)
        }
    }

fun <T> Notifying<T>.handleDialogResponse(dialogId: Long, result: DialogResult) {
    dialogIds[dialogId]?.let { type ->
        when (result) {
            is DialogResult.Primary -> if (result.input != null) doPrimaryActionWithInput(
                type,
                result.input
            ) else doPrimaryAction(type)
            DialogResult.Secondary -> doSecondaryAction(type)
        }
        dialogIds.remove(dialogId)
    }
}

fun <T> Notifying<T>.isShowingDialog(category: T): Boolean {
    return dialogIds.values.contains(category)
}