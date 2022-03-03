package com.kazantsev.ui_common.adapter

import android.graphics.Color
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PuzzleViewHolder {
        val bindingItem =
            ItemPuzzleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PuzzleViewHolder(bindingItem)
    }

    inner class PuzzleViewHolder(private val binding: ItemPuzzleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Puzzle?) {

            data?.let {
                with(binding) {
                    root.setOnClickListener { onListItemClickListener.onItemClick(data) }
                    tvName.text = it.name
                    root.setBackgroundColor(if (it.solved) Color.LTGRAY else Color.TRANSPARENT)
                    tvDescription.text = questionFormatting(it.question)
                    ivFavorite.setColorFilter(Util.favoriteColor(it.favorite))
                    ivDifficult.setColorFilter(Util.difficultColor(it.difficult))
                    ivFavorite.setOnClickListener {
                        onListFavoriteClickListener.onItemClick(data)
                    }
                }
            }
        }

        private fun questionFormatting(question: String): CharSequence {
            val spannedText: Spanned =
                Html.fromHtml(question.replace(Regex("<img.*?src=\"(.*?)\"[^>]+>"), ""))
            return spannedText.trim()
        }
    }
}

private object PuzzleDiffItemCallback : DiffUtil.ItemCallback<Puzzle>() {
    override fun areItemsTheSame(oldItem: Puzzle, newItem: Puzzle): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Puzzle, newItem: Puzzle): Boolean {
        return oldItem.id == newItem.id
    }
}

interface OnListItemClickListener {
    fun onItemClick(puzzle: Puzzle)
}