package com.projects.rootmu.projectpinenut.ui.util.general

import android.text.method.TransformationMethod
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("inputType")
fun TextInputEditText.setInputType(inputType: TransformationMethod) {
    transformationMethod = inputType
}