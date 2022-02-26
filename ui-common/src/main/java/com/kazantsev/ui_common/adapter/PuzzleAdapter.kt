package com.kazantsev.ui_common.adapter

import android.graphics.Color
import android.text.Html
import android.text.Spanned
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kazantsev.domain.model.Difficult
import com.kazantsev.domain.model.Puzzle
import com.kazantsev.ui_common.databinding.ItemPuzzleBinding


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

            val question = questionFormat(data)

            data?.let {
                with(binding) {

                    root.setOnClickListener { onListItemClickListener.onItemClick(data) }
                    tvName.text = it.name
                    root.setBackgroundColor(if (it.solved) Color.LTGRAY else Color.TRANSPARENT)
                    tvDescription.text = question
                    ivFavorite.setColorFilter(if (it.favorite) Color.MAGENTA else Color.GRAY)
                    ivDifficult.setColorFilter(
                        when (it.difficult) {
                            Difficult.Easy -> Color.GREEN
                            Difficult.Medium -> Color.YELLOW
                            Difficult.Hard -> Color.RED
                        }
                    )
                    ivFavorite.setOnClickListener {
                        onListFavoriteClickListener.onItemClick(data)
                    }
                }
            }
        }

        private fun questionFormat(data: Puzzle?): CharSequence {
            val width = binding.root.context.resources.displayMetrics.widthPixels
            val spannedText: Spanned =
                Html.fromHtml(data?.question?.replace(Regex("<img.*?src=\"(.*?)\"[^>]+>"), ""))
            val trimLenght = if (width > 900) 140 else 70

            val question = if (spannedText.length > trimLenght) {
                spannedText.toString().substring(0, trimLenght)
            } else {
                spannedText.trim()
            }
            return question
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