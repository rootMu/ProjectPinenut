package com.projects.rootmu.projectpinenut.ui.viewmodel.messages

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.projects.rootmu.projectpinenut.repositories.MessagingRepository
import com.projects.rootmu.projectpinenut.ui.models.messages.Conversation
import com.projects.rootmu.projectpinenut.ui.models.messages.Message
import com.projects.rootmu.projectpinenut.util.general.mapDistinct
import com.projects.rootmu.projectpinenut.util.general.updateValueIfNecessary
import timber.log.Timber
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

class MessageViewModel @ViewModelInject constructor(private val messagingRepository: MessagingRepository) : ViewModel() {

    val selectedConversation = MutableLiveData<Conversation?>()

    val selectedConversationMessages = selectedConversation.mapDistinct { it?.messages }

    val conversations = MutableLiveData<List<Conversation>?>()

    init {
        if (conversations.value?.isNotEmpty() != true) {
            conversations.value = popuplateConversions()
        }
    }

    fun selectConversation(conversation: Conversation) {
        selectedConversation.value = conversation
    }

    fun clearSelectedConversation() {
        selectedConversation.value = null
    }

    fun isSelectedConversation(conversation: Conversation): Boolean {
        return conversation == selectedConversation.value
    }

    fun updateNewMessages() {
        conversations.value = conversations.value?.let { conversations ->
            val sublist: List<Conversation> =
                conversations.filterNot { it == selectedConversation.value }
            val newSelectedConversation: Conversation =
                selectedConversation.value!!.copy(lastRead = LocalDateTime.now())
            listOf(newSelectedConversation) + sublist
        }
    }

    private fun popuplateConversions(): List<Conversation> {
        val now = LocalDateTime.now()
        val moeMessages = listOf(
            Message(
                "Hey",
                "Moe",
                now.minusMinutes(10)
            ),
            Message(
                "Hows it going",
                "Moe",
                now.minusMinutes(10).plusSeconds(10)
            ),
            Message(
                "Hey yourself, not bad thanks. How are you?",
                "User",
                now.minusMinutes(9)
            ),
            Message(
                "Yeah I'm doing swell thanks, hows the misses?",
                "Moe",
                now.minusMinutes(8)
            ),
            Message(
                "I'm glad, she's not doing too good, I think she's got the lockdown blues",
                "User",
                now.minusMinutes(6)
            ),
            Message(
                "That sucks man, I'm sorry to hear that, if theres anything I can do let me know. Wish her well from me",
                "Moe",
                now.minusMinutes(4)
            ),
            Message(
                "Yeah will do thanks, you're a top lad you know that?",
                "User",
                now.minusMinutes(4).plusSeconds(30)
            ),
            Message(
                "You're not half bad yourself :P",
                "Moe",
                now.minusMinutes(2)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(5)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(6)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(7)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(8)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(9)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(10)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(12)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(13)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(15)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(16)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(17)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(18)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(20)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(22)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(23)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(25)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(28)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(32)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(35)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(39)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(45)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(50)
            ),
            Message(
                "?",
                "Moe",
                now.minusMinutes(1).plusSeconds(59)
            )

        )
        val moeConversation = Conversation(listOf("Moe", "User"), moeMessages, now.minusMinutes(3))

        val availableMessages = listOf(
            Message(
                "Good evening, is this available?",
                "Elizabeth",
                now.minusDays(2)
            ),
            Message(
                "Yes it is",
                "User",
                now.minusDays(2).plusHours(4)
            ),
            Message(
                "Please leave me alone - we are sleeping.",
                "Elizabeth",
                now.minusDays(1)
            ),
            Message(
                "?",
                "User",
                now.minusDays(1).plusMinutes(10)
            ),
            Message(
                "No more contacting please. Thanks appreciate.",
                "Elizabeth",
                now.minusDays(1).plusMinutes(12)
            ),
            Message(
                "You contacted me",
                "User",
                now.minusDays(1).plusMinutes(13)
            ),
            Message(
                "I Know- I  no longer interested. Please stop contacing me now. I will contact attorney general if you do not stop.",
                "Elizabeth",
                now.minusDays(1).plusMinutes(15)
            ),
            Message(
                "Thsnks",
                "Elizabeth",
                now.minusDays(1).plusMinutes(15).plusSeconds(10)
            )
        )
        val elizabethConversation = Conversation(
            listOf("Elizabeth", "User"),
            availableMessages,
            now.minusDays(1).plusMinutes(14)
        )

        val helenMessages = listOf(
            Message(
                ". Caroline   KEEP MY. NAME  OUT OF YOUR  THIN. MOUTH",
                "Helen",
                now.minusDays(1)
            ),
            Message(
                "What did Caroline do Helen?",
                "Alicia",
                now.minusDays(1).plusHours(5)
            ),
            Message(
                "She stole my broccoli. Casserole recipe. 8 years ago &. Claimed it wa hers",
                "Elizabeth",
                now.minusDays(1).plusHours(5).plusMinutes(10)
            ),
            Message(
                "She claimed it was hers",
                "User",
                now.minusDays(1).plusHours(5).plusMinutes(10).plusSeconds(30)
            ),
            Message(
                "YOU NOT TALKIN ABOIT MYBFRIEND CAROLIN SHE A CHRIETSN LADY",
                "Doris",
                now.minusHours(5)
            ),
            Message(
                "shes. A rotten recipe stealin. Bitch , Doris. Careful. Who yoir friends are",
                "Helen",
                now.minusHours(3)
            )
        )
        val helenConversation = Conversation(
            listOf("Helen", "User", "Dorris", "Alicia", "Caroline"),
            helenMessages,
            now.minusHours(6)
        )

        return listOf(helenConversation, moeConversation, elizabethConversation)
    }


}