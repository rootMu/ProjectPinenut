package com.projects.rootmu.projectpinenut.ui.screens.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.ConversationsFragmentBinding
import com.projects.rootmu.projectpinenut.listeners.MessagesListener
import com.projects.rootmu.projectpinenut.ui.components.listeners.navigateTo
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.models.PopupType
import com.projects.rootmu.projectpinenut.ui.models.messages.Conversation
import com.projects.rootmu.projectpinenut.ui.screens.dialog.NotifyingBaseFragment
import com.projects.rootmu.projectpinenut.ui.util.general.autoCleared
import com.projects.rootmu.projectpinenut.ui.viewmodel.messages.MessageViewModel
import com.projects.rootmu.projectpinenut.util.general.TargetedObserver
import com.projects.rootmu.projectpinenut.util.specific.newMessages
import kotlinx.android.synthetic.main.conversations_fragment.*
import java.io.Serializable
import javax.annotation.meta.Exhaustive

open class ConversationsFragment : NotifyingBaseFragment<ConversationsFragment.DialogCategory>(),
    MessagesListener {

    sealed class DialogCategory : Serializable {
        object OnNetworkErrorRetryAvailable : DialogCategory()
        object OnNetworkError : DialogCategory()
    }

    companion object {
        const val INDEX = 1
    }

    override val observers by lazy {
        super.observers + localObservers
    }

    protected open val localObservers: List<TargetedObserver<out Any?>> by lazy {
        listOf(TargetedObserver({ messageViewModel.selectedConversation }) {
            it?.let {
                navigateTo(MessagesFragment())
            }
        })
    }

    protected val messageViewModel: MessageViewModel by activityViewModels()

    private lateinit var adapter: ConversationsAdapter

    private var binding: ConversationsFragmentBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ConversationsFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.handle()
        setupObservers()
    }

    protected open fun View.handle() {

        items.apply {

            adapter = ConversationsAdapter().apply {
                this.listener = this@ConversationsFragment
                this@ConversationsFragment.adapter = this
            }

            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

            registerAdapterObserver()

            messageViewModel.clearSelectedConversation()
        }
    }

    protected open fun setupObservers() {
        messageViewModel.conversations.observe(viewLifecycleOwner) { list ->
            list?.let { conversations ->
                val count = conversations.flatMap { it.newMessages() }.count()
                mainTabsViewModel.updateBottomNavigationCount(
                    INDEX,
                    if (count > 0) "$count" else null
                )
                adapter.submitList(conversations)
            }
        }
    }

    protected open fun RecyclerView.registerAdapterObserver() {
        adapter?.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(
                positionStart: Int,
                itemCount: Int
            ) {
                scrollToPosition(0)
            }

            override fun onItemRangeRemoved(
                positionStart: Int,
                itemCount: Int
            ) {
                smoothScrollToPosition(itemCount)
            }
        })
    }

    /** Notifying **/

    override fun doPrimaryAction(category: DialogCategory) {
        @Exhaustive
        when (category) {
            DialogCategory.OnNetworkErrorRetryAvailable -> {
                //DO Nothing
            }
            DialogCategory.OnNetworkError -> {
                //DO Nothing
            }
        }
    }

    override fun doSecondaryAction(category: DialogCategory) {
        @Exhaustive
        when (category) {
            DialogCategory.OnNetworkErrorRetryAvailable -> {
                //DO Nothing
            }
            DialogCategory.OnNetworkError -> {
                //DO Nothing
            }
        }
    }

    override fun getDialogData(category: DialogCategory) = when (category) {
        is DialogCategory.OnNetworkErrorRetryAvailable -> DialogData.Basic.fromIds(
            resources,
            PopupType.ERROR,
            R.string.error_title,
            R.string.message_retrieval_failed_error_retry,
            R.string.retry,
            null
        )
        is DialogCategory.OnNetworkError -> DialogData.Basic.error(
            resources,
            descriptionId = R.string.message_retrieval_failed_error
        )
    }

    /** Listener **/

    override fun openConversation(conversation: Conversation) {
        messageViewModel.selectConversation(conversation)
    }
}
