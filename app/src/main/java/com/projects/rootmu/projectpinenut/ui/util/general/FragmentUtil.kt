package com.projects.rootmu.projectpinenut.ui.util.general

import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

fun Fragment.onBackPressed(invoke: () -> Unit = {}) =
    requireActivity().onBackPressedDispatcher.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                invoke.invoke()
            }
        })