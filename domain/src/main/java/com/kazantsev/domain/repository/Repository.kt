package com.kazantsev.domain.repository

import Category
import com.kazantsev.domain.model.Puzzle
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getCategoryList(): Flow<List<Category>>
    fun getPuzzleListByCategory(id:Int): Flow<List<Puzzle>>
    fun getPuzzleById(id: Int): Flow<Puzzle>
    suspend fun updateFavoriteByPuzzleId(id: Int, isFavorite: Boolean)
    suspend fun updateSolvedByPuzzleId(id: Int)
    suspend fun clearSolvedAndFavoritePuzzle()
}