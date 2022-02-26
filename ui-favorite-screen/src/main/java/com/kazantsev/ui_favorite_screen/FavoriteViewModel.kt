package com.kazantsev.ui_favorite_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kazantsev.domain.model.Puzzle
import com.kazantsev.domain.use_case.FavoriteListUseCase
import com.kazantsev.domain.use_case.SetFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteListUseCase: FavoriteListUseCase,
    private val setFavoriteUseCase: SetFavoriteUseCase
) : ViewModel() {

    fun loadPuzzleFavoriteList() = favoriteListUseCase()

    fun setFavorite(puzzle: Puzzle) {
        viewModelScope.launch {
            setFavoriteUseCase(puzzle.id, !puzzle.favorite)
        }
    }
}