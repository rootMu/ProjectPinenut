package com.projects.rootmu.projectpinenut.ui.screens.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.projects.rootmu.projectpinenut.databinding.MessagesFragmentBinding
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationReselectedListener
import com.projects.rootmu.projectpinenut.ui.components.listeners.navigateBack
import com.projects.rootmu.projectpinenut.ui.models.messages.Conversation
import com.projects.rootmu.projectpinenut.ui.util.general.autoCleared
import com.projects.rootmu.projectpinenut.util.general.TargetedObserver
import com.projects.rootmu.projectpinenut.util.specific.hasNewMessages
import com.projects.rootmu.projectpinenut.util.specific.newMessages
import com.projects.rootmu.projectpinenut.util.specific.positionOfFirstNewMessage
import kotlinx.android.synthetic.main.messages_fragment.*

class MessagesFragment : ConversationsFragment(), BottomNavigationReselectedListener {

    override val localObservers by lazy {
        listOf(
            TargetedObserver({ messageViewModel.selectedConversation }) {
                it?.let {
                    binding.conversation = it
                }
            },
            TargetedObserver({ messageViewModel.selectedConversationMessages }) {
                adapter.submitList(it)
            })
    }

    override val onBackPressed = {
        navigateBack()
        messageViewModel.clearSelectedConversation()
    }

    private lateinit var adapter: MessagesAdapter

    private var binding: MessagesFragmentBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MessagesFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainTabsViewModel.updateBottomNavigationReselectListener(this)
    }

    override fun View.handle() {

        items.apply {

            adapter = MessagesAdapter().apply {
                this@MessagesFragment.adapter = this
            }

            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            registerAdapterObserver()
        }
    }

    override fun setupObservers() {
        messageViewModel.conversations.observe(viewLifecycleOwner) { list ->
            val conversation = list?.firstOrNull { messageViewModel.isSelectedConversation(it) }
            conversation?.scrollToMessages {
                messageViewModel.updateNewMessages()
            }

            list?.let { conversations ->
                val count = conversations.flatMap { it.newMessages() }.count()
                mainTabsViewModel.updateBottomNavigationCount(
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