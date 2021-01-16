package com.projects.rootmu.projectpinenut.util.general

fun getRandomString(length: Int = 24) : String {
    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    return (1..length)
        .map { allowedChars.random() }
        .joinToString("")
}