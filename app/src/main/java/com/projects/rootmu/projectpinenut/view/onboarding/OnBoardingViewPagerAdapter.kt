package com.projects.rootmu.projectpinenut.view.onboarding

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.projects.rootmu.projectpinenut.R

class OnBoardingViewPagerAdapter(manager: FragmentManager,
                                 private val context : Context, private val numItems: Int) :
    FragmentPagerAdapter(manager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    companion object {
        @JvmStatic fun newInstance(manager: FragmentManager,
                        context : Context, num: Int): OnBoardingViewPagerAdapter{
            return OnBoardingViewPagerAdapter(manager, context, num)
        }
    }


    // Returns total number of pages
    override fun getCount(): Int {
        return numItems
    }

    // Returns the fragment to display for that page
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> OnBoardingFragment.newInstance(
                context.resources.getString(R.string.title_onboarding_1),
                context.resources.getString(R.string.description_onboarding_1),
                R.raw.lottie_delivery_boy_bumpy_ride
            )
            1 -> OnBoardingFragment.newInstance(
                context.resources.getString(R.string.title_onboarding_2),
                context.resources.getString(R.string.description_onboarding_2),
                R.raw.lottie_developer
            )
            2 -> OnBoardingFragment.newInstance(
                context.resources.getString(R.string.title_onboarding_3),
                context.resources.getString(R.string.description_onboarding_3),
                R.raw.lottie_girl_with_a_notebook
            )
            else -> null
        }!!
    }

    // Returns the page title for the top indicator
    override fun getPageTitle(position: Int): CharSequence? {
        return "Page $position"
    }

}