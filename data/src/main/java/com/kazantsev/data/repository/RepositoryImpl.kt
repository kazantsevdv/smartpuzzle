package com.kazantsev.data.repository

import com.kazantsev.data.database.AppDatabase
import com.kazantsev.domain.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val db: AppDatabase,
) : Repository {

    override fun getCategoryList() =
        db.puzzleDao.getAllCategory().map { listCategory -> listCategory.map { it.toDomain() } }

    override fun getPuzzleListByCategory(id: Int) =
        db.puzzleDao.getPuzzleByCategory(id).map { listPuzzle -> listPuzzle.map { it.toDomain() } }

    override fun getPuzzleById(id: Int) =
        db.puzzleDao.getPuzzleById(id).map { puzzle -> puzzle.toDomain() }

    override suspend fun updateFavoriteByPuzzleId(id: Int, isFavorite: Boolean) {
        db.puzzleDao.updateFavoriteByPuzzleId(id, isFavorite)
    }

    override suspend fun updateSolvedByPuzzleId(id: Int) =
        db.puzzleDao.updateFavoriteByPuzzleId(id, true)

    override suspend fun clearSolvedAndFavoritePuzzle() {
        db.puzzleDao.clearSolvedPuzzle()
        db.puzzleDao.clearFavoritePuzzle()
    }

    override fun getFavoriteCount()=db.puzzleDao.getFavoriteCount()
}