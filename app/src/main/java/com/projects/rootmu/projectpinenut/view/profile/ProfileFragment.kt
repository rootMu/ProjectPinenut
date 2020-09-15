package com.projects.rootmu.projectpinenut.view.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.ProfileFragmentBinding
import com.projects.rootmu.projectpinenut.utils.autoCleared
import com.projects.rootmu.projectpinenut.utils.onAnimationEnd
import com.projects.rootmu.projectpinenut.utils.onBackPressed
import com.projects.rootmu.projectpinenut.view.MainActivity
import com.projects.rootmu.projectpinenut.viewmodels.AccountsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private var binding: ProfileFragmentBinding by autoCleared()
    private val viewModel: AccountsViewModel by activityViewModels()

    private var hasLoggedOut = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.fragment = this
        binding.lifecycleOwner = viewLifecycleOwner

        FirebaseAuth.getInstance().currentUser?.let {
            binding.user = it
        }

        retainInstance = true

        setupObservers()

        return binding.root
    }

    private fun setupObservers(){
        viewModel.authenticationState.observe(viewLifecycleOwner) { authenticationState ->
            if(authenticationState == AccountsViewModel.AuthenticationState.UNAUTHENTICATED){
                hasLoggedOut = true

                findNavController().navigate(
                    R.id.action_profileFragment_to_loginFragment
                )
            }
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.setProfileVisibility(hasLoggedOut)
    }
}