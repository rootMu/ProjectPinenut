package com.projects.rootmu.projectpinenut.utils

import androidx.viewpager2.widget.ViewPager2

fun ViewPager2.onPageSelected(invoke: (Int) -> Unit = {}) = registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
        super.onPageSelected(position)
        invoke.invoke(position)
    }
})
