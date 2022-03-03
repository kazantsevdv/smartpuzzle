package com.kazantsev.ui_favorite_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kazantsev.domain.model.Puzzle
import com.kazantsev.navigation.RootNavDirections
import com.kazantsev.ui_common.adapter.OnListItemClickListener
import com.kazantsev.ui_common.adapter.PuzzleAdapter
import com.kazantsev.ui_common.base.BaseFragment
import com.kazantsev.ui_common.util.findTopNavController
import com.kazantsev.ui_favorite_screen.databinding.FavoriteFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoriteFragment : BaseFragment<FavoriteFragmentBinding>() {

    private val viewModel: FavoriteViewModel by viewModels()


    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        PuzzleAdapter(onListItemClickListener, onItemFavoriteClickListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    viewModel.loadPuzzleFavoriteList().collect {
                        binding.emptyImage.isVisible = it.isEmpty()
                        binding.emptyMessage.isVisible = it.isEmpty()
                        binding.list.isVisible = it.isNotEmpty()
                        adapter.submitList(it)
                    }
                }

            }
        }
    }


    private val onListItemClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(puzzle: Puzzle) {
                findTopNavController().navigate(
                    RootNavDirections.actionDetailFlow(
                        puzzle.id
                    )
                )
            }
        }
    private val onItemFavoriteClickListener: OnListItemClickListener =
        object : OnListItemClickListener {
            override fun onItemClick(puzzle: Puzzle) {
                viewModel.setFavorite(puzzle)
            }
        }

    override fun initBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FavoriteFragmentBinding.inflate(inflater, container, false)
}