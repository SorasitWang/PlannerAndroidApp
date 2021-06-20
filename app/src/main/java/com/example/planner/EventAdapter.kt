package com.example.planner

import android.R.attr.data
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.planner.databinding.GridViewItemBinding
import com.example.planner.generated.callback.OnClickListener


class EventAdapter(var deleteListener : OnClickListener,var editListener : OnClickListener) : ListAdapter<EventProperty, EventAdapter.EventPropertyViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<EventProperty>() {
        override fun areItemsTheSame(oldItem: EventProperty, newItem: EventProperty): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: EventProperty, newItem: EventProperty): Boolean {
            return oldItem.id == newItem.id
        }
    }

    class EventPropertyViewHolder(private var binding: GridViewItemBinding,
                                  var deleteListener: OnClickListener ,
                                  var editListener: OnClickListener):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(eventProperty: EventProperty) {
            binding.property = eventProperty
            binding.deleteButton.setImageResource(R.drawable.delete)
            binding.editButton.setImageResource(R.drawable.edit)

           /* binding.editButton.setOnClickListener{
                editListener.onClick(eventProperty)
            }*/
            binding.deleteButton.setOnClickListener{
                deleteListener.onClick(eventProperty)
            }
            binding.editButton.setOnClickListener{
                editListener.onClick(eventProperty)
            }
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: EventPropertyViewHolder, position: Int) {
        val eventProperty = getItem(position)
        Log.i("adapter", "bind")
        holder.bind(eventProperty)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            EventPropertyViewHolder {
        Log.i("adapter", "create")
        return EventPropertyViewHolder(
            GridViewItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ) , deleteListener , editListener
        )
    }
    class OnClickListener(val clickListener: (eventProperty: EventProperty) -> Unit) {
        fun onClick(eventProperty:EventProperty) = clickListener(eventProperty)
    }
}