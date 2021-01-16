package com.projects.rootmu.projectpinenut.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentManager
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.listeners.MainTabNavigationListener
import com.projects.rootmu.projectpinenut.ui.models.MainTab
import com.projects.rootmu.projectpinenut.ui.screens.dialog.DialogActivity
import com.projects.rootmu.projectpinenut.ui.screens.main.MainFragment
import com.projects.rootmu.projectpinenut.ui.screens.onboarding.OnBoardingActivity
import com.projects.rootmu.projectpinenut.ui.util.specific.AppStart
import com.projects.rootmu.projectpinenut.ui.util.specific.CheckAppStart
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : DialogActivity(),
    MainTabNavigationListener {

    companion object {
        const val MAIN_TAG = "main"
    }

    @Inject
    lateinit var checkAppStart: CheckAppStart

    private val mainFragment
        get() = supportFragmentManager.findFragmentByTag(MAIN_TAG) as? MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        if (savedInstanceState == null) {
            addFragment(MainFragment(), tag = MAIN_TAG)
        }

        when (checkAppStart.status) {
            AppStart.UPDATE -> {
                //TODO show whats new
            }
            AppStart.FIRST_TIME -> {
                startActivity(Intent(applicationContext, OnBoardingActivity::class.java))
            }
            else -> {
                //TODO normal stuff? though it is worth nothing this will not be called AFTER UPDATE or FIRST_TIME
                //TODO think about maybe having a callback for either of those
            }
        }
    }

    /** MainTabNavigationListener **/

    override fun goToHome() {
        mainFragment?.goTo(MainTab.HOME)
    }

    override fun goToMessages() {
        mainFragment?.goTo(MainTab.MESSAGES)
    }

    override fun goToJobs() {
        mainFragment?.goTo(MainTab.JOBS)
    }

    override fun goToNotifications() {
        mainFragment?.goTo(MainTab.NOTIFICATIONS)
    }

    override fun goToAccount() {
        mainFragment?.goTo(MainTab.ACCOUNT)
    }

//
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        menuInflater.inflate(R.menu.menu_main, menu)
//        viewModel.authenticationState.observe(this) { authenticationState ->
//            menu.setGroupVisible(
//                R.id.logout,
//                authenticationState == AccountsViewModel.AuthenticationState.AUTHENTICATED
//            )
//        }
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        // Handle action bar item clicks here.
//        when (item.itemId) {
//            R.id.action_logout -> viewModel.logout()
//            R.id.action_profile -> navigateToProfile()
//        }
//        val id = item.itemId
//        if (id == R.id.action_logout) {
//            viewModel.logout()
//        }
//        return super.onOptionsItemSelected(item)
//    }
//
//    private fun navigateToProfile() {
//        supportFragmentManager.beginTransaction().also { ft ->
//            supportFragmentManager.findFragmentByTag("profile")?.let {
//                ft.remove(it)
//            }
//        }
//            .setCustomAnimations(
//                R.anim.slide_in_up,
//                R.anim.slide_out_down,
//                R.anim.slide_out_down,
//                R.anim.slide_in_up
//            )
//            .addToBackStack(null)
//            .add(R.id.profile_frame, ProfileFragment(), "profile")
//            .commit()
//    }

}