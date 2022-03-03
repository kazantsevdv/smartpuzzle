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
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kazantsev.domain.model.Difficult
import com.kazantsev.domain.model.Puzzle
import com.kazantsev.navigation.RootNavDirections
import com.kazantsev.ui_common.adapter.OnListItemClickListener
import com.kazantsev.ui_common.adapter.PuzzleAdapter
import com.kazantsev.ui_common.base.BaseFragment
import com.kazantsev.ui_common.util.findTopNavController
import com.kazantsev.ui_puzzle_screen.databinding.FilterDialogBinding
import com.kazantsev.ui_puzzle_screen.databinding.PuzzleFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PuzzleFragment : BaseFragment<PuzzleFragmentBinding>() {
    private val viewModel: PuzzleViewModel by viewModels()
    private val args: PuzzleFragmentArgs by navArgs()
    private val adapter by lazy(LazyThreadSafetyMode.NONE) {
        PuzzleAdapter(onListItemClickListener, onItemFavoriteClickListener)
    }
    private var difficult: Difficult = Difficult.All
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        binding.list.adapter = adapter
        initToolbar()
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
                    viewModel.data.collect {
                        adapter.submitList(it)
                    }
                }
                launch {
                    viewModel.difficultFlowUseCase().collect {
                        difficult = it
                        updateToolbarMenu(it)
                    }
                }

            }
        }
    }

    private fun initToolbar() {
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.inflateMenu(R.menu.puzzle_menu)
        binding.toolbar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.filter) {
                openDialogSort()
                true
            } else false
        }
    }

    private fun updateToolbarMenu(difficult: Difficult) {
        val saveItem = binding.toolbar.menu.findItem(R.id.filter)
        if (difficult == Difficult.All)
            saveItem.setIcon(R.drawable.ic_baseline_filter_list_off_24)
        else
            saveItem.setIcon(R.drawable.ic_baseline_filter_list_24)
    }

    private fun openDialogSort() {
        val binding = FilterDialogBinding.inflate(LayoutInflater.from(context))
        binding.all.isSelected = difficult == Difficult.All
        binding.easy.isSelected = difficult == Difficult.Easy
        binding.medium.isSelected = difficult == Difficult.Medium
        binding.hard.isSelected = difficult == Difficult.Hard
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(binding.root)
        when (difficult) {
            Difficult.Easy -> binding.easy.isChecked = true
            Difficult.Medium -> binding.medium.isChecked = true
            Difficult.Hard -> binding.hard.isChecked = true
            else -> binding.all.isChecked = true
        }
        binding.rgFilter.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.all -> viewModel.setFilter(Difficult.All)
                R.id.easy -> viewModel.setFilter(Difficult.Easy)
                R.id.medium -> viewModel.setFilter(Difficult.Medium)
                R.id.hard -> viewModel.setFilter(Difficult.Hard)
            }
        }
        bottomSheetDialog.show()
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