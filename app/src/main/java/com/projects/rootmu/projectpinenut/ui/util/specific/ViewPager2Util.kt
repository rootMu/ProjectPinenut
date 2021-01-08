package com.projects.rootmu.projectpinenut.ui.util.specific

import androidx.viewpager2.widget.ViewPager2
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.projects.rootmu.projectpinenut.ui.screens.main.BottomNavAdapter

fun ViewPager2.onPageSelected(invoke: (Int) -> Unit = {}) =
    registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            invoke.invoke(position)
        }
    })

fun ViewPager2.setupWithMeowBottomNavigation(
    bottomNavigationView: MeowBottomNavigation,
    onChangeTab: (() -> Unit)? = null
) {

    isUserInputEnabled = false

    (adapter as? BottomNavAdapter)?.apply {
        for (i in 0 until itemCount) {
            val icon = getTabIconResId(i)
            bottomNavigationView.add(MeowBottomNavigation.Model(i, icon))
        }
    }

    bottomNavigationView.setOnShowListener { model ->
        val newIndex = bottomNavigationView.models.let {
            var index: Int? = null
            for (i in 0 until it.count()) {
                if (it[i].id == model.id) {
                    index = i
                    break
                }
            }
            index
        }

        if (newIndex != currentItem) {
            onChangeTab?.invoke()
        }

        if (newIndex != null && newIndex != currentItem) {
            setCurrentItem(newIndex, true)
        }
    }

    onPageSelected {
        bottomNavigationView.show(it, false)
    }

}
