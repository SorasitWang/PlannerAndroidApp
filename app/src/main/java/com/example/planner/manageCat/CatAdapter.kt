package com.example.planner.manageCat


import android.util.Log
import android.view.LayoutInflater
import android.view.View
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
        var editing : Boolean = false
        var deleteText = "Deete"
        var editText = "Edit"
        var inputVisible = View.GONE
        var nameVisible = View.VISIBLE
        var tmpNewCat = ""
        fun bind(property: StringInt) {

            binding.property = property
            binding.adapter = this
            /* binding.editButton.setOnClickListener{
                 editListener.onClick(eventProperty)
             }*/
            binding.catEdit.setOnClickListener{

                Log.i("adpater","edit")
                if (editing == true){
                    showInput(false)
                    editing = false
                    editListener.onClick(property)
                }
                else {

                    showInput(true)
                    editing = true

                }
            }
            binding.catDelete.setOnClickListener{
                if (editing == true){
                    showInput(false)
                    editing = false
                }
                else {
                    deleteListener.onClick(property)
                }
            }
            binding.executePendingBindings()
        }

        fun showInput(t:Boolean){
            if (t == false){
                binding.catEdit.text = "Edit"
                binding.catDelete.text = "Delete"
                binding.catInput.visibility = View.GONE
                binding.catName.visibility = View.VISIBLE
            }
            else{
                binding.catEdit.text = "OK"
                binding.catDelete.text = "Cancel"
                binding.catInput.visibility = View.VISIBLE
                binding.catName.visibility = View.GONE
            }
        }
    }

    override fun onBindViewHolder(holder: CatPropertyViewHolder, position: Int) {
        val catProperty = getItem(position)
        Log.i("adapter", "bind")
        holder.bind(catProperty)


    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            CatPropertyViewHolder {
        var binding =  CatItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        var holder = CatPropertyViewHolder(binding,deleteListener , editListener)
        binding.adapter = holder
        return holder

    }
    class OnClickListener(val clickListener: (eventProperty: StringInt) -> Unit) {
        fun onClick(eventProperty:StringInt) = clickListener(eventProperty)
    }


}