package com.projects.rootmu.projectpinenut.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingViewPagerAdapter <T : FragmentActivity> (activity : T, private val numItems: Int) :
    FragmentStateAdapter(activity) {

    companion object {
        @JvmStatic fun <T : FragmentActivity> newInstance(activity : T , num: Int): OnBoardingViewPagerAdapter<T>{
            return OnBoardingViewPagerAdapter(activity, num)
        }
    }

    override fun getItemCount() = numItems

    override fun createFragment(position: Int): Fragment {
        return OnBoardingFragment.newInstance(position)
    }

}