package com.projects.rootmu.projectpinenut.ui.util.general.drawableHelper

import android.content.Context
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

internal object DrawableHelper {

    fun changeColorDrawableVector(c: Context?, resDrawable: Int, color: Int): Drawable? {
        if (c == null)
            return null

        val d = VectorDrawableCompat.create(c.resources, resDrawable, null) ?: return null
        d.mutate()
        if (color != -2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                d.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_IN)
            } else {
                d.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            }
        }
        return d
    }

    fun changeColorDrawableRes(c: Context?, resDrawable: Int, color: Int): Drawable? {
        if (c == null)
            return null

        val d = ContextCompat.getDrawable(c, resDrawable) ?: return null
        d.mutate()
        if (color != -2) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                d.colorFilter = BlendModeColorFilter(color, BlendMode.SRC_IN)
            } else {
                d.setColorFilter(color, PorterDuff.Mode.SRC_IN)
            }

        }
        return d
    }
}

internal fun Context.getDrawableCompat(res: Int) = ContextCompat.getDrawable(this, res)