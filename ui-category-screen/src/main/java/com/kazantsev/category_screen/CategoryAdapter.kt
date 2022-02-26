package com.kazantsev.category_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

import com.kazantsev.category_screen.model.CategoryUi
import com.kazantsev.category_screen.R
import com.kazantsev.category_screen.databinding.ItemCategoryBinding


class CategoryAdapter(
    val onListItemClickListener: OnListItemClickListener,
) : ListAdapter<CategoryUi, CategoryAdapter.CategoryViewHolder>(CategoryDiffItemCallback) {
    private lateinit var bindingItem: ItemCategoryBinding

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        bindingItem =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(bindingItem)
    }

    inner class CategoryViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CategoryUi?) {
            data?.let {
                with(binding) {
                    root.setOnClickListener { onListItemClickListener.onItemClick(data) }
                    tvTitle.text = it.name
                    tvDescription.text = it.description
                    tvScore.text =
                        root.context.getString(R.string.total_format, it.solved, it.total)
                }
            }
        }
    }
}

private object CategoryDiffItemCallback : DiffUtil.ItemCallback<CategoryUi>() {
    override fun areItemsTheSame(oldItem: CategoryUi, newItem: CategoryUi): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: CategoryUi, newItem: CategoryUi): Boolean {
        return oldItem.id == newItem.id
    }
}

interface OnListItemClickListener {
    fun onItemClick(category: CategoryUi)
}