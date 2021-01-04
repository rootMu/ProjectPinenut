package com.projects.rootmu.projectpinenut.ui.util.general

import android.content.Context
import android.util.TypedValue

private fun getDP(context: Context) = context.resources.displayMetrics.density

internal fun dipf(context: Context, f: Float) = f * getDP(context)

internal fun dipf(context: Context, i: Int) = i * getDP(context)

internal fun toDP(context: Context,value: Int): Int = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(),context.resources.displayMetrics).toInt()

internal fun toPx(context: Context,value: Float): Float = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, value,context.resources.displayMetrics)

internal fun dip(context: Context, i: Int) = (i * getDP(context)).toInt()