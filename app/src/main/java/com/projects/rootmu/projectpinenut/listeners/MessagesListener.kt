package com.projects.rootmu.projectpinenut.listeners

import com.projects.rootmu.projectpinenut.ui.models.messages.Conversation

interface MessagesListener {
    fun openConversation(conversation: Conversation)
}