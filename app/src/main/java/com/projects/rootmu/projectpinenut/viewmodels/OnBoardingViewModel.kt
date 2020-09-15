package com.projects.rootmu.projectpinenut.viewmodels

import android.view.View
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.viewpager.widget.ViewPager
import com.projects.rootmu.projectpinenut.utils.CheckAppStart

class OnBoardingViewModel @ViewModelInject constructor(private val checkAppStart: CheckAppStart) :
    ViewModel() {

    companion object {
        const val NUM_ITEMS = 3
    }

    val image: MutableLiveData<Int> = MutableLiveData()
    val title: MutableLiveData<String> = MutableLiveData()
    val description: MutableLiveData<String> = MutableLiveData()

    val pagerPosition: MutableLiveData<Int> = MutableLiveData(0)
    val nextVisibility = Transformations.map(pagerPosition) {
        if (it + 1 > NUM_ITEMS - 1) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }
    val backVisibility = Transformations.map(pagerPosition) {
        if (it + 1 == 1) {
            View.INVISIBLE
        } else {
            View.VISIBLE
        }
    }

    fun finishOnBoarding() {
        checkAppStart.updateLastAppVersion()
    }

}