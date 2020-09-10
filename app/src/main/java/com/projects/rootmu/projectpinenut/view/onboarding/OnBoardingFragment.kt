package com.projects.rootmu.projectpinenut.view.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.projects.rootmu.projectpinenut.databinding.OnboardingFragmentBinding
import com.projects.rootmu.projectpinenut.utils.autoCleared
import com.projects.rootmu.projectpinenut.viewmodels.OnBoardingViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingFragment : Fragment() {

    private var binding: OnboardingFragmentBinding by autoCleared()
    private val viewModel: OnBoardingViewModel by viewModels()

    companion object {
        private const val ARG_PARAM1 = "param1"
        private const val ARG_PARAM2 = "param2"
        private const val ARG_PARAM3 = "param3"
        fun newInstance(
            title: String,
            description: String,
            imageResource: Int
        ): OnBoardingFragment {
            val fragment =
                OnBoardingFragment()
            val args = Bundle()
            args.putString(
                ARG_PARAM1,
                title
            )
            args.putString(
                ARG_PARAM2,
                description
            )
            args.putInt(
                ARG_PARAM3,
                imageResource
            )
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            viewModel.apply{
                title.postValue(requireArguments().getString(ARG_PARAM1, ""))

                description.postValue(requireArguments().getString(ARG_PARAM2, ""))
                image.postValue(requireArguments().getInt(ARG_PARAM3))
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = OnboardingFragmentBinding.inflate(inflater, container, false)

        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        viewModel.image.observe(viewLifecycleOwner) {
            binding.imageOnboarding.setAnimation(it)
        }

        return binding.root
    }


}