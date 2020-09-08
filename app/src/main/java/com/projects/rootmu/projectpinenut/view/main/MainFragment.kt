package com.projects.rootmu.projectpinenut.view.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseUser
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.MainFragmentBinding
import com.projects.rootmu.projectpinenut.utils.autoCleared
import com.projects.rootmu.projectpinenut.utils.onBackPressed
import com.projects.rootmu.projectpinenut.viewmodels.AccountsViewModel
import com.projects.rootmu.projectpinenut.viewmodels.AccountsViewModel.Companion.USER
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var binding: MainFragmentBinding by autoCleared()
    private val viewModel: AccountsViewModel by activityViewModels()
    private var user: FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        onBackPressed{
            viewModel.logout()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Pass the User in here as initially authentication hasn't taken place
        val user = arguments?.get(USER) as FirebaseUser?

        setupObservers(user)
    }

    private fun setupObservers(user: FirebaseUser? = null) {
        this.user = user
        viewModel.authenticationState.observe(viewLifecycleOwner)
        { authenticationState ->
            when (authenticationState) {
                AccountsViewModel.AuthenticationState.AUTHENTICATED -> {
                    //reset temp user variable so logout can happen
                    this.user = null
                }
                else -> {
                    if (this.user == null)
                        navigateToLogin()
                }
            }
        }
    }

    private fun navigateToLogin() {
        findNavController().navigate(
            R.id.action_mainFragment_to_loginFragment
        )
    }

}