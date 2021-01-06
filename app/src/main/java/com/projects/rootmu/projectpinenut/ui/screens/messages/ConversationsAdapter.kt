package com.projects.rootmu.projectpinenut.ui.screens.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projects.rootmu.projectpinenut.databinding.ViewholderConversationBinding
import com.projects.rootmu.projectpinenut.databinding.ViewholderMessageBinding
import com.projects.rootmu.projectpinenut.listeners.MessagesListener
import com.projects.rootmu.projectpinenut.ui.models.messages.Conversation
import java.util.*

class ConversationsAdapter :
    ListAdapter<Conversation, ConversationsAdapter.ViewHolder>(DiffCallback()) {

    var listener: MessagesListener? = null

    inner class ViewHolder(private val binding: ViewholderConversationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(conversation: Conversation) {
            binding.conversation = conversation
            itemView.setOnClickListener {
                listener?.openConversation(conversation)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewholderConversationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val conversation: Conversation = getItem(position)
        viewHolder.bind(conversation)

    }

    class DiffCallback : DiffUtil.ItemCallback<Conversation>() {
        override fun areItemsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Conversation, newItem: Conversation): Boolean {
            return oldItem.recipients == newItem.recipients
                    && oldItem.messages == newItem.messages
        }
    }
}