package com.projects.rootmu.projectpinenut.ui.screens.messages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.projects.rootmu.projectpinenut.R
import com.projects.rootmu.projectpinenut.databinding.MessagesFragmentBinding
import com.projects.rootmu.projectpinenut.ui.components.listeners.BottomNavigationReselectedListener
import com.projects.rootmu.projectpinenut.ui.components.listeners.navigateBack
import com.projects.rootmu.projectpinenut.ui.models.DialogData
import com.projects.rootmu.projectpinenut.ui.models.PopupType
import com.projects.rootmu.projectpinenut.ui.models.messages.Conversation
import com.projects.rootmu.projectpinenut.ui.screens.dialog.NotifyingBaseFragment
import com.projects.rootmu.projectpinenut.ui.util.general.autoCleared
import com.projects.rootmu.projectpinenut.ui.util.general.onBackPressed
import com.projects.rootmu.projectpinenut.ui.viewmodel.messages.MessageViewModel
import com.projects.rootmu.projectpinenut.util.specific.hasNewMessages
import com.projects.rootmu.projectpinenut.util.specific.newMessages
import com.projects.rootmu.projectpinenut.util.specific.positionOfFirstNewMessage
import kotlinx.android.synthetic.main.jobs_fragment.*
import java.io.Serializable
import javax.annotation.meta.Exhaustive


class MessagesFragment : NotifyingBaseFragment<MessagesFragment.DialogCategory>(),
    BottomNavigationReselectedListener {

    sealed class DialogCategory : Serializable {
        object OnNetworkErrorRetryAvailable : DialogCategory()

        object OnNetworkError : DialogCategory()
    }

    companion object {
        const val INDEX = 1
    }

    private val messageViewModel: MessageViewModel by activityViewModels()

    private lateinit var adapter: MessagesAdapter
    private lateinit var layoutManager: LinearLayoutManager

    private var binding: MessagesFragmentBinding by autoCleared()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MessagesFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = messageViewModel

        onBackPressed {
            doBack()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        items.adapter = MessagesAdapter().apply {
            adapter = this
        }

        items.layoutManager = LinearLayoutManager(view.context, RecyclerView.VERTICAL, false).also {
            layoutManager = it
        }

        bottomNavigationListener?.setBottomNavigationReselectedListener(this)

        setupObservers()
    }

    private fun doBack() {
        messageViewModel.clearSelectedConversation()
    }

    private fun setupObservers() {

        messageViewModel.selectedConversation.observe(viewLifecycleOwner)
        {
            it?.let {
                binding.conversation = it
                adapter.submitList(it.messages)
                it.scrollToMessages()
            } ?: if(!messageViewModel.selectedConversation.isPaused)
                    finish()
        }


        messageViewModel.conversations.observe(viewLifecycleOwner) { list ->
            val conversation = list?.firstOrNull { messageViewModel.isSelectedConversation(it) }
            conversation?.scrollToMessages { messageViewModel.updateNewMessages() }

            list?.let { conversations ->
                val count = conversations.flatMap { it.newMessages() }.count()
                bottomNavigationListener?.updateBottomNavigationCount(
                    INDEX,
                    if (count > 0) "$count" else null
                )
            }
        }
    }

    private fun finish() {
        messageViewModel.selectedConversation.pause()
        navigateBack()
    }

    private fun Conversation.scrollToMessages(doExtraOnNewMessages: (() -> Unit)? = null) {
        if (hasNewMessages()) {
            items.post { items.scrollToPosition(positionOfFirstNewMessage()) }
            doExtraOnNewMessages?.invoke()
        } else {
            items.post { items.scrollToPosition(messages.size - 1) }
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

    /** BottomNavigationReselectedListener **/
    override fun onNavigationItemReselected(model: MeowBottomNavigation.Model) {
        doBack()
    }

}