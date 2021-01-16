package com.projects.rootmu.projectpinenut.ui.util.general

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("icon")
fun ImageView.setCustomIcon(@DrawableRes id: Int) {
    setImageResource(id)
}