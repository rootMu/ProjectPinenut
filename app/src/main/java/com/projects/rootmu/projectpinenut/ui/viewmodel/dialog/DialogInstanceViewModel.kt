package com.projects.rootmu.projectpinenut.ui.viewmodel.dialog

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.models.DialogResult
import com.projects.rootmu.projectpinenut.useCase.dialog.DialogUseCase

class DialogInstanceViewModel @ViewModelInject constructor(
    private val dialogUseCase: DialogUseCase,
) : ViewModel() {

    private var id: Long? = null

    inline fun <reified T : DialogData> getTypedData(id: Long): T? = getDialogData(id) as? T

    fun getDialogData(id: Long): DialogData? {
        setId(id)
        return id.let {
            dialogUseCase.findDialogData(it)
        }
    }

    private fun setId(id: Long) {
        this.id = id
    }

    fun setResult(result: DialogResult) {
        id?.let {
            dialogUseCase.setDialogResult(it, result)
            dialogUseCase.finishDialog(it)
        }
    }
}
