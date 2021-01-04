package com.projects.rootmu.projectpinenut.ui.screens.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.rootmu.projectpinenut.databinding.FragmentDialogBannerBinding
import com.projects.rootmu.projectpinenut.ui.components.base.BaseDialogFragment
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.models.PopupType
import com.projects.rootmu.projectpinenut.ui.util.general.autoCleared
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.getLongDelegate
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.getValue
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.orThrow
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.setValue
import com.projects.rootmu.projectpinenut.ui.util.specific.iconId
import kotlinx.android.synthetic.main.fragment_dialog_banner.*

class BannerDialogFragment : BaseDialogFragment() {

    companion object {
        fun instantiate(id: Long): BannerDialogFragment {
            return BannerDialogFragment().apply {
                this.id = id
            }
        }
    }

    override var id by getLongDelegate("dialogId").orThrow()
        private set

    private var binding: FragmentDialogBannerBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDialogBannerBinding.inflate(inflater, container, false).let {
        binding = it
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    private fun setDialogData(dialogData: DialogData.Banner) {
        icon.setImageResource(dialogData.type.iconId())
        description.text = dialogData.text

        if (dialogData.type in listOf(PopupType.WARNING, PopupType.ERROR)) {
            //dialogInstanceViewModel.shownError(dialogData.text)
        }
    }

}