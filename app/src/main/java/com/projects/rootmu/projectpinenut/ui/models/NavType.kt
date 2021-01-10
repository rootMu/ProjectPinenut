package com.projects.rootmu.projectpinenut.ui.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.ui.screens.account.AccountContainerFragment
import com.projects.rootmu.projectpinenut.ui.screens.home.HomeContainerFragment
import com.projects.rootmu.projectpinenut.ui.screens.jobs.JobsContainerFragment
import com.projects.rootmu.projectpinenut.ui.screens.messages.ConversationsContainerFragment
import javax.annotation.meta.Exhaustive

enum class NavType(val id: Int) {
    HOME(0),
    BACK(1);

    @Exhaustive
    @DrawableRes
    fun getIconId() = when (this) {
        HOME -> R.drawable.ic_home
        BACK -> R.drawable.ic_back
    }
}
