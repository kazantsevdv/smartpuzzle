package com.kazantsev.ui_common.adapter

import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kazantsev.domain.model.Puzzle
import com.kazantsev.ui_common.databinding.ItemPuzzleBinding
import com.kazantsev.ui_common.util.Util


class PuzzleAdapter(
    val onListItemClickListener: OnListItemClickListener,
    val onListFavoriteClickListener: OnListItemClickListener,
) : ListAdapter<Puzzle, PuzzleAdapter.PuzzleViewHolder>(PuzzleDiffItemCallback) {

    override fun onBindViewHolder(holder: PuzzleViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
        holder: PuzzleViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else
            holder.bindFavoriteState(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PuzzleViewHolder {
        val bindingItem =
            ItemPuzzleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PuzzleViewHolder(bindingItem)
    }

    inner class PuzzleViewHolder(private val binding: ItemPuzzleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Puzzle) {
            with(binding) {
                tvName.text = data.name
                tvName.alpha = if (data.solved) solved else notSolved
                tvDescription.text = questionFormatting(data.question)
                tvDescription.alpha = if (data.solved) solved else notSolved
                ivFavorite.setColorFilter(Util.favoriteColor(data.favorite))
                ivDifficult.setColorFilter(Util.difficultColor(data.difficult))
                ivFavorite.setOnClickListener {
                    onListFavoriteClickListener.onItemClick(data)
                }
                root.setOnClickListener { onListItemClickListener.onItemClick(data) }
            }

        }

        fun bindFavoriteState(data: Puzzle)  {
            binding.ivFavorite.setColorFilter(Util.favoriteColor(data.favorite))
            binding.ivFavorite.setOnClickListener {
                onListFavoriteClickListener.onItemClick(data)
            }
        }
        private fun questionFormatting(question: String): CharSequence {
            val spannedText: Spanned =
                Html.fromHtml(question.replace(Regex("<img.*?src=\"(.*?)\"[^>]+>"), ""))
            return spannedText.trim()
        }


    }

    companion object {
        const val solved = 0.4f
        const val notSolved = 1f
    }
}

private object PuzzleDiffItemCallback : DiffUtil.ItemCallback<Puzzle>() {
    override fun areItemsTheSame(oldItem: Puzzle, newItem: Puzzle): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Puzzle, newItem: Puzzle): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: Puzzle, newItem: Puzzle): Any? {
        return if (oldItem.favorite != newItem.favorite) true else null
    }


}

interface OnListItemClickListener {
    fun onItemClick(puzzle: Puzzle)
}