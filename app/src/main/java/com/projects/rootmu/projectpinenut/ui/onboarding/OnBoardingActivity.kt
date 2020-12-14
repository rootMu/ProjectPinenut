package com.projects.rootmu.projectpinenut.ui.onboarding

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.projects.rootmu.projectpinenut.databinding.ActivityOnboardingBinding
import com.projects.rootmu.projectpinenut.utils.onPageSelected
import com.projects.rootmu.projectpinenut.viewmodels.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_onboarding.*

@AndroidEntryPoint
class OnBoardingActivity : FragmentActivity() {

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

        viewPager.onPageSelected{
            viewModel.pagerPosition.postValue(it)
        }
    }

}
