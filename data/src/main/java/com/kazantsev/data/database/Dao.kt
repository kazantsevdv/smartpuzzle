package com.kazantsev.data.database

import androidx.room.Dao
import androidx.room.Query
import com.kazantsev.data.database.model.CategoryEntity
import com.kazantsev.data.database.model.PuzzleEntity
import com.kazantsev.domain.model.CategoryWithTotal
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM Category")
    fun getAllCategory(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM Puzzle WHERE categoryID=:idCategory")
    fun getPuzzleByCategory(idCategory: Int): Flow<List<PuzzleEntity>>

    @Query("SELECT * FROM Puzzle WHERE favorite=1")
    fun getFavoritePuzzle(): Flow<List<PuzzleEntity>>

    @Query("SELECT * FROM Puzzle WHERE id=:id")
    fun getPuzzleById(id: Int): Flow<PuzzleEntity>

    @Query("UPDATE Puzzle SET favorite = :favorite  WHERE id=:id")
    suspend fun updateFavoriteByPuzzleId(id: Int, favorite: Boolean)

    @Query("UPDATE Puzzle SET solved = :solved  WHERE id=:id")
    suspend fun updateSolvedByPuzzleId(id: Int, solved: Boolean)

    @Query("UPDATE Puzzle SET solved = 0")
    suspend fun clearSolvedPuzzle()

    @Query("UPDATE Puzzle SET favorite = 0")
    suspend fun clearFavoritePuzzle()

    @Query("SELECT COUNT(id) FROM Puzzle WHERE favorite = 1")
    fun getFavoriteCount(): Flow<Int>

    @Query("SELECT categoryID, COUNT(id) as total ,SUM(solved) as solved FROM Puzzle GROUP BY categoryID HAVING (:idCategory IS NULL OR categoryID = :idCategory)")
    fun getCategoryWithTotal(idCategory: Int? = null): Flow<List<CategoryWithTotal>>
}