package com.rd

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.text.TextUtilsCompat
import androidx.core.util.component1
import androidx.core.util.component2
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.AdapterDataObserver
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.rd.animation.type.*
import com.rd.draw.data.Indicator
import com.rd.draw.data.PositionSavedState
import com.rd.draw.data.RtlMode
import com.rd.utils.CoordinatesUtils
import com.rd.utils.IdUtils

/**
 * Custom Page Indicator View to allow use with ViewPager2
 */

class PageIndicatorView2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr), IndicatorManager.Listener, View.OnTouchListener {

    init {
        setupId()
        initIndicatorManager(attrs)
        if (manager.indicator().isFadeOnIdle) {
            startIdleRunnable()
        }
    }

    private val callback: OnPageChangeCallback by lazy {
        object : OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                onPageScroll(position, positionOffset)
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    manager.indicator().isInteractiveAnimation = isInteractionEnabled
                }
            }
        }
    }

    private lateinit var manager: IndicatorManager
    private var setObserver: AdapterDataObserver? = null
    private var viewPager: ViewPager2? = null
    private var isInteractionEnabled = false


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        findViewPager(parent)
    }

    override fun onDetachedFromWindow() {
        unRegisterSetObserver()
        super.onDetachedFromWindow()
    }

    override fun onSaveInstanceState(): Parcelable {
        return PositionSavedState(super.onSaveInstanceState()).apply {
            selectedPosition = manager.indicator().selectedPosition
            selectingPosition = manager.indicator().selectingPosition
            lastSelectedPosition = manager.indicator().lastSelectedPosition
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is PositionSavedState) {
            manager.indicator().apply {
                selectedPosition = state.selectedPosition
                selectingPosition = state.selectingPosition
                lastSelectedPosition = state.lastSelectedPosition
            }
            super.onRestoreInstanceState(state.superState)
        } else {
            super.onRestoreInstanceState(state)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val (first, second) = manager.drawer()
            .measureViewSize(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(first, second)
    }

    override fun onDraw(canvas: Canvas) {
        manager.drawer().draw(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        manager.drawer().touch(event)
        return true
    }

    override fun onTouch(v: View?, event: MotionEvent): Boolean {
        if (!manager.indicator().isFadeOnIdle) return false
        when (event.action) {
            MotionEvent.ACTION_DOWN -> stopIdleRunnable()
            MotionEvent.ACTION_UP -> startIdleRunnable()
        }
        return false
    }

    override fun onIndicatorUpdated() {
        invalidate()
    }

    private fun setDynamicCount(dynamicCount: Boolean) {
        manager.indicator().isDynamicCount = dynamicCount
        if (dynamicCount) {
            registerSetObserver()
        } else {
            unRegisterSetObserver()
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setViewPager(@Nullable pager: ViewPager2?) {
        releaseViewPager()
        viewPager = pager?.apply {
            registerOnPageChangeCallback(callback)
            setOnTouchListener(this@PageIndicatorView2)
        }?.apply {
            manager.indicator().viewPagerId = id
            setDynamicCount(manager.indicator().isDynamicCount)
            updateState()
        }
    }

    private fun releaseViewPager() {
        viewPager?.let {
            it.unregisterOnPageChangeCallback(callback)
            viewPager = null
        }
    }

    private fun setProgress(selectedPosition: Int, progress: Float) {

        with(manager.indicator()) {
            if (isInteractiveAnimation) {

                val p = progress.coerceAtLeast(0f).coerceAtMost(1f)
                val s = selectedPosition.coerceAtLeast(0).coerceAtMost(count - 1)
                if (p == 1f) {
                    lastSelectedPosition = this.selectedPosition
                    this.selectedPosition = s
                }
                selectingPosition = s
                manager.animate().interactive(p)
            }
        }
    }

    private fun setupId() {
        if (id == NO_ID) {
            id = IdUtils.generateViewId()
        }
    }

    private fun initIndicatorManager(@Nullable attrs: AttributeSet?) {
        manager = IndicatorManager(this).apply {
            drawer().initAttributes(context, attrs)
            indicator().apply {
                paddingLeft = paddingLeft
                paddingTop = paddingTop
                paddingRight = paddingRight
                paddingBottom = paddingBottom
                isInteractionEnabled = isInteractiveAnimation
            }
        }
    }

    private fun registerSetObserver() {
        setObserver ?: run {
            try {
                viewPager?.adapter?.registerAdapterDataObserver(object :
                    RecyclerView.AdapterDataObserver() {
                    override fun onChanged() {
                        updateState()
                    }
                }.also {
                    setObserver = it
                })
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }

        }
    }

    private fun unRegisterSetObserver() {
        try {
            setObserver?.let {
                viewPager?.adapter?.unregisterAdapterDataObserver(it)
            }
            setObserver = null
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        }
    }

    private fun updateState() {

        viewPager?.apply {
            adapter?.let {
                val selectedPos =
                    if (isRtl) it.itemCount - 1 - currentItem else currentItem
                manager.indicator().apply {
                    selectedPosition = selectedPos
                    selectingPosition = selectedPos
                    lastSelectedPosition = selectedPos
                    count = it.itemCount
                }
                manager.animate().end()
                updateVisibility()
                requestLayout()
            }
        }
    }

    private fun updateVisibility() {

        if (manager.indicator().isAutoVisibility) {
            visibility = when {
                visibility != VISIBLE && manager.indicator().count > Indicator.MIN_COUNT -> VISIBLE
                visibility != INVISIBLE && manager.indicator().count <= Indicator.MIN_COUNT -> INVISIBLE
                else -> visibility
            }
        }
    }

    private fun onPageScroll(position: Int, positionOffset: Float) {

        if (isViewMeasured &&
            manager.indicator().animationType != AnimationType.NONE &&
            manager.indicator().isInteractiveAnimation
        ) {
            val (selectingPosition, selectingProgress) = CoordinatesUtils.getProgress(
                manager.indicator(), position, positionOffset,
                isRtl
            )
            setProgress(selectingPosition, selectingProgress)
        }
    }

    private val isRtl: Boolean
        get() {
            return when (manager.indicator().rtlMode) {
                RtlMode.On -> true
                RtlMode.Off -> false
                RtlMode.Auto -> TextUtilsCompat.getLayoutDirectionFromLocale(
                    context.resources.configuration.locale
                ) == ViewCompat.LAYOUT_DIRECTION_RTL
            }
        }

    private val isViewMeasured: Boolean
        get() = measuredHeight != 0 || measuredWidth != 0

    private fun findViewPager(@Nullable viewParent: ViewParent?) {

        viewParent?.apply {
            if (this is ViewGroup && childCount > 0) {
                findViewPager(viewParent as ViewGroup?, manager.indicator().viewPagerId)?.apply {
                    setViewPager(this)
                } ?: findViewPager(viewParent.getParent())
            }
        }
    }

    @Nullable
    private fun findViewPager(@NonNull viewGroup: ViewGroup?, id: Int) =
        with(viewGroup?.findViewById<View>(id)) {
            when (this) {
                is ViewPager2 -> this
                else -> null
            }
        }


    private fun adjustPosition(position: Int) =
        position.coerceAtMost(manager.indicator().count - 1).coerceAtLeast(0)


    private fun displayWithAnimation() {
        animate().cancel()
        animate().alpha(1.0f).duration = Indicator.IDLE_ANIMATION_DURATION.toLong()
    }

    private fun hideWithAnimation() {
        animate().cancel()
        animate().alpha(0f).duration = Indicator.IDLE_ANIMATION_DURATION.toLong()
    }

    private fun startIdleRunnable() {
        HANDLER.removeCallbacks(idleRunnable)
        HANDLER.postDelayed(idleRunnable, manager.indicator().idleDuration)
    }

    private fun stopIdleRunnable() {
        HANDLER.removeCallbacks(idleRunnable)
        displayWithAnimation()
    }

    private val idleRunnable = Runnable {
        manager.indicator().isIdle = true
        hideWithAnimation()
    }

    companion object {
        private val HANDLER: Handler = Handler(Looper.getMainLooper())
    }
}