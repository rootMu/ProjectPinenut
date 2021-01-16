package com.projects.rootmu.projectpinenut.models.account

data class Account(
    val id: String,
    val user: UserDetails,
    val profilePicture: ProfilePicture
)