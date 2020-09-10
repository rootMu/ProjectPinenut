package com.projects.rootmu.projectpinenut.view.onboarding

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.projects.rootmu.projectpinenut.databinding.ActivityOnboardingBinding
import com.projects.rootmu.projectpinenut.utils.onPagePositionUpdate
import com.projects.rootmu.projectpinenut.viewmodels.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_onboarding.*

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    private val viewModel: OnBoardingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityOnboardingBinding = ActivityOnboardingBinding.inflate(layoutInflater)
        binding.activity = this
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        viewPager.onPagePositionUpdate{
            viewModel.pagerPosition.postValue(it)
        }
    }

}
