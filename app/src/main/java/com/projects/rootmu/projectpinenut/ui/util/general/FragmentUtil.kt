package com.projects.rootmu.projectpinenut.ui.util.general

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment

fun Fragment.onBackPressed(invoke: (() -> Unit)? = null) =
    requireActivity().onBackPressedDispatcher.addCallback(
        viewLifecycleOwner,
        object : OnBackPressedCallback(true /* enabled by default */) {
            override fun handleOnBackPressed() {
                invoke?.invoke()
            }
        })

inline fun <T : Fragment> T.withArgs(argsBuilder: Bundle.() -> Unit): T =
    this.apply {
        arguments = Bundle().apply(argsBuilder)
    }

fun getMainHandler() = Handler(Looper.getMainLooper())