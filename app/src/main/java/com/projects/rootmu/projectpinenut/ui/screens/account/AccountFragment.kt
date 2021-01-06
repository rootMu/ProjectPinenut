package com.projects.rootmu.projectpinenut.ui.screens.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.AccountFragmentBinding
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.models.PopupType
import com.projects.rootmu.projectpinenut.ui.screens.dialog.NotifyingBaseFragment
import com.projects.rootmu.projectpinenut.ui.screens.dialog.showDialog
import com.projects.rootmu.projectpinenut.ui.util.general.autoCleared
import java.io.Serializable
import javax.annotation.meta.Exhaustive

class AccountFragment : NotifyingBaseFragment<AccountFragment.DialogCategory>() {

    sealed class DialogCategory : Serializable {
        object UpdateFailed : DialogCategory()
    }

    companion object {
        const val INDEX = 4
    }

    private var binding: AccountFragmentBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AccountFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun showUpdateFailed(update: Unit) {
        showDialog(
            DialogCategory.UpdateFailed
        )
    }

    // Notifying

    override fun doPrimaryAction(category: DialogCategory) {
        @Exhaustive
        when (category) {
            DialogCategory.UpdateFailed -> {
                //DO Nothing
            }
        }
    }

    override fun doSecondaryAction(category: DialogCategory) {
        @Exhaustive
        when (category) {
            DialogCategory.UpdateFailed -> {
                //DO Nothing
            }
        }
    }

    override fun getDialogData(category: DialogCategory) = when (category) {
        DialogCategory.UpdateFailed -> DialogData.Banner.fromIds(
            resources,
            PopupType.WARNING,
            R.string.update_failed
        )
    }


}