package com.projects.rootmu.projectpinenut.ui.screens.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.LoginFragmentBinding
import com.projects.rootmu.projectpinenut.ui.util.general.autoCleared
import com.projects.rootmu.projectpinenut.ui.util.general.onBackPressed
import com.projects.rootmu.projectpinenut.ui.viewmodel.AccountsViewModel
import com.projects.rootmu.projectpinenut.ui.viewmodel.AccountsViewModel.Companion.SIGN_IN_REQUEST_CODE
import com.projects.rootmu.projectpinenut.ui.viewmodel.AccountsViewModel.Companion.USER
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var binding: LoginFragmentBinding by autoCleared()
    private val viewModel: AccountsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = LoginFragmentBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        onBackPressed { /**Do Nothing**/ }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
    }

    private fun setupObservers() {
        viewModel.providers.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                startActivityForResult(
                    AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(it)
                        .build(),
                    SIGN_IN_REQUEST_CODE
                )
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SIGN_IN_REQUEST_CODE) {
            val response = IdpResponse.fromResultIntent(data)
            if (resultCode == Activity.RESULT_OK) {
                // User successfully signed in.
                val user = FirebaseAuth.getInstance().currentUser
                navigateToMain(user)
                Timber.i("Successfully signed in user ${user?.displayName}!")
            } else {
                // Sign in failed. If response is null, the user canceled the
                // sign-in flow using the back button. Otherwise, check
                // the error code and handle the error.
                response?.let{
                    Timber.i("Sign in unsuccessful ${it.error?.errorCode}!")
                }
            }
        }
    }


    private fun navigateToMain(user: FirebaseUser? = null) {
        val args = user?.let {
            bundleOf(USER to user)
        }
        findNavController().navigate(
            R.id.action_loginFragment_to_mainFragment,
            args
        )
    }
}
