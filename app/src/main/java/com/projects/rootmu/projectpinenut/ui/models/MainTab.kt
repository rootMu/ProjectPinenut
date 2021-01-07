package com.projects.rootmu.projectpinenut.ui.models

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.ui.screens.account.AccountContainerFragment
import com.projects.rootmu.projectpinenut.ui.screens.home.HomeContainerFragment
import com.projects.rootmu.projectpinenut.ui.screens.jobs.JobsContainerFragment
import com.projects.rootmu.projectpinenut.ui.screens.messages.ConversationsContainerFragment

enum class MainTab(@StringRes val titleRes: Int) {
    HOME(R.string.main_tab_home_title),
    MESSAGES(R.string.main_tab_messages_title),
    JOBS(R.string.main_tab_jobs_title),
    NOTIFICATIONS(R.string.main_tab_notifications_title),
    ACCOUNT(R.string.main_tab_account_title);

    companion object {
        fun fromInt(id: Int): MainTab = when (id) {
            0 -> HOME
            1 -> MESSAGES
            2 -> JOBS
            3 -> NOTIFICATIONS
            4 -> ACCOUNT
            else -> HOME
        }
    }

    @DrawableRes
    fun getIconId() = when (this) {
        HOME -> R.drawable.ic_home
        MESSAGES -> R.drawable.ic_messages
        JOBS -> R.drawable.ic_jobs
        NOTIFICATIONS -> R.drawable.ic_notifications
        ACCOUNT -> R.drawable.ic_account
    }

}

fun MainTab.getFragmentTag(): String =
    when (this) {
        MainTab.HOME -> HomeContainerFragment::class.simpleName ?: "home"
        MainTab.MESSAGES -> ConversationsContainerFragment::class.simpleName ?: "messages"
        MainTab.JOBS -> JobsContainerFragment::class.simpleName ?: "jobs"
        MainTab.NOTIFICATIONS -> AccountContainerFragment::class.simpleName ?: "notifications"
        MainTab.ACCOUNT -> AccountContainerFragment::class.simpleName ?: "account"
    }
