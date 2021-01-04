package com.projects.rootmu.projectpinenut.ui.util.general.delegates

import android.animation.Animator
import androidx.lifecycle.LifecycleOwner
import com.projects.rootmu.projectpinenut.listeners.addCompletionListener
import kotlin.reflect.KProperty

class AnimatorDelegate<T: Animator>(private val lifecycleOwner: LifecycleOwner? = null) {

    private var field: T? = null

    operator fun getValue(thisRef: Any?, property: KProperty<*>): T? {
        return field
    }

    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: T?) {
        field?.cancel()

        value?.addCompletionListener(lifecycleOwner) {
            if (field == value) {
                field = null
            }
        }
        field = value
        value?.start()
    }

}