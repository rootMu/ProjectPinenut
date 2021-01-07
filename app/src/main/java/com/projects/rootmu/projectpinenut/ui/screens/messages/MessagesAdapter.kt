package com.projects.rootmu.projectpinenut.ui.screens.messages

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projects.rootmu.projectpinenut.databinding.ViewholderMessageBinding
import com.projects.rootmu.projectpinenut.listeners.MessagesListener
import com.projects.rootmu.projectpinenut.ui.models.messages.Message

class MessagesAdapter :
    ListAdapter<Message, MessagesAdapter.ViewHolder>(DiffCallback()) {

    var listener: MessagesListener? = null

    inner class ViewHolder(private val binding: ViewholderMessageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(message: Message) {
            binding.message = message
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ViewholderMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val message: Message = getItem(position)
        viewHolder.bind(message)

    }

    class DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return (oldItem.author == newItem.author
                    && oldItem.date == newItem.date
                    && oldItem.message == newItem.message)
        }

    }
}