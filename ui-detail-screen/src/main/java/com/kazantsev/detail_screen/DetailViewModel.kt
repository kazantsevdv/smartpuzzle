package com.kazantsev.detail_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazantsev.domain.model.Puzzle
import com.kazantsev.domain.use_case.PuzzleListUseCase
import com.kazantsev.domain.use_case.PuzzleUseCase
import com.kazantsev.domain.use_case.SetFavoriteUseCase
import com.kazantsev.domain.use_case.SolvedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val puzzleUseCase: PuzzleUseCase,
    private val setFavoriteUseCase: SetFavoriteUseCase,
    private val solvedUseCase: SolvedUseCase,
) : ViewModel() {
    fun loadPuzzle(id: Int) =
        puzzleUseCase(id)

    fun setFavorite(puzzle: Puzzle) {
        viewModelScope.launch {
            setFavoriteUseCase(puzzle.id, !puzzle.favorite)
        }
    }
    fun setSolved(puzzle: Puzzle) {
        viewModelScope.launch {
            solvedUseCase(puzzle.id)
        }
    }
}