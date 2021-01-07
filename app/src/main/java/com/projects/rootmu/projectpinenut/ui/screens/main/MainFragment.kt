package com.projects.rootmu.projectpinenut.ui.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseUser
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.MainFragmentBinding
import com.projects.rootmu.projectpinenut.ui.components.base.BaseFragment
import com.projects.rootmu.projectpinenut.ui.components.base.ContainerFragment
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationListener
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationReselectedListener
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.models.MainTab
import com.projects.rootmu.projectpinenut.ui.models.PopupType
import com.projects.rootmu.projectpinenut.ui.screens.dialog.NotifyingBaseFragment
import com.projects.rootmu.projectpinenut.ui.util.SharedPreferencesManager
import com.projects.rootmu.projectpinenut.ui.util.general.autoCleared
import com.projects.rootmu.projectpinenut.ui.util.general.onBackPressed
import com.projects.rootmu.projectpinenut.ui.util.specific.CheckAppStart
import com.projects.rootmu.projectpinenut.ui.util.specific.setupWithMeowBottomNavigation
import com.projects.rootmu.projectpinenut.ui.viewmodel.AccountsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_onboarding.*
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.main_fragment.viewPager
import javax.annotation.meta.Exhaustive
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : NotifyingBaseFragment<MainFragment.DialogCategory>(),
    BottomNavigationListener {

    enum class DialogCategory {
        JOB_REQUEST,
        MESSAGE
    }

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    @Inject
    lateinit var checkAppStart: CheckAppStart

    private lateinit var adapter: BottomNavAdapter

    private var binding: MainFragmentBinding by autoCleared()
    private val viewModel: AccountsViewModel by activityViewModels()

    private var user: FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        onBackPressed {
            viewModel.logout()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val isFirstCreation = !hasCreatedView

        super.onViewCreated(view, savedInstanceState)
        //Pass the User in here as initially authentication hasn't taken place

//        with(arguments?.get(USER) as FirebaseUser?) {
//
//            val currentUser = this ?: FirebaseAuth.getInstance().currentUser
//
//            currentUser?.let { user ->
//                user.displayName?.let {
//                    sharedPreferencesManager.displayName = it
//                }
//
//                user.email?.let {
//                    sharedPreferencesManager.email = it
//                }
//            }
//
//            this@MainFragment.user = this
//        }
//
//        setupObservers()
//
//        if(sharedPreferencesManager.launchTutorial)
//            handleTutorial()


        viewPager.apply {
            adapter = BottomNavAdapter.newInstance(this@MainFragment).also {
                this@MainFragment.adapter = it
            }
            setupWithMeowBottomNavigation(bottom_navigation) {
                (getCurrentFragment() as? ContainerFragment)?.popToParent()
            }
        }

        mainTabsViewModel.setBottomNavigationListener(this)

        if (isFirstCreation && savedInstanceState == null) {
            val id = mainTabsViewModel.getInitialSelectedTab().ordinal
            viewPager.setCurrentItem(id, false)
            bottom_navigation.show(id, false)
            mainTabsViewModel.setCurrentSelectedTab(id)
        }
    }

    private fun changeToTabIfViewActive(tab: MainTab) {
        val index = adapter.getIndexOf(tab)
        viewPager.setCurrentItem(index, true)
    }

    override fun ancestorAnimatingAway(duration: Long) {
        (getCurrentFragment() as? BaseFragment)?.ancestorAnimatingAway(duration)
    }

    private fun getFragment(position: Int): Fragment {
        return adapter.getItem(position)
    }

    private fun getCurrentFragment(): Fragment {
        return getFragment(viewPager.currentItem)
    }

    fun goTo(tab: MainTab) {
        changeToTabIfViewActive(tab)
    }

    private fun setupObservers() {

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

    private fun handleTutorial() {

    }

    /** Notifying **/

    override fun doPrimaryAction(category: DialogCategory) {
        @Exhaustive
        when (category) {
            DialogCategory.JOB_REQUEST -> TODO("Open Jobs")
            DialogCategory.MESSAGE -> TODO("Show Message")
        }
    }

    override fun doSecondaryAction(category: DialogCategory) {
        @Exhaustive
        when (category) {
            DialogCategory.JOB_REQUEST -> {
                //Do Nothing
            }
            DialogCategory.MESSAGE -> {
                //Do Nothing
            }
        }
    }

    override fun getDialogData(category: DialogCategory): DialogData = when (category) {
        DialogCategory.JOB_REQUEST -> DialogData.Banner.fromIds(
            resources,
            PopupType.INFO,
            R.string.job_request
        )
        DialogCategory.MESSAGE -> DialogData.Banner.fromIds(
            resources,
            PopupType.INFO,
            R.string.message
        )
    }

    /** BottomNavigationListener **/

    override fun updateBottomNavigationCount(id: Int, count: String?) {
        count?.let {
            bottom_navigation.setCount(id, it)
        } ?: bottom_navigation.clearCount(id)
    }

    override fun setBottomNavigationReselectedListener(listener: BottomNavigationReselectedListener) {
        bottom_navigation.setOnReselectListener(listener::onNavigationItemReselected)
    }

}