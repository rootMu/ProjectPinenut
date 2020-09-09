package com.projects.rootmu.projectpinenut.view

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.*
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.ActivityMainBinding
import com.projects.rootmu.projectpinenut.view.profile.ProfileFragment
import com.projects.rootmu.projectpinenut.viewmodels.AccountsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: AccountsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(navController.graph)
        toolbar.setupWithNavController(navController, appBarConfiguration)

        setSupportActionBar(toolbar)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        viewModel.authenticationState.observe(this) { authenticationState ->
            menu.setGroupVisible(
                R.id.logout,
                authenticationState == AccountsViewModel.AuthenticationState.AUTHENTICATED
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
        val id = item.itemId
        if (id == R.id.action_logout) {
            viewModel.logout()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToProfile() {
        supportFragmentManager.beginTransaction().also { ft ->
            supportFragmentManager.findFragmentByTag("profile")?.let {
                ft.remove(it)
            }
        }
            .setCustomAnimations(
                R.anim.slide_in_up,
                R.anim.slide_out_down,
                R.anim.slide_out_down,
                R.anim.slide_in_up
            )
            .addToBackStack(null)
            .add(R.id.profile_frame, ProfileFragment(), "profile")
            .commit()
    }

}