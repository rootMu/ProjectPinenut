package com.projects.rootmu.projectpinenut.ui.viewmodel.dialog

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.useCase.dialog.DialogUseCase
import com.projects.rootmu.projectpinenut.useCase.dialog.DialogUseCaseImpl

class DialogViewModel @ViewModelInject constructor(private val dialogUseCase: DialogUseCase) :
    ViewModel() {

    private val dialogIds = mutableSetOf<Long>()

    val dialogResults = dialogUseCase.dialogResults

    val activeDialogData: MutableLiveData<Pair<Long, DialogData>?> = dialogUseCase.activeDialogData as MutableLiveData<Pair<Long, DialogData>?>

    override fun onCleared() {
        super.onCleared()

        for (id in dialogIds) {
            dialogUseCase.finishDialog(id)
        }
    }

    fun createDialog(dialogData: DialogData) = dialogUseCase.createDialog(dialogData).also {
        dialogIds.add(it)
    }

    fun removeDialog(id: Long) {
        dialogUseCase.finishDialog(id)
        dialogIds.remove(id)
    }

    fun handledDialogResults(ids: Set<Long>) {
        dialogUseCase.clearDialogResults(ids)
        dialogIds.removeAll(ids)
    }
}