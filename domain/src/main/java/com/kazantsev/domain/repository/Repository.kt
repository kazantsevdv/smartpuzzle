package com.kazantsev.domain.repository

import com.kazantsev.domain.model.Category
import com.kazantsev.domain.model.CategoryWithTotal
import com.kazantsev.domain.model.Puzzle
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun getCategoryList(): Flow<List<Category>>
    fun getPuzzleListByCategory(id:Int): Flow<List<Puzzle>>
    fun getPuzzleById(id: Int): Flow<Puzzle>
    suspend fun updateFavoriteByPuzzleId(id: Int, isFavorite: Boolean)
    suspend fun updateSolvedByPuzzleId(id: Int)
    suspend fun clearSolvedAndFavoritePuzzle()
    fun getFavoriteCount(): Flow<Int>
    fun getCategoryWithTotal(idCategory: Int? = null):Flow<List<CategoryWithTotal>>
    fun getFavoritePuzzleList(): Flow<List<Puzzle>>
}