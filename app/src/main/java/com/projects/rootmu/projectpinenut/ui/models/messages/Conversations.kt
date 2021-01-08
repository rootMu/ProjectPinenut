package com.projects.rootmu.projectpinenut.ui.models.messages

import java.time.LocalDateTime

data class Message(val message: String, val author: String, val date: LocalDateTime)
data class Conversation(val recipients: List<String>, val messages: List<Message>, val lastRead: LocalDateTime)
//TODO make recipients users,