package com.kazantsev.ui_puzzle_screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazantsev.domain.model.Difficult
import com.kazantsev.domain.model.Puzzle
import com.kazantsev.domain.use_case.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PuzzleViewModel @Inject constructor(
    private val categoryWithTotalUseCase: CategoryWithTotalUseCase,
    private val puzzleListUseCase: PuzzleListUseCase,
    private val setFavoriteUseCase: SetFavoriteUseCase,
    private val difficultSaveUseCase: DifficultSaveUseCase,
     val preferenceFlowUseCase: PreferenceFlowUseCase,
    private val notShowSolvedSaveUseCase: NotShowSolvedSaveUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val args = PuzzleFragmentArgs.fromSavedStateHandle(savedStateHandle)
    private val categoryId = args.id

    private val _data = MutableStateFlow<List<Puzzle>>(emptyList())
    val data: StateFlow<List<Puzzle>> = _data

    init {
        viewModelScope.launch {
            preferenceFlowUseCase().flatMapLatest { pref ->
                val difficult = Difficult.valueOf(pref.difficult)
                puzzleListUseCase(categoryId)
                    .map { list ->
                        list
                            .filter {
                                if (difficult == Difficult.All) true else it.difficult == difficult
                            }
                            .filter {
                                if (!pref.notShowSolved) true else !it.solved
                            }
                    }
            }.collect {
                _data.value = it
            }
        }
    }

    fun setFilter(difficult: Difficult) {
        viewModelScope.launch {
            difficultSaveUseCase(difficult)
        }
    }
    fun setShowSolved(show: Boolean) {
        viewModelScope.launch {
            notShowSolvedSaveUseCase(show)
        }
    }

    fun loadCategoryWithTotal(categoryId: Int) =
        categoryWithTotalUseCase(categoryId).map { it.firstOrNull() }


    fun setFavorite(puzzle: Puzzle) {
        viewModelScope.launch {
            setFavoriteUseCase(puzzle.id, !puzzle.favorite)
        }
    }
}