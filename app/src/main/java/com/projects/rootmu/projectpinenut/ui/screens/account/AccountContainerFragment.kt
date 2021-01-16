package com.projects.rootmu.projectpinenut.ui.screens.account

import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.projects.rootmu.projectpinenut.ui.components.base.ContainerFragment
import com.projects.rootmu.projectpinenut.ui.screens.login.LoginFragment
import com.projects.rootmu.projectpinenut.ui.viewmodel.account.AccountsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountContainerFragment : ContainerFragment() {

    private val viewModel: AccountsViewModel by viewModels()

    override fun getInitialFragment(): Fragment = if(viewModel.isLoggedIn()) AccountFragment() else LoginFragment()
}