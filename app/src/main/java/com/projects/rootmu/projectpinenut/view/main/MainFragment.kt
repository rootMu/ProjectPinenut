package com.projects.rootmu.projectpinenut.view.main

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.MainFragmentBinding
import com.projects.rootmu.projectpinenut.utils.CheckAppStart
import com.projects.rootmu.projectpinenut.utils.SharedPreferencesManager
import com.projects.rootmu.projectpinenut.utils.autoCleared
import com.projects.rootmu.projectpinenut.utils.onBackPressed
import com.projects.rootmu.projectpinenut.viewmodels.AccountsViewModel
import com.projects.rootmu.projectpinenut.viewmodels.AccountsViewModel.Companion.USER
import dagger.hilt.android.AndroidEntryPoint
import smartdevelop.ir.eram.showcaseviewlib.GuideView
import smartdevelop.ir.eram.showcaseviewlib.config.DismissType
import smartdevelop.ir.eram.showcaseviewlib.config.Gravity
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class MainFragment : Fragment() {

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager
    @Inject
    lateinit var checkAppStart: CheckAppStart

    private var binding: MainFragmentBinding by autoCleared()
    private val viewModel: AccountsViewModel by activityViewModels()
    private var user: FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        onBackPressed {
            viewModel.logout()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Pass the User in here as initially authentication hasn't taken place

        with(arguments?.get(USER) as FirebaseUser?) {

            val currentUser = this ?: FirebaseAuth.getInstance().currentUser

            currentUser?.let { user ->
                user.displayName?.let {
                    sharedPreferencesManager.displayName = it
                }

                user.email?.let {
                    sharedPreferencesManager.email = it
                }
            }

            this@MainFragment.user = this
        }

        setupObservers()
    }

    private fun setupObservers() {

        viewModel.authenticationState.observe(viewLifecycleOwner)
        { authenticationState ->
            when (authenticationState) {
                AccountsViewModel.AuthenticationState.AUTHENTICATED -> {
                    //reset temp user variable so logout can happen
                    this.user = null


                    if(sharedPreferencesManager.launchTutorial)
                        handleTutorial()

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

    private fun handleTutorial(){

        AlertDialog.Builder(requireContext()).setMessage("Would you like a quick tour of the application?")
            .setPositiveButton("Sure!") { _, _ ->
                startTour()
            }
            .setNegativeButton("No Thanks!") { _, _ ->

            }
            .create().also{
             //   sharedPreferencesManager.launchTutorial = false
            }.show()
    }

    private fun startTour(){

        GuideView.Builder(requireActivity())
            .setTitle("What Sort of User Are you?")
            .setContentText("As a new User you need to\n decide on how you will be using OddJobs\n " +
                    "Do you have an Odd Job you need completed?\n Or do you fancy undertaking some OddJobs?\n" +
                    "Click the profile Icon to set up your account")
            .setGravity(Gravity.center)
//            .setDismissType(DismissType.outside) //optional - default dismissible by TargetView
            .setGuideListener {
                Timber.d("You Clicked Profile Button")
            }
            .build()
            .show()
    }

}