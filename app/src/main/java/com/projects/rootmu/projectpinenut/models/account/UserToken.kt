package com.projects.rootmu.projectpinenut.models.account

import com.projects.rootmu.projectpinenut.models.token.Token

data class UserToken(
    val token: Token,
    val passwordUpdateRequired: Boolean,
    val user: Account
)
