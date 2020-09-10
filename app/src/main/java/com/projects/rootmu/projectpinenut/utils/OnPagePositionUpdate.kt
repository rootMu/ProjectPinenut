package com.projects.rootmu.projectpinenut.utils

import androidx.viewpager.widget.ViewPager

fun ViewPager.onPagePositionUpdate(invoke: (Int) -> Unit = {}) = addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

    override fun onPageScrollStateChanged(state: Int) {}

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageSelected(position: Int) {
        invoke.invoke(position)
    }

})
