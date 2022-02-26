package com.kazantsev.ui_puzzle_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazantsev.domain.model.Puzzle
import com.kazantsev.domain.use_case.CategoryWithTotalUseCase
import com.kazantsev.domain.use_case.PuzzleListUseCase
import com.kazantsev.domain.use_case.SetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PuzzleViewModel @Inject constructor(
    private val categoryWithTotalUseCase: CategoryWithTotalUseCase,
    private val puzzleListUseCase: PuzzleListUseCase,
    private val setFavoriteUseCase: SetFavoriteUseCase
) : ViewModel() {
    fun loadCategoryWithTotal(categoryId: Int) =
        categoryWithTotalUseCase(categoryId).map { it.firstOrNull() }

    fun loadPuzzleList(categoryId: Int) =
        puzzleListUseCase(categoryId)

    fun setFavorite(puzzle: Puzzle) {
        viewModelScope.launch {
            setFavoriteUseCase(puzzle.id, !puzzle.favorite)
        }
    }
}