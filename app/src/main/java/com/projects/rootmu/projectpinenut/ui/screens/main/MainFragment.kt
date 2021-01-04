package com.projects.rootmu.projectpinenut.ui.screens.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.firebase.auth.FirebaseUser
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.MainFragmentBinding
import com.projects.rootmu.projectpinenut.ui.components.base.BaseFragment
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationCountListener
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationListener
import com.projects.rootmu.projectpinenut.ui.components.tabbedFragmentControl.TabbedFragmentCachedAdapter
import com.projects.rootmu.projectpinenut.ui.components.tabbedFragmentControl.TabbedFragmentContainer
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.models.MainTab
import com.projects.rootmu.projectpinenut.ui.models.PopupType
import com.projects.rootmu.projectpinenut.ui.screens.dialog.NotifyingBaseFragment
import com.projects.rootmu.projectpinenut.ui.util.SharedPreferencesManager
import com.projects.rootmu.projectpinenut.ui.util.general.autoCleared
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.enumDelegate
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.getStringDelegate
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.getValue
import com.projects.rootmu.projectpinenut.ui.util.general.delegates.setValue
import com.projects.rootmu.projectpinenut.ui.util.general.onBackPressed
import com.projects.rootmu.projectpinenut.ui.util.specific.CheckAppStart
import com.projects.rootmu.projectpinenut.ui.viewmodel.AccountsViewModel
import com.projects.rootmu.projectpinenut.ui.viewmodel.main.MainTabsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.main_fragment.*
import javax.annotation.meta.Exhaustive
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : NotifyingBaseFragment<MainFragment.DialogCategory>(),
    TabbedFragmentContainer.Listener, BottomNavigationCountListener, BottomNavigationListener {

    enum class DialogCategory {
        JOB_REQUEST,
        MESSAGE
    }

    @Inject
    lateinit var sharedPreferencesManager: SharedPreferencesManager

    @Inject
    lateinit var checkAppStart: CheckAppStart

    private lateinit var internalAdapter: MainTabAdapter
    private lateinit var adapter: TabbedFragmentContainer.Adapter


    private var binding: MainFragmentBinding by autoCleared()
    private val viewModel: AccountsViewModel by activityViewModels()
    private val mainTabsViewModel: MainTabsViewModel by activityViewModels()

    private var currentTab by enumDelegate<MainTab>(getStringDelegate("currentTab"))

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

        tabs.adapter =
            TabbedFragmentCachedAdapter(MainTabAdapter(childFragmentManager, resources, this).also {
                internalAdapter = it
            }).also {
                this.adapter = it
            }

        tabs.listener = this

        tabs.setupWithMeowBottomNavigation(bottom_navigation)
        bottom_navigation.setOnClickMenuListener(this::onNavigationItemSelected)

        if (isFirstCreation && savedInstanceState == null) {
            val id = mainTabsViewModel.getInitialSelectedTab().ordinal
            bottom_navigation.show(id)
            mainTabsViewModel.setCurrentSelectedTab(id)
        }
    }

    override fun onResume() {
        super.onResume()

        currentTab?.let { changeToTabIfViewActive(it, popToRoot = false) }
    }

    private fun changeToTabIfViewActive(tab: MainTab, popToRoot: Boolean) {
        val index = internalAdapter.getIndexOf(tab)
        tabs?.currentTabIndex = index

        if (popToRoot) {
            (adapter.getItem(index) as? BaseFragment)?.popToRootWhenResumed()
        }
    }

    override fun ancestorAnimatingAway(duration: Long) {
        (getCurrentFragment() as? BaseFragment)?.ancestorAnimatingAway(duration)
    }

    private fun getFragment(position: Int): Fragment? {
        return adapter.getItem(position)
    }

    private fun getCurrentFragment(): Fragment? {
        return getFragment(tabs.currentTabIndex!!)
    }

    fun goTo(tab: MainTab) {
        currentTab = tab
        changeToTabIfViewActive(tab, popToRoot = true)
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

    /** TabbedFragmentContainer.Listener **/

    override fun onTabChanged(
        oldIndex: Int?,
        newIndex: Int,
        oldFragment: Fragment?,
        newFragment: Fragment
    ) {
        mainTabsViewModel.setCurrentSelectedTab(newIndex)
    }

    /** BottomNavigationCountListener **/

    override fun updateBottomNavigationCount(id: Int, count: String) {
        bottom_navigation.setCount(id, count)
    }

    /** BottomNavigationListener **/

    override fun onNavigationItemSelected(model: MeowBottomNavigation.Model) {
        val isNavigating = if (tabs.currentTabIndex != model.id) {
            tabs.currentTabIndex = model.id
            val newTab = internalAdapter.getTab(model.id)
            currentTab = newTab

            mainTabsViewModel.setCurrentSelectedTab(model.id)
            true
        } else {
            false
        }

        (adapter.getItem(model.id) as? OnTabIconClickedListener)?.onTabIconClicked(
            isNavigating
        )
    }

    override fun onNavigationItemReSelected(model: MeowBottomNavigation.Model) {
        //TODO handle re selection if wanted
    }
}