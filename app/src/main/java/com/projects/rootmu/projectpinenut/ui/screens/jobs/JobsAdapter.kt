package com.projects.rootmu.projectpinenut.ui.screens.jobs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.projects.rootmu.projectpinenut.databinding.ViewholderJobBinding

class JobsAdapter : ListAdapter<JobsAdapter.Job, JobsAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ViewholderJobBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(job: Job) {
            binding.job = job
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ViewholderJobBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val job: Job = getItem(position)
        viewHolder.bind(job)

    }

    class DiffCallback: DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem.title == newItem.title && oldItem.description == newItem.description
        }

    }

    data class Job(val title: String, val description: String)
}