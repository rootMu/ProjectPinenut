package com.projects.rootmu.projectpinenut.ui.components.tabbedFragmentControl

import com.projects.rootmu.projectpinenut.ui.components.base.BaseFragment

abstract class TabFragment: BaseFragment() {

    var isShowing = false
        private set

    open fun onShow() {
    }

    open fun onHide() {
    }

    fun show() {
        isShowing = true

        if (isResumed) {
            onShow()
        }
    }

    fun hide() {
        isShowing = false

        if (isResumed) {
            onHide()
        }
    }

    override fun onResume() {
        super.onResume()

        if (isShowing) {
            onShow()
        }
    }

    override fun onPause() {
        if (isShowing) {
            onHide()
        }

        super.onPause()
    }
}