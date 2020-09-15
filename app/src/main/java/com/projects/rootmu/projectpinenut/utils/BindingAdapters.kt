package com.projects.rootmu.projectpinenut.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:src")
fun setImageUri(view: ImageView, src: Int) {
    view.setImageResource(src)
}