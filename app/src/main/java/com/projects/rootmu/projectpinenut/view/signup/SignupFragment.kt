package com.projects.rootmu.projectpinenut.view.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.SignupFragmentBinding
import com.projects.rootmu.projectpinenut.utils.AccountAction
import com.projects.rootmu.projectpinenut.utils.autoCleared
import com.projects.rootmu.projectpinenut.viewmodels.AccountsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.login_fragment.*


@AndroidEntryPoint
class SignupFragment : Fragment() {

    private var binding: SignupFragmentBinding by autoCleared()
    private val viewModel: AccountsViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = SignupFragmentBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupBack()
    }

    private fun setupObservers(){
        viewModel.accountsAction.observe(viewLifecycleOwner) {

            when(it.status){

                AccountAction.Status.SIGNUP -> {
                }

                AccountAction.Status.SUCCESS -> {
                    loading.visibility = View.GONE
                    navigateToLogin()
                }

                AccountAction.Status.ERROR -> {
                    loading.visibility = View.GONE
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }

                AccountAction.Status.ATTEMPT -> {
                    loading.visibility = View.VISIBLE
                }

                else -> navigateToLogin()
            }
        }
    }

    private fun setupBack(){
        requireActivity().toolbar.setNavigationOnClickListener {
            viewModel.goToLogin()
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.goToLogin()
            }
        })
    }

    private fun navigateToLogin(){
        findNavController().navigate(
            R.id.action_signupFragment_to_loginFragment
        )
    }
}