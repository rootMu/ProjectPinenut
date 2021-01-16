package com.projects.rootmu.projectpinenut.useCase.dialog

import androidx.lifecycle.LiveData
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.models.DialogResult
import com.projects.rootmu.projectpinenut.util.general.NonNullMutableLiveData
import com.projects.rootmu.projectpinenut.util.general.map
import javax.inject.Inject
import javax.inject.Singleton

interface DialogUseCase {

    val activeDialogData: LiveData<Pair<Long, DialogData>?>

    val dialogResults: LiveData<Map<Long, DialogResult>>

    fun createDialog(dialogData: DialogData): Long

    fun findDialogData(id: Long): DialogData?

    fun finishDialog(id: Long)

    fun setDialogResult(id: Long, result: DialogResult)

    fun clearDialogResults(ids: Set<Long>)
}

@Singleton
class DialogUseCaseImpl @Inject constructor(): DialogUseCase {

    private var nextId = 0L

    private fun getId(): Long = nextId++

    private val activeDialogs = NonNullMutableLiveData(emptyList<Pair<Long, DialogData>>())
    override val activeDialogData = activeDialogs.map { it.firstOrNull() }

    override val dialogResults = NonNullMutableLiveData(emptyMap<Long, DialogResult>())

    override fun createDialog(dialogData: DialogData): Long {
        val id = getId()

        activeDialogs.value += listOf(id to dialogData)

        return id
    }

    override fun findDialogData(id: Long): DialogData? {
        return activeDialogs.value.firstOrNull { (dialogId, _) -> dialogId == id }?.second
    }

    override fun finishDialog(id: Long) {
        activeDialogs.value = activeDialogs.value.filter { (dialogId, _) -> dialogId != id }
    }

    override fun setDialogResult(id: Long, result: DialogResult) {
        dialogResults.value += mapOf(id to result)
    }

    override fun clearDialogResults(ids: Set<Long>) {
        dialogResults.value -= ids
    }
}