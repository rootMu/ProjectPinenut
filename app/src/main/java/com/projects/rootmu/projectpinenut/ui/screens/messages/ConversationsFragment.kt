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
import com.projects.rootmu.projectpinenut.util.specific.newMessages
import kotlinx.android.synthetic.main.jobs_fragment.*
import java.io.Serializable
import javax.annotation.meta.Exhaustive

class ConversationsFragment : NotifyingBaseFragment<ConversationsFragment.DialogCategory>(),
    MessagesListener {

    sealed class DialogCategory : Serializable {
        object OnNetworkErrorRetryAvailable : DialogCategory()

        object OnNetworkError : DialogCategory()
    }

    companion object {
        const val INDEX = 1
    }

    private val messageViewModel: MessageViewModel by activityViewModels()

    private lateinit var adapter: ConversationsAdapter
    private lateinit var layoutManager: LinearLayoutManager

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

        items.adapter = ConversationsAdapter().apply {
            this.listener = this@ConversationsFragment
            adapter = this
        }

        setupObservers()

        messageViewModel.clearSelectedConversation()

        items.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false).also {
            layoutManager = it
        }
    }

    private fun setupObservers() {

        messageViewModel.selectedConversation.observe(viewLifecycleOwner) {
            it?.let {
                navigateTo(MessagesFragment().apply {
                    this.bottomNavigationListener =
                        this@ConversationsFragment.bottomNavigationListener
                })
            }
        }

        messageViewModel.conversations.observe(viewLifecycleOwner) {
            it?.let { conversations ->
                val count = conversations.flatMap { it.newMessages() }.count()
                bottomNavigationListener?.updateBottomNavigationCount(
                    INDEX,
                    if (count > 0) "$count" else null
                )
                adapter.submitList(conversations)
            }
        }

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