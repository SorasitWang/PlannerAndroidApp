package com.example.planner

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.planner.EventProperty
import com.example.planner.databinding.GridViewItemBinding

class EventAdapter : ListAdapter<EventProperty, EventAdapter.EventPropertyViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<EventProperty>() {
        override fun areItemsTheSame(oldItem: EventProperty, newItem: EventProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: EventProperty, newItem: EventProperty): Boolean {
            return oldItem.id == newItem.id
        }
    }
    class EventPropertyViewHolder(private var binding: GridViewItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(eventProperty: EventProperty) {
            binding.property = eventProperty
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: EventPropertyViewHolder, position: Int) {
        val marsProperty = getItem(position)
        holder.bind(marsProperty)

    }

    override fun onCreateViewHolder(parent: ViewGroup,viewType: Int):
            EventPropertyViewHolder {
        return EventPropertyViewHolder(GridViewItemBinding.inflate(LayoutInflater.from(parent.context)))
    }
}