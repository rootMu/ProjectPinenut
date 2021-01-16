package com.projects.rootmu.projectpinenut.ui.screens.login

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.LoginFragmentBinding
import com.projects.rootmu.projectpinenut.repository.account.AccountRepository
import com.projects.rootmu.projectpinenut.ui.components.listeners.navigateTo
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.models.PopupType
import com.projects.rootmu.projectpinenut.ui.screens.account.AccountFragment
import com.projects.rootmu.projectpinenut.ui.screens.dialog.NotifyingBaseFragment
import com.projects.rootmu.projectpinenut.ui.screens.dialog.showDialog
import com.projects.rootmu.projectpinenut.ui.util.general.autoCleared
import com.projects.rootmu.projectpinenut.ui.util.general.onBackPressed
import com.projects.rootmu.projectpinenut.ui.viewmodel.account.AccountsViewModel
import com.projects.rootmu.projectpinenut.util.general.TargetedObserver
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
import javax.annotation.meta.Exhaustive

@AndroidEntryPoint
class LoginFragment : NotifyingBaseFragment<LoginFragment.DialogCategory>() {

    sealed class DialogCategory : Serializable {
        object InvalidPassword : DialogCategory()
        object NoUserFound : DialogCategory()
        object PasswordUpdateRequired : DialogCategory()
    }

    override val observers: List<TargetedObserver<*>>
        get() = super.observers + listOf(TargetedObserver({ viewModel.loginState }) {

            when (it?.authenticationState) {
                AccountRepository.AuthenticationState.AUTHENTICATED -> {
                    if (it.userToken?.passwordUpdateRequired == true) {
                        showDialog(DialogCategory.PasswordUpdateRequired)
                    } else {
                        navigateTo(AccountFragment())
                    }
                }
                AccountRepository.AuthenticationState.INVALID_AUTHENTICATION -> {
                    showDialog(DialogCategory.InvalidPassword)
                }
                AccountRepository.AuthenticationState.UNAUTHENTICATED -> {
                    showDialog(DialogCategory.NoUserFound)
                }
            }
        })

    private var binding: LoginFragmentBinding by autoCleared()
    private val viewModel: AccountsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        binding.loginDetails.lifecycleOwner = viewLifecycleOwner

        onBackPressed { /**Do Nothing**/ }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }


    // Notifying

    override fun doPrimaryAction(category: DialogCategory) {
        @Exhaustive
        when (category) {
            DialogCategory.InvalidPassword -> {
                //DO Nothing
            }
            DialogCategory.NoUserFound -> {
                //DO Nothing
            }
            DialogCategory.PasswordUpdateRequired -> {
                //DO Nothing
            }
        }
    }

    override fun doSecondaryAction(category: DialogCategory) {
        @Exhaustive
        when (category) {
            DialogCategory.InvalidPassword -> {
                //DO Nothing
            }
            DialogCategory.NoUserFound -> {
                //DO Nothing
            }
            DialogCategory.PasswordUpdateRequired -> {
                //DO Nothing
            }
        }
    }

    override fun getDialogData(category: DialogCategory) = when (category) {
        DialogCategory.InvalidPassword -> DialogData.Banner.fromIds(
            resources,
            PopupType.ERROR,
            R.string.invalid_password
        )
        DialogCategory.NoUserFound -> DialogData.Banner.fromIds(
            resources,
            PopupType.ERROR,
            R.string.no_user_found
        )
        DialogCategory.PasswordUpdateRequired -> DialogData.Input.fromIds(
            resources,
            PopupType.WARNING,
            R.string.password_outdated_title,
            R.string.password_outdated_message,
            R.string.ok,
            null,
            null,
            R.string.new_password,
            PasswordTransformationMethod.getInstance(),
            null
        )
    }
}
