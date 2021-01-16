package com.projects.rootmu.projectpinenut.ui.components.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.FrameLayout

open class FragmentContainer @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
): FrameLayout(context, attrs, defStyleAttr), ViewGroup.OnHierarchyChangeListener {

    private var insets: WindowInsets? = null

    init {
        @Suppress("LeakingThis")
        setOnHierarchyChangeListener(this)
    }

    override fun onChildViewRemoved(parent: View, child: View) {
        // Ignore
    }

    override fun onChildViewAdded(parent: View, child: View) {
        insets?.let { child.dispatchApplyWindowInsets(it) }
    }

    override fun onApplyWindowInsets(insets: WindowInsets): WindowInsets =
        insets.also { this.insets = it }
}