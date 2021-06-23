package com.example.planner.manageCat


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.planner.Category
import com.example.planner.databinding.CatItemBinding
import org.jetbrains.annotations.NotNull


class CatAdapter(var deleteListener : OnClickListener,var editListener : OnClickListener) : ListAdapter<Category, CatAdapter.EventPropertyViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Category, newItem:Category): Boolean {
            return oldItem.cat== newItem.cat
        }
    }

    class EventPropertyViewHolder(private var binding: CatItemBinding,
                                  var deleteListener: OnClickListener,
                                  var editListener: OnClickListener):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(property: Category) {
            binding.property = property

            /* binding.editButton.setOnClickListener{
                 editListener.onClick(eventProperty)
             }*/
            binding.catEdit.setOnClickListener{
                deleteListener.onClick(property)
            }
            binding.catDelete.setOnClickListener{
                editListener.onClick(property)
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
            CatItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ) , deleteListener , editListener
        )
    }
    class OnClickListener(val clickListener: (eventProperty: Category) -> Unit) {
        fun onClick(eventProperty:Category) = clickListener(eventProperty)
    }
}