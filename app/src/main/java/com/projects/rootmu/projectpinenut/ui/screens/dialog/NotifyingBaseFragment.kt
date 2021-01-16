package com.projects.rootmu.projectpinenut.ui.screens.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import com.projects.rootmu.projectpinenut.ui.components.base.BaseFragment
import com.projects.rootmu.projectpinenut.ui.screens.dialog.Notifying.Companion.DIALOG_IDS_KEY
import java.io.Serializable

abstract class NotifyingBaseFragment<T: Serializable>: BaseFragment(), Notifying<T> {

    // Can't use a sparse array as it's not serializable
    @SuppressLint("UseSparseArrays")
    override var dialogIds = HashMap<Long, T>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        (savedInstanceState?.getSerializable(DIALOG_IDS_KEY) as? HashMap<Long, T>)?.let {
            dialogIds = it
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(DIALOG_IDS_KEY, dialogIds)
    }
}