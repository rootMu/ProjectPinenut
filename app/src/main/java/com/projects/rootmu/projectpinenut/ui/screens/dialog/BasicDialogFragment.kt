package com.projects.rootmu.projectpinenut.ui.screens.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.rootmu.projectpinenut.databinding.FragmentDialogBasicBinding
import com.projects.rootmu.projectpinenut.ui.components.base.BaseDialogFragment
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.models.DialogResult
import com.projects.rootmu.projectpinenut.ui.util.general.autoCleared
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.getLongDelegate
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.getValue
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.orThrow
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.setValue
import kotlinx.android.synthetic.main.fragment_dialog_basic.*

class BasicDialogFragment : BaseDialogFragment() {

    companion object {
        fun instantiate(id: Long): BasicDialogFragment {
            return BasicDialogFragment().apply {
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
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        dialogInstanceViewModel.getTypedData<DialogData.Basic>()?.let {
//            setDialogData(it)
//        }
//
//        primary_cta.setOnClickListener {
//            dismiss()
//            dialogInstanceViewModel.setResult(DialogResult.Primary())
//        }
//
//        secondary_cta.setOnClickListener {
//            dismiss()
//            dialogInstanceViewModel.setResult(DialogResult.Secondary)
//        }

    }

    private fun setDialogData(dialogData: DialogData.Basic) {
        binding.data = dialogData
    }

}