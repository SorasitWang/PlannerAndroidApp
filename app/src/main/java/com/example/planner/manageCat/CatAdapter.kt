package com.example.planner.manageCat


import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.planner.Category
import com.example.planner.StringInt
import com.example.planner.databinding.CatItemBinding
import org.jetbrains.annotations.NotNull


class CatAdapter(var deleteListener : OnClickListener,var editListener : OnClickListener) : ListAdapter<StringInt, CatAdapter.CatPropertyViewHolder>(DiffCallback) {

    companion object DiffCallback : DiffUtil.ItemCallback<StringInt>() {
        override fun areItemsTheSame(oldItem: StringInt, newItem: StringInt): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: StringInt, newItem:StringInt): Boolean {
            return oldItem.category== newItem.category
        }
    }

    class CatPropertyViewHolder(private var binding: CatItemBinding,
                                  var deleteListener: OnClickListener,
                                  var editListener: OnClickListener):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(property: StringInt) {
            binding.property = property

            /* binding.editButton.setOnClickListener{
                 editListener.onClick(eventProperty)
             }*/
            binding.catEdit.setOnClickListener{
                editListener.onClick(property)
            }
            binding.catDelete.setOnClickListener{
                deleteListener.onClick(property)
            }
            binding.executePendingBindings()
        }
    }

    override fun onBindViewHolder(holder: CatPropertyViewHolder, position: Int) {
        val catProperty = getItem(position)
        Log.i("adapter", "bind")
        holder.bind(catProperty)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            CatPropertyViewHolder {
        Log.i("adapter", "create")
        return CatPropertyViewHolder(
            CatItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ) , deleteListener , editListener
        )
    }
    class OnClickListener(val clickListener: (eventProperty: StringInt) -> Unit) {
        fun onClick(eventProperty:StringInt) = clickListener(eventProperty)
    }
}