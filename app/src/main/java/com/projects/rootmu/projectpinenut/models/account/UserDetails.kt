package com.projects.rootmu.projectpinenut.models.account

data class UserDetails(
    val userName: String,
    val firstName: String?,
    val lastName: String?,
    val phoneNumber: String?,
    val emailAddress: String?
)