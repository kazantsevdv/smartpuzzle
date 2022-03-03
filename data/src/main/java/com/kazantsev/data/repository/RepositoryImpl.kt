package com.kazantsev.data.repository

import com.kazantsev.data.database.AppDatabase
import com.kazantsev.domain.repository.Repository
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val db: AppDatabase,
) : Repository {

    override fun getCategoryList() =
        db.puzzleDao.getAllCategory().map { listCategory -> listCategory.map { it.toDomain() } }

    override fun getPuzzleListByCategory(id: Int) =
        db.puzzleDao.getPuzzleByCategory(id).map { listPuzzle -> listPuzzle.map { it.toDomain() } }

    override fun getFavoritePuzzleList() =
        db.puzzleDao.getFavoritePuzzle().map { listPuzzle -> listPuzzle.map { it.toDomain() } }

    override fun getPuzzleById(id: Int) =
        db.puzzleDao.getPuzzleById(id).map { puzzle -> puzzle.toDomain() }

    override suspend fun updateFavoriteByPuzzleId(id: Int, isFavorite: Boolean) {
        db.puzzleDao.updateFavoriteByPuzzleId(id, isFavorite)
    }

    override suspend fun updateSolvedByPuzzleId(id: Int) =
        db.puzzleDao.updateSolvedByPuzzleId(id, true)

    override suspend fun clearSolvedPuzzle() = db.puzzleDao.clearSolvedPuzzle()

    override fun getFavoriteCount() = db.puzzleDao.getFavoriteCount()

    override fun getCategoryWithTotal(idCategory: Int?) =
        db.puzzleDao.getCategoryWithTotal(idCategory)

}