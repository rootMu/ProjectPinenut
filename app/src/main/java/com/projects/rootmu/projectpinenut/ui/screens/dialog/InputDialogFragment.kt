package com.projects.rootmu.projectpinenut.ui.screens.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.rootmu.projectpinenut.databinding.FragmentDialogBasicBinding
import com.projects.rootmu.projectpinenut.ui.components.base.BaseDialogFragment
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.util.general.autoCleared
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.getLongDelegate
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.getValue
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.orThrow
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.setValue

class InputDialogFragment: BaseDialogFragment() {

    companion object {
        fun instantiate(id: Long): InputDialogFragment {
            return InputDialogFragment().apply {
                this.id = id
            }
        }
    }

    override var id by getLongDelegate("dialogId").orThrow()
        private set

    private var binding: FragmentDialogBasicBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDialogBasicBinding.inflate(inflater, container, false).let {
        binding = it
        it.lifecycleOwner = viewLifecycleOwner
        it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setDialogData(dialogData: DialogData.Basic) {
        binding.data = dialogData


//        if (dialogData.type in listOf(PopupType.WARNING, PopupType.ERROR)) {
//            //dialogInstanceViewModel.shownError(dialogData.data.text.toString())
//        }
    }

}