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
        holder.bind(position)
    }

    override fun onBindViewHolder(
        holder: PuzzleViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNullOrEmpty())
            super.onBindViewHolder(holder, position, payloads)
        else
            getItem(position)?.let { holder.bindFavoriteState(it.favorite) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PuzzleViewHolder {
        val bindingItem =
            ItemPuzzleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PuzzleViewHolder(bindingItem)
    }

    inner class PuzzleViewHolder(private val binding: ItemPuzzleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(id: Int) {

            getItem(id)?.let {
                with(binding) {
                    root.setOnClickListener { onListItemClickListener.onItemClick(getItem(id)) }
                    tvName.text = it.name
                    tvName.alpha = if (it.solved) solved else notSolved
                    tvDescription.text = questionFormatting(it.question)
                    tvDescription.alpha = if (it.solved) solved else notSolved
                    ivFavorite.setColorFilter(Util.favoriteColor(it.favorite))
                    ivDifficult.setColorFilter(Util.difficultColor(it.difficult))
                    ivFavorite.setOnClickListener {
                        onListFavoriteClickListener.onItemClick(getItem(id))
                    }
                }
            }
        }

        fun bindFavoriteState(isFavorite: Boolean) =
            binding.ivFavorite.setColorFilter(Util.favoriteColor(isFavorite))

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