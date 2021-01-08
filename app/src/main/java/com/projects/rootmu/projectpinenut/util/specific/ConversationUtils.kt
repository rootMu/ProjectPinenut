package com.projects.rootmu.projectpinenut.util.specific

import com.projects.rootmu.projectpinenut.ui.models.messages.Conversation
import com.projects.rootmu.projectpinenut.ui.models.messages.Message
import com.projects.rootmu.projectpinenut.ui.util.general.indexOfFirstOrNull

fun Conversation?.otherParticipants(): String {
    if(this == null)
        return ""

    val self = "User" //TODO get this from user repo
    val users = recipients.filterNot {it == self}
    return if(users.size > 1) {
        users.subList(0, users.size - 1).joinToString().plus(" and ${users.last()}")
    }else{
        users.first()
    }
}

fun Conversation.lastMessage(): Message {
    return messages.last()
}

fun Conversation.newMessages(): List<Message> {
    return messages.filter { it.date.isAfter(lastRead) }
}

fun Conversation?.hasNewMessages(): Boolean {
    return this?.newMessages()?.isNotEmpty() == true
}

fun Conversation?.positionOfFirstNewMessage(): Int {
    return this?.messages?.indexOfFirstOrNull { it.date.isAfter(lastRead) }?:0
}