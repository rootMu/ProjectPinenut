package com.projects.rootmu.projectpinenut.ui.screens.account

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.projects.rootmu.projectpinenut.databinding.AccountFragmentBinding
import com.projects.rootmu.projectpinenut.ui.components.base.BaseFragment
import com.projects.rootmu.projectpinenut.ui.components.listeners.navigateTo
import com.projects.rootmu.projectpinenut.ui.screens.login.LoginFragment
import com.projects.rootmu.projectpinenut.ui.util.general.autoCleared
import com.projects.rootmu.projectpinenut.ui.viewmodel.account.AccountsViewModel
import com.projects.rootmu.projectpinenut.util.general.TargetedObserver

class AccountFragment : BaseFragment() {

    override val observers: List<TargetedObserver<*>>
        get() = super.observers + listOf(TargetedObserver({ viewModel.isLoggedIn }) {
            if (!it) {
                navigateTo(LoginFragment())
            }
        }, TargetedObserver({ viewModel.user }) {
                binding.account = it
            }
        )

    private var binding: AccountFragmentBinding by autoCleared()
    private val viewModel: AccountsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AccountFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

}