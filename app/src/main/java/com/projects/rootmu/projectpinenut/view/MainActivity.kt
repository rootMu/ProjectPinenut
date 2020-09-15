package com.projects.rootmu.projectpinenut.view

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.ActivityMainBinding
import com.projects.rootmu.projectpinenut.utils.AppStart
import com.projects.rootmu.projectpinenut.utils.CheckAppStart
import com.projects.rootmu.projectpinenut.view.onboarding.OnBoardingActivity
import com.projects.rootmu.projectpinenut.viewmodels.AccountsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    companion object {
        const val PROFILE = "profile"
    }

    private val viewModel: AccountsViewModel by viewModels()
    private lateinit var navController: NavController

    @Inject
    lateinit var checkAppStart: CheckAppStart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainFragment, R.id.profileFragment,
                R.id.loginFragment
            )
        )
        toolbar.setupWithNavController(navController, appBarConfiguration)

        setSupportActionBar(toolbar)

        when (checkAppStart.status) {
            AppStart.UPDATE -> {
                //TODO do some sort of whats new dialog box
            }
            AppStart.FIRST_TIME -> {
                startActivity(Intent(applicationContext, OnBoardingActivity::class.java))
            }
            else -> {
                //TODO normal stuff? though it would need to be considered that this will not be called AFTER the onboarding
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        viewModel.authenticationState.observe(this) { authenticationState ->
            menu.setGroupVisible(
                R.id.logout,
                authenticationState == AccountsViewModel.AuthenticationState.AUTHENTICATED
            )
            menu.setGroupVisible(
                R.id.profile,
                authenticationState == AccountsViewModel.AuthenticationState.AUTHENTICATED
            )
        }
        viewModel.profileVisible.observe(this) {
            menu.setGroupVisible(
                R.id.profile,
                !it
            )
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        when (item.itemId) {
            R.id.action_logout -> viewModel.logout()
            R.id.action_profile -> navigateToProfile()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToProfile() {
        navController.navigate(
            R.id.action_mainFragment_to_profileFragment
        )
        viewModel.setProfileVisibility(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}