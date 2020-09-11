package com.projects.rootmu.projectpinenut.view.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.OnboardingFragmentBinding
import com.projects.rootmu.projectpinenut.utils.autoCleared
import com.projects.rootmu.projectpinenut.viewmodels.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {

    private var binding: OnboardingFragmentBinding by autoCleared()
    private val viewModel: OnBoardingViewModel by viewModels()

    companion object {
        private const val POSITION = "position"

        fun newInstance(position: Int): OnBoardingFragment {
            return OnBoardingFragment().apply {
                arguments = Bundle().apply {
                    putInt(POSITION, position)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(requireArguments()) {
            viewModel.apply {
                val position = getInt(POSITION)
                title.postValue(resources.getStringArray( R.array.onboarding_title )[position])
                description.postValue(resources.getStringArray( R.array.onboarding_description )[position])
                image.postValue(when(position){
                    0 -> R.raw.lottie_delivery_boy_bumpy_ride
                    1 -> R.raw.lottie_developer
                    else -> R.raw.lottie_girl_with_a_notebook
                })
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OnboardingFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = viewLifecycleOwner
            viewModel = this@OnBoardingFragment.viewModel.apply {
                image.observe(viewLifecycleOwner) {
                    binding.imageOnboarding.setAnimation(it)
                }
            }
        }
        return binding.root
    }
}