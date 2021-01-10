package com.projects.rootmu.projectpinenut.ui.screens.messages

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.projects.rootmu.projectpinenut.ui.components.BackNavigation
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationReselectedListener
import com.projects.rootmu.projectpinenut.ui.components.listeners.navigateBack
import com.projects.rootmu.projectpinenut.ui.models.messages.Conversation
import com.projects.rootmu.projectpinenut.util.general.TargetedObserver
import com.projects.rootmu.projectpinenut.util.specific.hasNewMessages
import com.projects.rootmu.projectpinenut.util.specific.newMessages
import com.projects.rootmu.projectpinenut.util.specific.positionOfFirstNewMessage
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.messages_fragment.*

class MessagesFragment : ConversationsFragment(), BottomNavigationReselectedListener,
    BackNavigation {

    override val localObservers by lazy {
        listOf(
            TargetedObserver({ messageViewModel.selectedConversationMessages }) {
                adapter.submitList(it)
            })
    }

    override val onBackPressed = {
        navigateBack()
        messageViewModel.clearSelectedConversation()
    }

    private lateinit var adapter: MessagesAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationViewModel.updateBottomNavigationReselectListener(this)
        toolBarViewModel.setWaterfallToolbarRecyclerView(items)
    }

    override fun View.handle() {

        items.apply {
            adapter = MessagesAdapter().apply {
                this@MessagesFragment.adapter = this
            }

            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            registerAdapterObserver()
        }

        send_card.visibility = View.VISIBLE
    }

    override fun setupObservers() {
        messageViewModel.conversations.observe(viewLifecycleOwner) { list ->
            val conversation = list?.firstOrNull { messageViewModel.isSelectedConversation(it) }

            conversation?.apply {
                scrollToMessages {
                    messageViewModel.updateNewMessages()
                }
            }

            list?.let { conversations ->
                val count = conversations.flatMap { it.newMessages() }.count()
                bottomNavigationViewModel.updateBottomNavigationCount(
                    INDEX,
                    if (count > 0) "$count" else null
                )
            }
        }
    }

    private fun Conversation.scrollToMessages(doExtraOnNewMessages: (() -> Unit)? = null) {
        if (hasNewMessages()) {
            val position = positionOfFirstNewMessage()
            position.takeIf { it > 0 }?.let(items::scrollToPosition)
            doExtraOnNewMessages?.invoke()
        } else {
            items.post {
                (messages.size - 1).takeIf { it > 0 }?.let(
                    items::smoothScrollToPosition
                )
            }
        }
    }

    /** BottomNavigationReselectedListener **/

    override fun onNavigationItemReselected(model: MeowBottomNavigation.Model) {
        onBackPressed.invoke()
    }

}