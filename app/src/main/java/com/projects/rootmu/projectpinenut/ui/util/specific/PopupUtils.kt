package com.projects.rootmu.projectpinenut.ui.util.specific

import androidx.annotation.DrawableRes
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.ui.models.PopupType

@DrawableRes
fun PopupType?.iconId() = when (this) {
        PopupType.INFO -> R.drawable.ic_notification_info
        PopupType.WARNING -> R.drawable.ic_notification_warning
        PopupType.ERROR -> R.drawable.ic_notification_error
        else -> R.drawable.ic_notification_info
    }