package com.projects.rootmu.projectpinenut.models.token

import java.time.ZonedDateTime

data class Token(
    val accessToken: String,
    val expiration: ZonedDateTime
)