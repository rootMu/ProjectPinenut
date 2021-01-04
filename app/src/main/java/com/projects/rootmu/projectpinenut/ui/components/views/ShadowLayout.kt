package com.projects.rootmu.projectpinenut.ui.components.views

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.ui.util.general.setHeightIfNecessary
import com.projects.rootmu.projectpinenut.ui.util.general.setWidthIfNecessary
import kotlinx.android.synthetic.main.view_shadow.view.*

class ShadowLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    var topShadowEnabled: Boolean
        get() = shadow_top.isVisible
        set(value) {
            shadow_top.isVisible = value
        }

    var bottomShadowEnabled: Boolean
        get() = shadow_bottom.isVisible
        set(value) {
            shadow_bottom.isVisible = value
        }

    private val subview: FrameLayout

    @Suppress("SENSELESS_COMPARISON")
    private val loaded: Boolean
        get() = subview != null

    init {
        View.inflate(context, R.layout.view_shadow, this)
        subview = content
        subview.clipChildren = clipChildren
        subview.clipToPadding = clipToPadding

        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ShadowLayout,
            0, 0
        ).apply {

            try {
                if (hasValue(R.styleable.ShadowLayout_topShadowEnabled)) {
                    topShadowEnabled =
                        getBoolean(R.styleable.ShadowLayout_topShadowEnabled, true)
                }
                if (hasValue(R.styleable.ShadowLayout_bottomShadowEnabled)) {
                    bottomShadowEnabled =
                        getBoolean(R.styleable.ShadowLayout_bottomShadowEnabled, true)
                }
            } finally {
                recycle()
            }
        }
    }

    override fun addView(child: View?) {
        if (loaded) {
            subview.addView(child)
        } else {
            super.addView(child)
        }
    }

    override fun addView(child: View?, index: Int) {
        if (loaded) {
            subview.addView(child, index)
        } else {
            super.addView(child, index)
        }
    }

    override fun addView(child: View?, width: Int, height: Int) {
        if (loaded) {
            subview.addView(child, width, height)
        } else {
            super.addView(child, width, height)
        }
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        if (loaded) {
            subview.addView(child, params)
        } else {
            super.addView(child, params)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(subview.measuredWidth, subview.measuredHeight)
    }

    override fun setLayoutParams(params: ViewGroup.LayoutParams?) {
        super.setLayoutParams(params)

        params?.let {
            subview.setHeightIfNecessary(if (it.height == 0) ViewGroup.LayoutParams.MATCH_PARENT else it.height)
            subview.setWidthIfNecessary(if (it.width == 0) ViewGroup.LayoutParams.MATCH_PARENT else it.width)
        }
    }

    override fun setClipToPadding(clipToPadding: Boolean) {
        super.setClipToPadding(clipToPadding)
        if (loaded) {
            subview.clipToPadding = clipToPadding
        }
    }

    override fun setClipChildren(clipChildren: Boolean) {
        super.setClipChildren(clipChildren)
        if (loaded) {
            subview.clipChildren = clipChildren
        }
    }
}