package com.kazantsev.ui_puzzle_screen

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.kazantsev.domain.model.Puzzle
import com.kazantsev.navigation.RootNavDirections
import com.kazantsev.ui_common.adapter.OnListItemClickListener
import com.kazantsev.ui_common.adapter.PuzzleAdapter
import com.kazantsev.ui_common.base.BaseFragment
import com.kazantsev.ui_common.util.findTopNavController
import com.kazantsev.ui_puzzle_screen.databinding.PuzzleFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PuzzleFragment : BaseFragment<PuzzleFragmentBinding>() {

    private val viewModel: PuzzleViewModel by viewModels()
    private val args: PuzzleFragmentArgs by navArgs()

    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        PuzzleAdapter(onListItemClickListener,onItemFavoriteClickListener)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.list.adapter = adapter

        initToolbarNavigation()

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.loadCategoryWithTotal(args.id).collect {
                        binding.tvStat.text = requireContext().getString(
                            R.string.total_format,
                            it?.solved ?: 0,
                            it?.total ?: 0
                        )
                    }
                }
                launch {
                    viewModel.loadPuzzleList(args.id).collect {
                        adapter.submitList(it)
                    }
                }

            }
        }
    }

    private fun initToolbarNavigation() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
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
    ) = PuzzleFragmentBinding.inflate(inflater, container, false)
}