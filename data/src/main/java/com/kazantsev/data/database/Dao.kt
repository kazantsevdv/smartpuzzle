package com.kazantsev.data.database

import androidx.room.Dao
import androidx.room.Query
import com.kazantsev.data.database.model.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    @Query("SELECT * FROM Category")
    fun getAllFavorite(): Flow<List<CategoryEntity>>

}