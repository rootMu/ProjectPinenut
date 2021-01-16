package com.projects.rootmu.projectpinenut.ui.screens.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.projects.rootmu.projectpinenut.databinding.FragmentDialogInputBinding
import com.projects.rootmu.projectpinenut.ui.components.base.BaseDialogFragment
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.models.DialogResult
import com.projects.rootmu.projectpinenut.ui.util.general.autoCleared
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.getLongDelegate
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.getValue
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.orThrow
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.setValue
import com.projects.rootmu.projectpinenut.ui.viewmodel.account.AccountsViewModel
import com.projects.rootmu.projectpinenut.ui.viewmodel.dialog.DialogInstanceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_dialog_input.*

@AndroidEntryPoint
class InputDialogFragment : BaseDialogFragment() {

    companion object {
        fun instantiate(id: Long): InputDialogFragment {
            return InputDialogFragment().apply {
                this.id = id
            }
        }
    }

//    enum class InputType(flagName: String) {
//        NONE("none"),
//        TEXT("text"),
//        TEXTCAPCHARACTERS("textCapCharacters"),
//        TEXTCAPWORDS("textCapWords"),
//        TEXTCAPSENTENCES("textCapSentences"),
//        TEXTAUTPCORRECT("textAutoCorrect"),
//        TEXTAUTOCOMPLETE("textAutoComplete"),
//        TEXTMULTILINE("textMultiLine"),
//        TEXTIMEMUTILLINE("textImeMultiLine"),
//        TEXTNOSUGGESTIONS("textNoSuggestions"),
//        TEXTURI("textUri"),
//        TEXTEMAILADDRESS("textEmailAddress"),
//        TEXTEMAILSUBJECT("textEmailSubject"),
//        TEXTSHORTMESSAGE("textShortMessage"),
//        TEXTLONGMESSAGE("textLongMessage"),
//        TEXTPERSONNAME("textPersonName"),
//        TEXTPOSTALADDRESS("textPostalAddress"),
//        TEXTPASSWORD("textPassword"),
//        TEXTVISIBLEPASSWORD("textVisiblePassword"),
//        TEXTWEBEDITTEXT("textWebEditText"),
//        TEXTFILTER("textFilter"),
//        TEXTPHONETIC("textPhonetic"),
//        TEXTWEBEMAILADDRESS("textWebEmailAddress"),
//        TEXTWEBPASSWORD("textWebPassword"),
//        NUMBER("number"),
//        NUMBERSIGNED("numberSigned"),
//        NUMBERDECIMAL("numberDecimal"),
//        NUMBERPASSWORD("numberPassword"),
//        PHONE("phone"),
//        DATETIME("datetime"),
//        DATE("date"),
//        TIME("time")
//    }

    override var id by getLongDelegate("dialogId").orThrow()
        private set

    private var binding: FragmentDialogInputBinding by autoCleared()

    private val dialogInstanceViewModel: DialogInstanceViewModel by viewModels()
    private val accountViewModel: AccountsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentDialogInputBinding.inflate(inflater, container, false).let {
        binding = it
        binding.lifecycleOwner = viewLifecycleOwner
        return it.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogInstanceViewModel.getTypedData<DialogData.Input>(id)?.let {
            setDialogData(it)
        }

        primary_cta.setOnClickListener {
            doSubmit()
        }

        secondary_cta.setOnClickListener {
            dismiss()
            dialogInstanceViewModel.setResult(DialogResult.Secondary)
        }

    }

    private fun doSubmit() {
        dismiss()
        dialogInstanceViewModel.setResult(DialogResult.Primary(txt_input.text?.toString()))
    }

    private fun setDialogData(dialogData: DialogData.Input) {
        binding.data = dialogData
    }

}
